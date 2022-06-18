package com.xurent.office.utils;


import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.exception.CommonException;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * 断言工具方法集
 *
 * @author xurent
 * @date 2021-04-16 16:04
 */
public class Asserts {

    /**
     * 指定code、message断言
     *
     * @param expression supplier、boolean表达式
     * @param serverCode 错误码
     * @param msg        消息内容
     */
    public static void check(final boolean expression, final int serverCode, String msg) {
        if (!expression) {
            throw new CommonException(serverCode, msg);
        }
    }

    /**
     * 指定错误枚举断言
     *
     * @param expression     supplier、boolean表达式
     * @param ServerCodeEnum 错误枚举
     */
    public static void check(final boolean expression, final ServerCodeEnum ServerCodeEnum) {
        if (!expression) {
            throw new CommonException(ServerCodeEnum.getCode(), ServerCodeEnum.getDesc());
        }
    }

    /**
     * 指定错误枚举、参数值断言
     *
     * @param expression     supplier、boolean表达式
     * @param ServerCodeEnum 错误枚举
     * @param args           参数
     */
    public static void check(final boolean expression, final ServerCodeEnum ServerCodeEnum, final Object... args) {
        if (!expression) {
            throw new CommonException(ServerCodeEnum.getCode(), MessageFormat.format(ServerCodeEnum.getDesc(), args));
        }
    }

    /**
     * 指定错误消息断言，使用通用错误码
     *
     * @param expression supplier、boolean表达式
     * @param msg        提示消息
     */
    public static void check(final boolean expression, final String msg) {
        if (!expression) {
            throw new CommonException(ServerCodeEnum.COMMON.getCode(), msg);
        }
    }

    /**
     * 指定错误消息、替换参数断言，使用通用错误码
     *
     * @param expression supplier、boolean表达式
     * @param msg        提示消息
     * @param args       待替换参数
     */
    public static void check(final boolean expression, final String msg, final Object... args) {
        if (!expression) {
            throw new CommonException(ServerCodeEnum.COMMON.getCode(), MessageFormat.format(msg, args));
        }
    }

    /**
     * 判空提示
     *
     * @param model          校验对象
     * @param ServerCodeEnum 错误消息枚举
     */
    public static void isNull(final Object model, final ServerCodeEnum ServerCodeEnum) {
        if (Objects.isNull(model)) {
            throw new CommonException(ServerCodeEnum.getCode(), ServerCodeEnum.getDesc());
        }
    }

    /**
     * 判空提示
     *
     * @param model          校验对象
     * @param ServerCodeEnum 错误消息枚举
     * @param args           待替换参数
     */
    public static void isNull(final Object model, final ServerCodeEnum ServerCodeEnum, final Object... args) {
        if (Objects.isNull(model)) {
            throw new CommonException(ServerCodeEnum.getCode(), MessageFormat.format(ServerCodeEnum.getDesc(), args));
        }
    }
}
