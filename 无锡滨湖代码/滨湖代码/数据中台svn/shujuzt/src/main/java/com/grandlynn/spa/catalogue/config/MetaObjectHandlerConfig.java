package com.grandlynn.spa.catalogue.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.extensions.AppRuntimeEnv;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
   private static final Logger log = LoggerFactory.getLogger(MetaObjectHandlerConfig.class);
   @Autowired
   private AppRuntimeEnv appRuntimeEnv;

   public void insertFill(MetaObject metaObject) {
      SysAdminUser user = SecurityUtil.currentUser();
      this.setFieldValByName("createdBy", this.appRuntimeEnv.getUsername(), metaObject);
      this.setFieldValByName("updatedBy", this.appRuntimeEnv.getUsername(), metaObject);
      if (null != user) {
         log.debug("MetaObjectHandlerConfig===tenantId={}；appId={}", user.getTenantId(), user.getAppId());
         this.setFieldValByName("createdBy", user.getName(), metaObject);
         this.setFieldValByName("updatedBy", user.getName(), metaObject);
      }

      this.setFieldValByName("createdTime", new Date(), metaObject);
      this.setFieldValByName("updatedTime", new Date(), metaObject);
      this.setFieldValByName("columnId", UUID.randomUUID().toString(), metaObject);
   }

   public void updateFill(MetaObject metaObject) {
      this.setFieldValByName("updatedBy", this.appRuntimeEnv.getUsername(), metaObject);
      SysAdminUser user = SecurityUtil.currentUser();
      if (null != user) {
         this.setFieldValByName("updatedBy", user.getName(), metaObject);
      }

      this.setFieldValByName("updatedTime", new Date(), metaObject);
   }
}
