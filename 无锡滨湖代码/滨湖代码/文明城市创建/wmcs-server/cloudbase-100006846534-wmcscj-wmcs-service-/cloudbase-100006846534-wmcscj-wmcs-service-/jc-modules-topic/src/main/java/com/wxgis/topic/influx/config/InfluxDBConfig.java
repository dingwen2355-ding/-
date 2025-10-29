package com.wxgis.topic.influx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.influx")
public class InfluxDBConfig {

    /**
     * 连接地址
     */
    public String url = "http://106.14.27.198:8086";

    /**
     * 用户
     */
    public String user = "wmcs";

    /**
     * 密码
     */
    public String password;

    /**
     * 数据库
     */
    public String database;

}
