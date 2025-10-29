package cn.wxgis.jc.jpa.domain.comment;

import java.lang.annotation.*;

/**
 * 实体类注解的注释类
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnComment {

    /**
     * 字段注释
     * @return
     */
    String value() default "";
}
