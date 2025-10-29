package com.znv.manage.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yufei on 2020/6/30.
 */

@Configuration
@ConfigurationProperties(prefix = "auth.apitokencheck")
@Data
public class ApiTokenConfig {

    // 开关
    private boolean enable;

    // 存储方式
    private String storeType;

    // token请求头
    private String header;

    // 过期时间
    private int expiration;

    // 加密key
    private String aesKey;

    // 调试用的session key
    private String debugSessionKey;
}
