package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.grandlynn.spa.catalogue.config.SsoLoginConfig;
import com.grandlynn.spa.catalogue.dto.sso.AppDepList;
import com.grandlynn.spa.catalogue.dto.sso.AppUserInfoDTO;
import com.grandlynn.spa.catalogue.service.AuthAppUserService;
import com.grandlynn.util.extension.ApplicationException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthAppUserServiceImpl implements AuthAppUserService {
   private static final Logger log = LogManager.getLogger(AuthAppUserServiceImpl.class);
   @Resource
   private SsoLoginConfig ssoLoginConfig;
   private static final String GET_TOKEN = "/api/squirrel-szbh/rest/getToken";
   private static final String USER_LIST = "/api/squirrel-szbh/rest/userList";
   private static final String DEP_LIST = "/api/squirrel-szbh/rest/departmentList";
   private static final String USER_INFO = "/api/squirrel-szbh/rest/userInfo";
   @Resource
   private RedisTemplate<String, Object> redisTemplate;

   public String getAppToken() {
      String appKey = "app-auth:" + this.ssoLoginConfig.getClient_id();
      Object oldToken = this.redisTemplate.opsForValue().get(appKey);
      if (oldToken != null) {
         return oldToken.toString();
      } else {
         Object token = this.getRequest(this.getTokenUrl());
         this.redisTemplate.opsForValue().set(appKey, token.toString(), 1800L, TimeUnit.SECONDS);
         return token.toString();
      }
   }

   public List<AppUserInfoDTO> appUserList(String depId) {
      String appToken = this.getAppToken();
      String userListUrl = this.getUserListUrl(appToken, depId);
      Object userListStr = this.getRequest(userListUrl);
      return JSONUtil.toList(JSONUtil.parseArray(userListStr.toString()), AppUserInfoDTO.class);
   }

   public AppUserInfoDTO appUserInfo(String account) {
      String appToken = this.getAppToken();
      String userInfoUrl = this.getUserInfoUrl(appToken, account);
      Object userInfoStr = this.getRequest(userInfoUrl);
      return (AppUserInfoDTO)JSONUtil.toBean(userInfoStr.toString(), AppUserInfoDTO.class);
   }

   public List<AppDepList> departmentList(String depId) {
      String appToken = this.getAppToken();
      String departmentListUrl = this.getDepartmentListUrl(appToken, depId);
      Object departmentListStr = this.getRequest(departmentListUrl);
      return JSONUtil.toList(JSONUtil.parseArray(departmentListStr.toString()), AppDepList.class);
   }

   private String getTokenUrl() {
      return this.ssoLoginConfig.getApp_url() + "/api/squirrel-szbh/rest/getToken" + "?appId=" + this.ssoLoginConfig.getClient_id() + "&appSecret=" + this.ssoLoginConfig.getClient_secret();
   }

   private String getUserListUrl(String token, String depId) {
      String url = this.ssoLoginConfig.getApp_url() + "/api/squirrel-szbh/rest/userList" + "?appId=" + this.ssoLoginConfig.getClient_id() + "&token=" + token;
      if (depId != null) {
         url = url + "&id=" + depId;
      }

      return url;
   }

   private String getUserInfoUrl(String token, String account) {
      return this.ssoLoginConfig.getApp_url() + "/api/squirrel-szbh/rest/userInfo" + "?appId=" + this.ssoLoginConfig.getClient_id() + "&token=" + token + "&account=" + account;
   }

   private String getDepartmentListUrl(String token, String depId) {
      String depUrl = this.ssoLoginConfig.getApp_url() + "/api/squirrel-szbh/rest/departmentList" + "?appId=" + this.ssoLoginConfig.getClient_id() + "&token=" + token + "&noFetchChild=1";
      if (depId != null) {
         depUrl = depUrl + "&id=" + depId;
      }

      return depUrl;
   }

   public Object getRequest(String url) {
      HttpRequest rq = HttpRequest.get(url);
      rq.timeout(5000);
      HttpResponse response = rq.execute();
      if (200 != response.getStatus()) {
         throw new ApplicationException("获取子应用数据失败");
      } else {
         String tokenInfo = response.body();
         JSONObject jsonObject = JSONUtil.parseObj(tokenInfo);
         String code = jsonObject.getStr("code");
         if (!"200".equals(code)) {
            throw new ApplicationException("获取子应用数据失败," + jsonObject.getStr("message"));
         } else {
            return jsonObject.getObj("data");
         }
      }
   }
}
