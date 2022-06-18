
package com.xurent.office.interceptor;

import com.alibaba.fastjson.JSON;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.xurent.office.annotation.Permission;
import com.xurent.office.constant.CommonConstant;
import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.emums.auth.RoleEnum;
import com.xurent.office.model.User;
import com.xurent.office.utils.Asserts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 权限拦截器，额外注入当前访问用户身份信息
 * 包含当前访问账号、角色列表、是否白名单
 *
 * @author xurent
 * @date 2020/9/14 17:34
 */
@Slf4j
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.warn(">>>>>>>> enter permission <<<<<<<<");
        long currentMills = System.currentTimeMillis();
        /*
         * 非handlerMethod直接返回
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //根据方法注入@Permission验证
        Method method = handlerMethod.getMethod();
        Permission auth = method.getAnnotation(Permission.class);
        Permission permissionRequiredClass = handlerMethod.getBeanType().getAnnotation(Permission.class);
        boolean required = (auth != null || permissionRequiredClass != null);

        //需要验证身份
        if (required) {
            User visitor = getVisitor(request);
            List<String> definedRoles = visitor.getRoleList();
            Asserts.check(!CollectionUtils.isEmpty(definedRoles), ServerCodeEnum.AUTH_FAIL);

            /*
             * 接口定义的角色列表
             */
            RoleEnum[] roles = auth != null ? auth.roles() : permissionRequiredClass.roles();
            List<String> requiredRoles = new ArrayList<>(Arrays.asList(roles)).stream().map(RoleEnum::getCode).collect(Collectors.toList());
            int requiredRoleSize = requiredRoles.size();

            /*
             * 判断登录用户角色与接口定义的角色是否存在交集
             * 是，继续访问
             * 否，拒绝访问
             */
            requiredRoles.retainAll(definedRoles);
            if (requiredRoleSize > 0 && requiredRoles.size() == 0) {
                log.warn(">>>>>>>> permission refuse，no auth role，user: {}", JSON.toJSONString(visitor));
                Asserts.check(false, ServerCodeEnum.AUTH_FAIL);
            }
        }
        log.warn(">>>>>>>> permission pass <<<<<<<<");
        return super.preHandle(request, response, handler);
    }

    /**
     * 通过访问账号获取角色等信息
     *
     * @param request 当前请求
     * @return 当前用户对象
     */
    private User getVisitor(HttpServletRequest request) {
        String account = request.getHeader(CommonConstant.ACCOUNT);
        List<String> roles = Stream.of(request.getAttribute(CommonConstant.ROLE).toString().split(CommonConstant.COMMA)).collect(Collectors.toList());

        return User.builder()
                .account(account)
                .roleList(roles)
                .build();
    }

}
