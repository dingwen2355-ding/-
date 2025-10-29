package com.grandlynn.spa.catalogue.config;

import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
   private static final String basePackage = "com.grandlynn.spa.catalogue.controller";
   @Value("${swagger.host}")
   private String swaggerHost;

   @Bean
   public Docket createRestApi() {
      return (new Docket(DocumentationType.OAS_30)).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
   }

   private ApiInfo apiInfo() {
      return (new ApiInfoBuilder()).title("目录系统 Restful api").contact(new Contact("deepexi", "http://www.deepexi.com", "kangbeibei@deepexi.com")).version("1.1.0").description("目录系统api管理").build();
   }

   private List<ResponseMessage> responseMessage() {
      List<ResponseMessage> responseMessageList = new ArrayList();
      Arrays.stream(ResultCodeEnum.values()).forEach((em) -> {
         responseMessageList.add((new ResponseMessageBuilder()).code(Integer.parseInt(em.getCode())).message(em.getMsg()).responseModel(new ModelRef(em.getMsg())).build());
      });
      return responseMessageList;
   }
}
