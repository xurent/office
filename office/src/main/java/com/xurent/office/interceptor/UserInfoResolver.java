
package com.xurent.office.interceptor;

import com.alibaba.fastjson.JSON;
import com.xurent.office.annotation.UserInfo;
import com.xurent.office.constant.CommonConstant;
import com.xurent.office.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author xurent
 * @description
 * @date 2021/3/5
 */
@Slf4j
public class UserInfoResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(UserInfo.class);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        User user = new User();
        user.setAccount(request.getHeader(CommonConstant.ACCOUNT));
        user.setRoleList(Stream.of(request.getAttribute(CommonConstant.ROLE).toString().split(CommonConstant.COMMA)).collect(Collectors.toList()));
        log.warn(">>>>>>>> 执行操作用户：{}", JSON.toJSONString(user));
        return user;
    }
}
