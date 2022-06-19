/*
 * Copyright (c) 2015-2020 BiliBili Inc.
 */

package com.xurent.office.annotation;

import java.lang.annotation.*;

/**
 * 注入用户信息
 *
 * @param
 * @author xurent
 * @date 2020/9/2
 * @return
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface UserInfo {

}
