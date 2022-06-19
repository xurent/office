
package com.xurent.office.emums.auth;

/**
 * auth header头枚举
 *
 * @author xurent
 * @date 2021/4/9 15:48
 */
public enum AuthHeaderEnum {
    // auth header头枚举
    USER_TYPE("X-UserType", "用户类型"),
    MID("X-Mid", "mid账户"),
    ACCOUNT("X-Account", "用户域账号"),
    APP_KEY("X-AppKey", "应用key"),
    COLOR_KEY("x1-bilispy-color", "染色参数");

    private String code;
    private String desc;

    AuthHeaderEnum(String code, String desc) {
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
