package com.grandlynn.spa.catalogue.extensions;

import org.springframework.stereotype.Component;

@Component
public class AppRuntimeEnv {
   private ThreadLocal<String> tenantId = ThreadLocal.withInitial(() -> {
      return null;
   });
   private ThreadLocal<Long> appId = ThreadLocal.withInitial(() -> {
      return null;
   });
   private ThreadLocal<String> token = ThreadLocal.withInitial(() -> {
      return null;
   });
   private ThreadLocal<String> username = ThreadLocal.withInitial(() -> {
      return "ANONYMOUS";
   });
   private ThreadLocal<String> requestId = ThreadLocal.withInitial(() -> {
      return null;
   });

   public void setTenantId(String tenantId) {
      this.tenantId.set(tenantId);
   }

   public void setToken(String token) {
      this.token.set(token);
   }

   public String getTenantId() {
      return (String)this.tenantId.get();
   }

   public String getToken() {
      return (String)this.token.get();
   }

   public void setUsername(String username) {
      this.username.set(username);
   }

   public String getUsername() {
      return (String)this.username.get();
   }

   public void setRequestId(String requestId) {
      this.requestId.set(requestId);
   }

   public String getRequestId() {
      return (String)this.requestId.get();
   }

   public Long getAppId() {
      return (Long)this.appId.get();
   }

   public void setAppId(Long appId) {
      this.appId.set(appId);
   }

   public void clearContext() {
      this.appId.remove();
      this.tenantId.remove();
      this.requestId.remove();
      this.token.remove();
      this.username.remove();
   }
}
