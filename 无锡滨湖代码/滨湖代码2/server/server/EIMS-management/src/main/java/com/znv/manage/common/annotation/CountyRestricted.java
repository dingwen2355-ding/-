package com.znv.manage.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于限制区县用户的返回数据
 *
 * @return
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CountyRestricted {

    /**
     * 用户id的参数名称
     *
     * @return java.lang.String
     */
    String idParam() default "userId";

    /**
     * 区域的参数名称
     *
     * @return java.lang.String
     */
    String regionParam() default "regionId";

    /**
     * 区域是否使用中文
     *
     * @return boolean
     */
    boolean useChinese() default false;
}
