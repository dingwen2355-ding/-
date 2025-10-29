package com.grandlynn.spa.catalogue.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grandlynn.spa.catalogue.extensions.AppRuntimeEnv;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** @deprecated */
@Component
@Deprecated
public class DefaultSpringMvcInterceptor extends HandlerInterceptorAdapter {
   private static final Logger log = LoggerFactory.getLogger(DefaultSpringMvcInterceptor.class);
   @Autowired
   private AppRuntimeEnv appRuntimeEnv;
   private static final String USERNAME = "username";
   private static final String TOKEN = "token";
   private static final String TENANTID = "tenantId";
   private static final String APPID = "appId";
   private static final String REQUESTID = "requestId";

   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      Object username = this.getParameterVal(request, "username");
      Object token = this.getParameterVal(request, "token");
      Object tenantId = this.getParameterVal(request, "tenantId");
      Object appId = this.getParameterVal(request, "appId");
      Object requestId = this.getParameterVal(request, "requestId");
      if (ObjectUtils.allNotNull(new Object[]{username})) {
         this.appRuntimeEnv.setUsername((String)username);
      }

      if (ObjectUtils.allNotNull(new Object[]{token})) {
         this.appRuntimeEnv.setToken((String)token);
      }

      if (ObjectUtils.allNotNull(new Object[]{tenantId})) {
         this.appRuntimeEnv.setTenantId((String)tenantId);
      }

      if (ObjectUtils.allNotNull(new Object[]{appId})) {
         this.appRuntimeEnv.setAppId(Long.valueOf(String.valueOf(appId)));
      }

      if (ObjectUtils.allNotNull(new Object[]{requestId})) {
         this.appRuntimeEnv.setRequestId((String)requestId);
      }

      return true;
   }

   private Object getParameterVal(HttpServletRequest request, String key) {
      Object value = request.getParameter(key);
      return ObjectUtils.allNotNull(new Object[]{value}) ? value : this.getPostParams(request, key);
   }

   private Object getPostParams(HttpServletRequest request, String key) {
      Object value = null;

      try {
         byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
         String body = new String(bodyBytes, request.getCharacterEncoding());
         if (StringUtils.isNotBlank(body)) {
            Object obj = JSON.parse(body);
            if (obj instanceof JSONObject) {
               Map<String, Object> map = (Map)JSON.parseObject(body, Map.class);
               value = map.get(key);
            }

            if (obj instanceof JSONArray) {
               JSONArray array = JSON.parseArray(body);
               Optional<Object> optional = array.stream().findFirst();
               if (optional.isPresent()) {
                  String jsonStr = JSON.toJSONString(optional.get());
                  Map<String, Object> map = (Map)JSON.parseObject(jsonStr, Map.class);
                  return map.get(key);
               }
            }

            return value;
         } else {
            return null;
         }
      } catch (Exception var11) {
         log.error("拦截器解析请求参数appId、tenantId、username异常{}", var11.getMessage());
         return null;
      }
   }

   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      super.afterCompletion(request, response, handler, ex);
      this.appRuntimeEnv.clearContext();
   }
}
