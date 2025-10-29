package com.grandlynn.spa.catalogue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
   prefix = "binhu.sso"
)
@Data
public class SsoLoginConfig {
   private static final String CODE_ROUTE = "/oauth/authorize";
   private static final String TOKEN_ROUTE = "/oauth/token";
   private String sso_url;
   private String client_id;
   private String client_secret;
   private String redirect_uri;
   private String scope;
   private String home_url;
   private String grant_type;
   private String app_url;
   private String logout;
   private String logout_url;
   private Boolean user_sync_allow;

   public String getCodeUrl() {
      return this.getSso_url() + "/oauth/authorize" + "?client_id=" + this.getClient_id() + "&client_secret=" + this.getClient_secret() + "&redirect_uri=" + this.getRedirect_uri() + "&response_type=code&scope=" + this.getScope();
   }

   public String getLoginUrl(String code) {
      return this.getSso_url() + "/oauth/token" + "?grant_type=" + this.getGrant_type() + "&client_id=" + this.getClient_id() + "&client_secret=" + this.getClient_secret() + "&code=" + code + "&redirect_uri=" + this.getRedirect_uri() + "&scope=" + this.getScope();
   }

}
