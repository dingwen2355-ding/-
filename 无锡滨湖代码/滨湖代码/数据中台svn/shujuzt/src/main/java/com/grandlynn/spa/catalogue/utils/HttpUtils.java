package com.grandlynn.spa.catalogue.utils;

import cn.hutool.core.collection.CollectionUtil;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
   private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
   private static final String OFFLINE_API_READER_CONTENT_TYPE = "Content-Type";
   private static final String OFFLINE_API_READER_CONTENT_TYPE_JSON = "application/json";
   private static final String OFFLINE_API_READER_CONTENT_TYPE_TEXT = "text/plain";
   private static final String OFFLINE_API_READER_CONTENT_TYPE_CHARSET = "; charset=utf-8";
   private static OkHttpClient client;

   public static void initHttpClient(Boolean scheme, Integer timeout) {
      try {
         if (scheme) {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init((KeyManager[])null, getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            client = (new OkHttpClient.Builder()).connectTimeout((long)timeout, TimeUnit.SECONDS).readTimeout((long)timeout, TimeUnit.SECONDS).writeTimeout((long)timeout, TimeUnit.SECONDS).sslSocketFactory(sslSocketFactory, (X509TrustManager)Objects.requireNonNull(getX509TrustManager())).hostnameVerifier(getHostnameVerifier()).build();
         } else {
            client = (new OkHttpClient.Builder()).connectTimeout((long)timeout, TimeUnit.SECONDS).readTimeout((long)timeout, TimeUnit.SECONDS).writeTimeout((long)timeout, TimeUnit.SECONDS).build();
         }

      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public static String get(String url, Map<String, String> headers) {
      Request request = (new Request.Builder()).url(url).headers(mapToHeader(headers)).build();
      return execute(request);
   }

   public static String postJson(String url, Map<String, String> headers, String jsonBody) {
      log.info("post请求json：{}", jsonBody);
      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
      RequestBody body = RequestBody.create(JSON, jsonBody);
      Request request = (new Request.Builder()).url(url).headers(mapToHeader(headers)).post(body).build();
      return execute(request);
   }

   public static String postFormData(String url, Map<String, String> headers, String raw) {
      MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");
      RequestBody formBody = (new FormBody.Builder()).add("username", "wxslj").add("password", "wxslj@2023").build();
      Request request = (new Request.Builder()).url(url).headers(mapToHeader(headers)).post(formBody).build();
      return execute(request);
   }

   private static String execute(Request request) {
      Response response = null;
      ResponseBody responseBody = null;

      String var4;
      try {
         response = client.newCall(request).execute();
         if (!response.isSuccessful()) {
            return null;
         }

         String contentType = response.headers().get("Content-Type");
         responseBody = response.body();
         var4 = responseBody.string();
      } catch (Exception var8) {
         log.error("http request error:", var8);
         throw new RuntimeException(var8);
      } finally {
         if (Objects.nonNull(responseBody)) {
            responseBody.close();
         }

         if (Objects.nonNull(response)) {
            response.close();
         }

      }

      return var4;
   }

   private static TrustManager[] getTrustManagers() {
      return new TrustManager[]{new X509TrustManager() {
         public void checkClientTrusted(X509Certificate[] chain, String authType) {
         }

         public void checkServerTrusted(X509Certificate[] chain, String authType) {
         }

         public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
         }
      }};
   }

   private static X509TrustManager getX509TrustManager() {
      return new X509TrustManager() {
         public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
         }

         public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
         }

         public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
         }
      };
   }

   private static HostnameVerifier getHostnameVerifier() {
      return (s, sslSession) -> {
         return true;
      };
   }

   private static Headers mapToHeader(Map<String, String> map) {
      if (CollectionUtil.isNotEmpty(map)) {
         Headers.Builder builder = new Headers.Builder();
         Iterator var2 = map.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var2.next();
            String name = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (value != null && value.length() != 0) {
               builder.add(name, value);
            }
         }

         return builder.build();
      } else {
         return (new Headers.Builder()).build();
      }
   }
}
