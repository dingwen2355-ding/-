/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jslc.common.annotation;

import java.lang.annotation.*;

/**
 *@描述 日志文件注解
 *@创建人 zhengyongbiao
 *@创建时间 2023/2/14 8:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LcSysLog {

	String value() default "";
}
