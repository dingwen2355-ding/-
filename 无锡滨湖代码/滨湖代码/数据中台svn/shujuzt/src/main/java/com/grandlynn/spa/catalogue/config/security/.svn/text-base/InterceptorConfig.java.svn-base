package com.grandlynn.spa.catalogue.config.security;

import com.grandlynn.spa.catalogue.security.SecurityInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
   private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);

   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new SecurityInterceptor()).addPathPatterns(new String[]{"/**"}).excludePathPatterns(new String[]{"/home/total"});
   }
}
