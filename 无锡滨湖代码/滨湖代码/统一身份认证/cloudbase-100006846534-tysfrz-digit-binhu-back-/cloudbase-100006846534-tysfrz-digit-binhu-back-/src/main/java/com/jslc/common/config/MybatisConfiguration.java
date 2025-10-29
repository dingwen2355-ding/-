package com.jslc.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisConfiguration
 * @Description
 * @Author zhengyongbiao
 * @Date 2021/11/10
 * @Version 3.6.0
 * @Since JDK 1.8
 **/
@Configuration
@MapperScan({"org.springblade.**.mapper.**", "com.jslc.**.mapper.**"})
@ComponentScan({"com.jslc"})
public class MybatisConfiguration {
}
