package com.grandlynn.spa.catalogue.config;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** @deprecated */
@Deprecated
public class WebMvcConfig implements WebMvcConfigurer {
   private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);
   @Autowired
   private DefaultSpringMvcInterceptor defaultSpringMvcInterceptor;

   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      log.info("====>WebMvcConfig===>");
      registry.addResourceHandler(new String[]{"/img/**"}).addResourceLocations(new String[]{"classpath:/img/"});
      registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
      registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
   }

   public void addInterceptors(InterceptorRegistry registry) {
      List<String> excludePathPatterns = new ArrayList();
      excludePathPatterns.add("/swagger-ui.html");
      excludePathPatterns.add("/swagger-resources/**");
      excludePathPatterns.add("/error");
      excludePathPatterns.add("/webjars/**");
      registry.addInterceptor(this.defaultSpringMvcInterceptor).addPathPatterns(new String[]{"/**"}).excludePathPatterns(excludePathPatterns);
   }

   @Bean
   @Qualifier("dispatcherServlet")
   public DispatcherServlet dispatcherServlet() {
      return new MyDispatcherServlet();
   }
}
