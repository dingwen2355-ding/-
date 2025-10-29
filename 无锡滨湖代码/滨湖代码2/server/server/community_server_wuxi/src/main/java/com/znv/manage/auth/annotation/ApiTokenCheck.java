package com.znv.manage.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要鉴权
 * Created by yufei on 2020/6/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface ApiTokenCheck {

    boolean isTokenEncrypt() default false;
}
