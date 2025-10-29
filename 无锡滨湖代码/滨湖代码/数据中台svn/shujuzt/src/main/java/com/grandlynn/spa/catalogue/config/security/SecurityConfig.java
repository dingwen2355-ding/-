package com.grandlynn.spa.catalogue.config.security;

import com.grandlynn.spa.catalogue.security.RedisTokenProvider;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class SecurityConfig {
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Value("${server.token.timeoutInseconds:7200}")
   private long tokenValidityInseconds;
   @Value("${server.token.perfix:platform}")
   private String perfix;

   @Bean
   public TokenProvider tokenProvider() {
      return new RedisTokenProvider(this.redisTemplate, this.tokenValidityInseconds, this.perfix);
   }

   @Bean
   public Object bootstrap(@Autowired TokenProvider provider) {
      SecurityUtil.setTokenProvider(provider);
      return new Object();
   }
}
