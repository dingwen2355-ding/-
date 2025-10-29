package cn.wxgis.jc.log.annotation;

import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    // ========== 模块字段 ==========

    /**
     * 操作模块
     */
    String title() default "";

    /**
     * 描述
     */
    String desc() default "";
    /**
     * 操作分类
     */
    OperLogTypeEnum type() default OperLogTypeEnum.OTHER;

    /**
     * 操作人类别
     */
    OperLogUserTypeEnum operUserType() default OperLogUserTypeEnum.MANAGE;

    // ========== 开关字段 ==========

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    String[] excludeParamNames() default {};

}
