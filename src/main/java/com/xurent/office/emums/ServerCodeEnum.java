package com.xurent.office.emums;

public enum ServerCodeEnum {
    COMMON(1, "通用错误码"),
    REQUEST_PARAM_ERROR(400, "请求参数错误"),
    REQUEST_METHOD_NOT_SUPPORT(401, "不支持的请求方式"),
    REQUEST_PARAM_FORMAT_ERROR(402, "请求参数格式错误"),
    AUTH_FAIL(403, "无权限访问"),
    DATA_AUTH_FAIL(404, "无权限访问该数据"),

    PDF_TO_WORD_FAIL(10001,"PDF转Wor失败"),
    ID_PHOTO_FAIL(20001,"证件照生成失败"),
    ID_PHOTO_TYPE_FAIL(20002,"图片上传格式错误"),
    ID_PHOTO_EXIT_FAIL(20003,"照片不存在")
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
