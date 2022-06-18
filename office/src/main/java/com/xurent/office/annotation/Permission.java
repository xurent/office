/*
 * Copyright (c) 2015-2020 BiliBili Inc.
 */

package com.xurent.office.annotation;

import com.xurent.office.emums.auth.RoleEnum;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


import java.lang.annotation.*;

/**
 * 接口层权限控制注解
 *
 * @author xurent
 * @date 2020/9/14 17:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface Permission {
    /**
     * 校验方式：
     * 1、默认可不设置值，仅校验角色不能为空
     * 2、设置角色列表，请求的角色需被包含在列表中
     *
     * @return 角色数组
     */
    RoleEnum[] roles() default {};
}
