package com.grandlynn.spa.catalogue.config.security;

import com.grandlynn.spa.catalogue.security.InitUserFilter;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
   @Bean
   public Filter initUserFilter() {
      return new InitUserFilter();
   }

   @Bean
   public FilterRegistrationBean initUserFilterRegistrationBean() {
      FilterRegistrationBean registrationBean = new FilterRegistrationBean();
      registrationBean.setFilter(this.initUserFilter());
      registrationBean.addUrlPatterns(new String[]{"/*"});
      registrationBean.setName("initUserFilter");
      registrationBean.setOrder(0);
      return registrationBean;
   }
}
