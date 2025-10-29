package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogueCategoryRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportPresSystemRequest;
import com.grandlynn.spa.catalogue.domain.response.report.ReportBaseResponse;
import com.grandlynn.spa.catalogue.domain.response.report.ReportCatalogueCategoryResponse;
import com.grandlynn.spa.catalogue.domain.response.report.ReportCatalogueResponse;
import com.grandlynn.spa.catalogue.domain.response.report.ReportPresSystemResponse;
import com.grandlynn.spa.catalogue.service.ReportCatalogueService;
import com.grandlynn.spa.catalogue.utils.HttpUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportCatalogueServiceImpl implements ReportCatalogueService {
   private static final Logger log = LoggerFactory.getLogger(ReportCatalogueServiceImpl.class);
   @Value("${wuxi.city_platform.url}")
   private String cityPlatformUrl;
   @Value("${wuxi.city_platform.appkey}")
   private String appkey;
   @Value("${wuxi.city_platform.appSecret}")
   private String appSecret;
   @Value("${wuxi.city_platform.ssoUrl}")
   private String ssoUrl;
   @Value("${wuxi.city_platform.enable-report}")
   private Boolean enableReport;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;

   public String catalogueCategoryRegister(ReportCatalogueCategoryRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String categoryRegisterUrl = "/catalogcategory/register";
         String response = HttpUtils.postJson(this.cityPlatformUrl + categoryRegisterUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", categoryRegisterUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode()) || "C-10".equals(baseResponse.getCode())) {
               ReportCatalogueCategoryResponse categoryResponse = (ReportCatalogueCategoryResponse)baseResponse.getData().toJavaObject(ReportCatalogueCategoryResponse.class);
               return categoryResponse.getCascadeguid();
            }
         }

         return null;
      }
   }

   public Boolean catalogueCategoryModify(ReportCatalogueCategoryRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String categoryModifyUrl = "/catalogcategory/modify";
         String response = HttpUtils.postJson(this.cityPlatformUrl + categoryModifyUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", categoryModifyUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode())) {
               return true;
            }
         }

         return false;
      }
   }

   public Boolean catalogueCategoryCancel(ReportCatalogueCategoryRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String categoryCancelUrl = "/catalogcategory/cancel";
         String response = HttpUtils.postJson(this.cityPlatformUrl + categoryCancelUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", categoryCancelUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode())) {
               return true;
            }
         }

         return false;
      }
   }

   public String presSystemRegister(ReportPresSystemRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String presSystemRegisterUrl = "/pressystem/register";
         String response = HttpUtils.postJson(this.cityPlatformUrl + presSystemRegisterUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", presSystemRegisterUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode()) || "P-29".equals(baseResponse.getCode())) {
               ReportPresSystemResponse categoryResponse = (ReportPresSystemResponse)baseResponse.getData().toJavaObject(ReportPresSystemResponse.class);
               return categoryResponse.getCascadeguid();
            }
         }

         return null;
      }
   }

   public Boolean presSystemModify(ReportPresSystemRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String presSystemModifyUrl = "/pressystem/modify";
         String response = HttpUtils.postJson(this.cityPlatformUrl + presSystemModifyUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", presSystemModifyUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode())) {
               return true;
            }
         }

         return false;
      }
   }

   public String catalogueRegister(ReportCatalogueRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String catalogueRegisterUrl = "/catalog/register";
         String response = HttpUtils.postJson(this.cityPlatformUrl + catalogueRegisterUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", catalogueRegisterUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode())) {
               ReportCatalogueResponse categoryResponse = (ReportCatalogueResponse)baseResponse.getData().toJavaObject(ReportCatalogueResponse.class);
               return categoryResponse.getCascadeguid();
            }
         }

         return null;
      }
   }

   public Boolean catalogueModify(ReportCatalogueRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String catalogueModifyUrl = "/catalog/modify";
         String response = HttpUtils.postJson(this.cityPlatformUrl + catalogueModifyUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", catalogueModifyUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode())) {
               return true;
            }
         }

         return false;
      }
   }

   public Boolean catalogueCancel(ReportCatalogueRequest request) {
      if (!this.enableReport) {
         return null;
      } else {
         request.setAppkey(this.appkey);
         Map<String, String> headers = new HashMap();
         headers.put("Authorization", "Bearer " + this.getToken());
         HttpUtils.initHttpClient(false, 5);
         String catalogueModifyUrl = "/catalog/cancel";
         String response = HttpUtils.postJson(this.cityPlatformUrl + catalogueModifyUrl, headers, JSON.toJSONString(request));
         if (null != response) {
            log.info("{}调用结果：{}", catalogueModifyUrl, response);
            ReportBaseResponse baseResponse = (ReportBaseResponse)JSON.parseObject(response, ReportBaseResponse.class);
            if ("1".equals(baseResponse.getCode())) {
               return true;
            }
         }

         return false;
      }
   }

   public String getToken() {
      try {
         Object tokeObj = this.redisTemplate.opsForValue().get("WUXI_TOKEN");
         if (ObjectUtils.isNotEmpty(tokeObj)) {
            return tokeObj.toString();
         }

         PostMethod postMethod = null;
         postMethod = new PostMethod(this.ssoUrl);
         postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
         NameValuePair[] data = new NameValuePair[]{new NameValuePair("grant_type", "client_credentials"), new NameValuePair("scope", "default"), new NameValuePair("client_id", this.appkey), new NameValuePair("client_secret", this.appSecret)};
         postMethod.setRequestBody(data);
         HttpClient httpClient = new HttpClient();
         httpClient.executeMethod(postMethod);
         String result = postMethod.getResponseBodyAsString();
         if (null != result) {
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject customObject = (JSONObject)jsonObject.get("custom");
            String token = customObject.get("access_token").toString();
            this.redisTemplate.opsForValue().set("WUXI_TOKEN", token, 1200L, TimeUnit.SECONDS);
            return token;
         }
      } catch (Exception var10) {
         log.info("请求异常" + var10.getMessage(), var10);
      }

      return null;
   }

   public static void main(String[] args) {
      String response = "{\"controls\":[],\"custom\":{\"access_token\":\"9672c439126490a946b1a50523c0e547\",\"refresh_token\":\"11a5d66cd02e859d10abc32dbd30686f\",\"jsessionid\":\"\",\"extendinfo\":\"\",\"expires_in\":\"1800\"},\"status\":{\"code\":\"1\"}}";
      if (null != response) {
         JSONObject jsonObject = JSON.parseObject(response);
         JSONObject customObject = (JSONObject)jsonObject.get("custom");
         System.out.println(customObject.get("access_token"));
      }

   }
}
