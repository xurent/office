package com.xurent.office.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    /**
     * 登录人AD账号
     */
    private String account;
    /**
     * 角色列表
     */
    private List<String> roleList;

    /**
     * 登录人昵称
     * 仅业务中初始化使用，登录时为初始化，勿直接使用
     */
    private String nickName;

}
