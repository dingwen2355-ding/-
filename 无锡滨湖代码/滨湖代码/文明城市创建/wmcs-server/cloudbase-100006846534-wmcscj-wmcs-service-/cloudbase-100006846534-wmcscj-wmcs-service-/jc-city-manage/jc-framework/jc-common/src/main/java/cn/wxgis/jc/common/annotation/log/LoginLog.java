package cn.wxgis.jc.common.annotation.log;

import cn.wxgis.jc.common.enums.basedata.SystemEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {

    SystemEnum.LoginEnum value();

    boolean isSaveRequestData() default true;
}
