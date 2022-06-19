package com.xurent.office.exception.pleiades;

import java.util.HashMap;
import java.util.Map;

import static com.xurent.office.exception.pleiades.ServerCode.*;


public class Loaders {
    static Map<Integer, String> DEFAULT_CODES = new HashMap<>();

    public static void init() {
        DEFAULT_CODES.put(SUCCESS.getCode(), "success");
        DEFAULT_CODES.put(APP_NOT_EXIST.getCode(), "应用程序不存在或已被封禁");
        DEFAULT_CODES.put(SIGN_ERROR.getCode(), "API校验密匙错误");
        DEFAULT_CODES.put(NOT_LOGIN.getCode(), "未登录");
        DEFAULT_CODES.put(ACCOUNT_BANED.getCode(), "账号被封停");
        DEFAULT_CODES.put(TOKEN_ILLEGAL.getCode(), "csrf 校验失败");
        DEFAULT_CODES.put(SERVICE_UPDATE.getCode(), "系统升级中，敬请谅解");
        DEFAULT_CODES.put(NOT_MODIFIED.getCode(), "木有改动");
        DEFAULT_CODES.put(ILLEGAL_REQUEST.getCode(), "请求错误");
        DEFAULT_CODES.put(FORBIDDEN.getCode(), "访问权限不足");
        DEFAULT_CODES.put(NOT_FOUND.getCode(), "啥都木有");
        DEFAULT_CODES.put(METHOD_NOT_SUPPORT.getCode(), "不支持该方法");
        DEFAULT_CODES.put(FAIL_RUNTIME.getCode(), "服务器错误");
        DEFAULT_CODES.put(FAIL_UNAVAILABLE.getCode(), "服务暂不可用");
        DEFAULT_CODES.put(FAIL_TIMEOUT.getCode(), "处理超时");
        DEFAULT_CODES.put(FAIL_FILE_NOT_EXIST.getCode(), "上传文件不存在");
        DEFAULT_CODES.put(FAIL_FILE_TOO_LARGE.getCode(), "上传文件太大");
        DEFAULT_CODES.put(FAIL_BUSINESS.getCode(), "处理失败");
        DEFAULT_CODES.put(NOT_COMPATIBLE_PAY.getCode(), "不支持新老支付混用订单号");
        DEFAULT_CODES.put(WARN.getCode(), "警告");
    }
}
