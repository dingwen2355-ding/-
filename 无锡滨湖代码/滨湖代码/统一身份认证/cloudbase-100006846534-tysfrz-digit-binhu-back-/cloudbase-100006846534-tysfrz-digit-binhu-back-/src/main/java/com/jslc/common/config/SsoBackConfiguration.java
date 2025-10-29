package com.jslc.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhengyongbiao
 * @title: SsoConfiguration
 * @projectName digit-binhu-back
 * @description: 单点登录配置
 * @date 2023/5/19 13:56
 */
@Component
@ConfigurationProperties(prefix = "sso.oauth2.client.back")
@Data
public class SsoBackConfiguration extends AbstractSsoConfiguration{

}
