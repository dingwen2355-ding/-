package cn.wxgis.jc.jpa.domain.comment;

import java.lang.annotation.*;

/**
 * 实体类表名的注释类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TableComment {
    /**
     * 字段注释
     * @return
     */
    String value() default "";
}
