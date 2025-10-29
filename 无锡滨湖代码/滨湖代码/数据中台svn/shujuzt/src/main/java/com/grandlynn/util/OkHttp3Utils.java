//package com.grandlynn.util;
//
//import com.alibaba.fastjson.JSONObject;
//import java.io.IOException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.concurrent.TimeUnit;
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.KeyManager;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class OkHttp3Utils {
//   private static final byte[] LOCKER = new byte[0];
//   private static OkHttp3Utils mInstance;
//   private OkHttpClient mOkHttpClient;
//   private MediaType mediaType = MediaType.parse("application/json");
//
//   private OkHttp3Utils() {
//      OkHttpClient.Builder ClientBuilder = new OkHttpClient.Builder();
//      ClientBuilder.readTimeout(20L, TimeUnit.SECONDS);
//      ClientBuilder.connectTimeout(6L, TimeUnit.SECONDS);
//      ClientBuilder.writeTimeout(60L, TimeUnit.SECONDS);
//      ClientBuilder.sslSocketFactory(this.createSSLSocketFactory());
//      ClientBuilder.hostnameVerifier(new HostnameVerifier() {
//         public boolean verify(String hostname, SSLSession session) {
//            return true;
//         }
//      });
//      this.mOkHttpClient = ClientBuilder.build();
//   }
//
//   public static OkHttp3Utils getInstance() {
//      if (mInstance == null) {
//         synchronized(LOCKER) {
//            if (mInstance == null) {
//               mInstance = new OkHttp3Utils();
//            }
//         }
//      }
//
//      return mInstance;
//   }
//
//   public Response getDataSynFromNet(String url) {
//      Request.Builder builder = new Request.Builder();
//      Request request = builder.get().url(url).build();
//      Call call = this.mOkHttpClient.newCall(request);
//      return this.execute(call);
//   }
//
//   public Response postDataSynToNet(String url, JSONObject jsonObject) {
//      RequestBody body = RequestBody.create(this.mediaType, jsonObject.toString());
//      Request.Builder requestBuilder = new Request.Builder();
//      Request request = requestBuilder.post(body).url(url).build();
//      Call call = this.mOkHttpClient.newCall(request);
//      return this.execute(call);
//   }
//
//   private Response execute(Call call) {
//      Response response = null;
//
//      try {
//         response = call.execute();
//         if (!response.isSuccessful()) {
//            response.close();
//         }
//      } catch (IOException var4) {
//         var4.printStackTrace();
//      }
//
//      return response;
//   }
//
//   public void getDataAsynFromNet(String url, final MyNetCall myNetCall) {
//      Request.Builder builder = new Request.Builder();
//      Request request = builder.get().url(url).build();
//      Call call = this.mOkHttpClient.newCall(request);
//      call.enqueue(new Callback() {
//         public void onFailure(Call call, IOException e) {
//            myNetCall.failed(call, e);
//         }
//
//         public void onResponse(Call call, Response response) throws IOException {
//            myNetCall.success(call, response);
//         }
//      });
//   }
//
//   public void postDataAsynToNet(String url, JSONObject jsonObject, final MyNetCall myNetCall) {
//      RequestBody body = RequestBody.create(this.mediaType, jsonObject.toString());
//      Request.Builder requestBuilder = new Request.Builder();
//      Request request = requestBuilder.post(body).url(url).build();
//      Call call = this.mOkHttpClient.newCall(request);
//      call.enqueue(new Callback() {
//         public void onFailure(Call call, IOException e) {
//            myNetCall.failed(call, e);
//         }
//
//         public void onResponse(Call call, Response response) throws IOException {
//            myNetCall.success(call, response);
//         }
//      });
//   }
//
//   public SSLSocketFactory createSSLSocketFactory() {
//      SSLSocketFactory ssfFactory = null;
//
//      try {
//         SSLContext sc = SSLContext.getInstance("TLS");
//         sc.init((KeyManager[])null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
//         ssfFactory = sc.getSocketFactory();
//      } catch (Exception var3) {
//      }
//
//      return ssfFactory;
//   }
//
//   class TrustAllCerts implements X509TrustManager {
//      public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//      }
//
//      public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//      }
//
//      public X509Certificate[] getAcceptedIssuers() {
//         return new X509Certificate[0];
//      }
//   }
//
//   public interface MyNetCall {
//      void success(Call var1, Response var2) throws IOException;
//
//      void failed(Call var1, IOException var2);
//   }
//}
