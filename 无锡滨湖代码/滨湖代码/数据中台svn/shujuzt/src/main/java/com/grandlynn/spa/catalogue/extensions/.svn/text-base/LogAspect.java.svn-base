package com.grandlynn.spa.catalogue.extensions;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSON;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** @deprecated */
@Deprecated
public class LogAspect {
   private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
   private static final String TENANT_KEY = "tenantId";
   private static final String TOKEN_KEY = "userToken";
   @Autowired
   private AppRuntimeEnv appRuntimeEnv;

   @Pointcut("execution (* com.grandlynn.spa.catalogue.controller..*.*(..))")
   public void apiLogAop() {
   }

   @Around("apiLogAop()")
   public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
      log.info("日志统一打印 ↓ ↓ ↓ ↓ ↓ ↓ {}.{}() start ↓ ↓ ↓ ↓ ↓ ↓,参数:\n{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), this.argsToString(point.getArgs())});

      TimeInterval timer = DateUtil.timer();
      Object response = null;
      if (RequestContextHolder.getRequestAttributes() != null) {
         ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
         if (null != servletRequestAttributes) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            this.appRuntimeEnv.setTenantId(this.getParam(request, "tenantId"));
            this.appRuntimeEnv.setToken(this.getParam(request, "userToken"));
         }
      }

      try {
         response = point.proceed();
      } catch (Exception var8) {
         log.error("", var8);

         log.info("日志统一打印 ↑ ↑ ↑ ↑ ↑ ↑ {}.{}() end ↑ ↑ ↑ ↑ ↑ ↑,响应时间:{}毫秒,响应内容:\n{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), timer.interval()});
         throw var8;
      }
      log.info("日志统一打印 ↑ ↑ ↑ ↑ ↑ ↑ {}.{}() end ↑ ↑ ↑ ↑ ↑ ↑,响应时间:{}毫秒,响应内容:\n{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), timer.interval(), this.argsToString(response)});
      return response;
   }

   private String argsToString(Object object) {
      try {
         return JSON.toJSONString(object);
      } catch (Exception var3) {
         log.error("", var3);
         return String.valueOf(object);
      }
   }

   private String getParam(HttpServletRequest request, String param) {
      String[] reqParam = request.getParameterValues(param);
      return reqParam != null && reqParam.length >= 1 ? reqParam[0] : null;
   }
}
