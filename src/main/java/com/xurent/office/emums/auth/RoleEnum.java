/*
 * Copyright (c) 2015-2020 BiliBili Inc.
 */

package com.xurent.office.emums.auth;

/**
 * 角色枚举
 *
 * @author xurent
 * @date 2021/4/13 14:18
 */
public enum RoleEnum {
    // 角色枚举
    COMMON("common", "通用角色"),
    OD("od", "OD"),
    SYSTEM("system", "管理员"),
    EMPLOYEE("employee", "考核员工"),
    BP("bp", "计划管理员"),
    BP_EXTERNAL("bp_offsite", "计划协管者/异地BP"),
    LEADER("leader", "Leader#$团队管理员"),
    VP("vp", "VP"),
    DEVELOPER("develop", "开发角色"),
    ;
    private String code;
    private String desc;

    RoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
