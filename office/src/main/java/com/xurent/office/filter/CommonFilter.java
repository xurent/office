package com.xurent.office.filter;

import com.xurent.office.constant.CommonConstant;
import com.xurent.office.emums.auth.RoleEnum;
import com.xurent.office.emums.auth.UserTypeEnum;
import com.xurent.office.model.properties.CommonProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@Order(-1)
@WebFilter(urlPatterns = "/*")
public class CommonFilter implements Filter {

    @Autowired
    private CommonProperties commonProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 白名单过滤
        StringBuffer requestUrl = request.getRequestURL();
        String filterUrl = commonProperties.getWhitelist();
        String[] filterUrlArr = filterUrl.split(CommonConstant.COMMA);
        boolean isWhite = false;
        for (String filterUrlAddress : filterUrlArr) {
            if (requestUrl.toString().contains(filterUrlAddress) && StringUtils.isNotBlank(filterUrlAddress)) {
                isWhite = true;
                break;
            }
        }

        if (!isWhite) {
            String account = request.getHeader(CommonConstant.ACCOUNT);
            String userType = request.getHeader(CommonConstant.USER_TYPE);

            if (StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(userType) &&
                    UserTypeEnum.ONE.getCode().equals(userType)) {
                // 获取角色
                request.setAttribute(CommonConstant.REQ_HEADER_APP_KEY, "office");

                //List<AuthRoleBO> authRoleResultList = authHttpService.getRole();
                List<String> authRoleResultList = Arrays.asList(request.getHeader(CommonConstant.ROLE));
                if (CollectionUtils.isEmpty(authRoleResultList)) {
                    log.error(">>>>>>>> 用户【{}】未配置角色信息，无权限访问", account);
                    response.sendRedirect(CommonConstant.FILTER_EXCEPTION_DISPATCHER_URL);
                    return;
                }

                StringBuilder roleBuilder = new StringBuilder();
                for (String authRoleResult : authRoleResultList) {
                    roleBuilder.append(authRoleResult).append(CommonConstant.COMMA);

                }

                String role = roleBuilder.toString();
                role = role.substring(NumberUtils.INTEGER_ZERO, role.lastIndexOf(CommonConstant.COMMA));
                request.setAttribute(CommonConstant.ROLE, role);
            } else {
                log.error(">>>>>>>> 无登录用户，无权限访问");
                response.sendRedirect(CommonConstant.FILTER_EXCEPTION_DISPATCHER_URL);
                return;
            }
        }
        log.info(">>>>>>>> filter pass <<<<<<<<");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
