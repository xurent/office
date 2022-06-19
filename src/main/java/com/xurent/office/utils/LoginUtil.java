package com.xurent.office.utils;

import com.xurent.office.model.User;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class LoginUtil {

    private  ConcurrentHashMap<String, User> map;
    private static LoginUtil loginUtil;

    private LoginUtil(){
        map=new ConcurrentHashMap<>();

        User user=User.builder()
                .account("test")
                .nickName("测试")
                .roleList(Arrays.asList("admin"))
                .build();
        map.put("test",user);
    }

    public static LoginUtil getInstance(){
        if(loginUtil==null) {
            synchronized (LoginUtil.class) {
                if (loginUtil == null) {
                    loginUtil=new LoginUtil();
                }
            }
        }
        return  loginUtil;
    }


    public ConcurrentHashMap<String, User> getMap() {
        return map;
    }


}
