package com.xurent.office.emums;

public enum ServerCodeEnum {
    COMMON(1, "通用错误码"),
    REQUEST_PARAM_ERROR(400, "请求参数错误"),
    REQUEST_METHOD_NOT_SUPPORT(401, "不支持的请求方式"),
    REQUEST_PARAM_FORMAT_ERROR(402, "请求参数格式错误"),
    AUTH_FAIL(403, "无权限访问"),
    DATA_AUTH_FAIL(404, "无权限访问该数据"),

    PDF_TO_WORD_FAIL(10001,"PDF转Wor失败")
    ;

    private Integer code;
    private String desc;

    ServerCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
