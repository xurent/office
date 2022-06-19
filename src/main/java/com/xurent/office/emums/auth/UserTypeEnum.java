
package com.xurent.office.emums.auth;

/**
 * 用户类型
 *
 * @author xurent
 * @date 2021/4/9 15:48
 */
public enum UserTypeEnum {
    // 存储类型枚举
    ONE("1", "小程序用户"),
    TWO("2", "网站用户"),
    THREE("3", "外部用户");

    private String code;
    private String desc;

    UserTypeEnum(String code, String desc) {
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
