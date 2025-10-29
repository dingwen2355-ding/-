package com.jslc.common.config;

import lombok.Data;

/**
 * @author lc
 * @title: AbstractSsoConfiguration
 * @projectName digit-binhu-back
 * @description: TODO
 * @date 2023/8/24 17:11
 */
@Data
public abstract class AbstractSsoConfiguration {
     String clientId;
     String clientSecret;
     String accessTokenUri;
     String userAuthorizationUri;
     String homePage;
     String redirectUri;
     String userTokenUrl;
}
