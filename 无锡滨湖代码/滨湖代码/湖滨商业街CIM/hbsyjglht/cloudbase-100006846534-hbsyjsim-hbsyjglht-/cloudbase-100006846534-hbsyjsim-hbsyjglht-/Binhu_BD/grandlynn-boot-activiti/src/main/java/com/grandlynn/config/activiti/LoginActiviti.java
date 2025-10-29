package com.grandlynn.config.activiti;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginActiviti {
    /**
     * 设置固定登录人
     *
     * @return
     */
    String loginName() default "";

    /**
     * 在原有的基础上添加新的角色,会自动拼接ROLE_前缀
     *
     * @return
     */
    String[] addNewRole() default {};

    /**
     * 在原有的基础上添加组,会自动拼接GROUP_前缀
     *
     * @return
     */
    String[] addNewGroup() default {};

    /**
     * 排除原有角色 默认不排除
     *
     * @return
     */
    boolean excludeOldRole() default false;
}
