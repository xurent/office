package com.xurent.office.exception.pleiades;

import com.google.common.base.Strings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerCode {
    private static final Map<Integer, String> CODES = new ConcurrentHashMap<>();

    public static final ServerCode SUCCESS = asCode(0); // success
    public static final ServerCode APP_NOT_EXIST = asCode(-1); //应用程序不存在或已被封禁
    public static final ServerCode SIGN_ERROR = asCode(-3); //API校验密匙错误
    public static final ServerCode NOT_LOGIN = asCode(-101); //未登录
    public static final ServerCode ACCOUNT_BANED = asCode(-102); //账号被封停
    public static final ServerCode TOKEN_ILLEGAL = asCode(-111); //csrf 校验失败
    public static final ServerCode SERVICE_UPDATE = asCode(-112); //系统升级中，敬请谅解
    public static final ServerCode NOT_MODIFIED = asCode(-304); //木有改动
    public static final ServerCode ILLEGAL_REQUEST = asCode(-400); //请求错误
    public static final ServerCode FORBIDDEN = asCode(-403); //访问权限不足
    public static final ServerCode NOT_FOUND = asCode(-404); //啥都木有
    public static final ServerCode METHOD_NOT_SUPPORT = asCode(-405); //不支持该方法
    public static final ServerCode FAIL_RUNTIME = asCode(-500); //服务器错误
    public static final ServerCode FAIL_UNAVAILABLE = asCode(-503); //服务暂不可用
    public static final ServerCode FAIL_TIMEOUT = asCode(-504); //处理超时
    public static final ServerCode FAIL_FILE_NOT_EXIST = asCode(-616); //上传文件不存在
    public static final ServerCode FAIL_FILE_TOO_LARGE = asCode(-617); //上传文件太大


    @Deprecated // do not use !!!!
    public static final ServerCode FAIL_BUSINESS = asCode(-10500); //处理失败
    @Deprecated // do not use !!!!
    public static final ServerCode NOT_COMPATIBLE_PAY = asCode(-40312); //不支持新老支付混用订单号
    @Deprecated // do not use !!!!
    public static final ServerCode WARN = asCode(-40313); //此码为警告码，指本次操作生效，但由于特殊原因一些期望的操作程序未能达成，需要通知到客户端


    static {
        Loaders.init();
        CODES.putAll(Loaders.DEFAULT_CODES);
    }

    private final int code;
    private final String message;

    private ServerCode(int code) {
        this(code, null);
    }

    private ServerCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        if (Strings.isNullOrEmpty(this.message)) {
            return CODES.getOrDefault(this.code, String.valueOf(this.code));
        }
        return this.message;
    }

    public static ServerCode create(int code, String message) {
        return new ServerCode(code, message);
    }

    public static ServerCode asCode(int code) {
        return new ServerCode(code);
    }

    public static void addCodes(Map<Integer, String> codes) {
        CODES.putAll(codes);
    }
}
