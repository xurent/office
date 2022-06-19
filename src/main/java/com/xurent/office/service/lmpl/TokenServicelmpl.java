package com.xurent.office.service.lmpl;

import com.xurent.office.constant.CommonConstant;
import com.xurent.office.service.TokenService;
import com.xurent.office.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service("tokenService")
public class TokenServicelmpl implements TokenService {


    //private Redis redis;

    @SuppressWarnings("all")
    private HttpServletRequest getRequest(){
        return  ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();

    }

    @Override
    public String getToken() {
        HttpServletRequest request = this.getRequest();
        String Token= CookieUtils.getCookie(request.getCookies(), CommonConstant.TOKEN_STRING);

        if(StringUtils.isNotBlank(Token)){
            return Token;
        }
        //不支持Cookie的情况
        Token=request.getParameter("token");

        return Token;

    }


    //public String getTokenByAuth(String username,String password){
    //
    // }

}
