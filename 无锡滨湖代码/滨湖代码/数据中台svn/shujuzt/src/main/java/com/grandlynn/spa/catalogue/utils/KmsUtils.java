package com.grandlynn.spa.catalogue.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.grandlynn.util.extension.ApplicationException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class KmsUtils {
   private static final Logger log = LoggerFactory.getLogger(KmsUtils.class);
   private static final String url = "http://192.170.13.200:9080";
   private static final String host = "192.170.13.200:9080";
   private static final String ak = "AKCACDAJCGBHDEBDTSSA";
   private static final String sk = "M0tEQU85Uk45VFNDR0tPOA==";
   private static final String keyId = "4d62fb2f-6f38-473a-b67d-7df727d827e8";
   private static OkHttpClient client = getClient();

   public static String sm3Hmac(String plaintext) {
      if (StringUtils.isEmpty(plaintext)) {
         return null;
      } else {
         try {
            String text = Base64.getEncoder().encodeToString(plaintext.getBytes());
            DigestRequest digestRequest = new DigestRequest();
            digestRequest.setAlgorithm("SM3");
            digestRequest.setMessage(text);
            String body = JSONObject.toJSON(digestRequest).toString();
            JSONObject jsonObject = JSONObject.parseObject(post(url, body, getBaseHeader("Digest", body.getBytes())));
            DigestResponse digestResponse = (DigestResponse)jsonObject.getObject("Response", DigestResponse.class);
            return digestResponse.getDigest();
         } catch (Exception var6) {
            log.error("sm3Hmac异常:{}", var6);
            throw new ApplicationException("数据sm3Hmac失败");
         }
      }
   }

   public static void main(String[] args) {
      String plaintext = Base64.getEncoder().encodeToString("反倒是金克拉".getBytes());
      System.out.println("plaintext=" + plaintext);
      String body = null;
      JSONObject jsonObject = null;

      try {
         DigestRequest digestRequest = new DigestRequest();
         digestRequest.setAlgorithm("SM3");
         digestRequest.setMessage(plaintext);
         body = JSONObject.toJSON(digestRequest).toString();
         jsonObject = JSONObject.parseObject(post(url, body, getBaseHeader("Digest", body.getBytes())));
         DigestResponse digestResponse = (DigestResponse)jsonObject.getObject("Response", DigestResponse.class);
         System.out.println(JSONObject.toJSON(JSONObject.toJSON(digestResponse).toString()).toString());
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static Map<String, String> getBaseHeader(String command, byte[] body) throws Exception {
      String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
      TencentAuthorization auth = new TencentAuthorization(host, timestamp, sk);
      Map<String, String> headers = new HashMap();
      headers.put("X-TC-Action", command);
      headers.put("X-TC-Version", "2019-01-18");
      headers.put("X-TC-Timestamp", String.valueOf(timestamp));
      headers.put("content-type", "application/json");
      String authStr = "TC3-HMAC-SHA256 Credential="+ ak + "/" + LocalDate.now() + "/kms/tc3_request, SignedHeaders=content-type;host, Signature=" + auth.getSignStr(sk, body, body.length) + "";
      headers.put("Authorization", authStr);
      return headers;
   }

   public static String post(String url, String body, Map<String, String> headers) throws IOException {
      try {
         RequestBody requestBody = RequestBody.create(MediaType.parse("JSON"), body);
         Request.Builder builder = (new Request.Builder()).url(url).post(requestBody);
         return request(headers, builder);
      } catch (Exception var5) {
         log.error("Post Request error,", var5);
         throw var5;
      }
   }

   private static String request(Map<String, String> headers, Request.Builder builder) throws IOException {
      if (headers != null) {
         Iterator var2 = headers.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var2.next();
            builder.header((String)entry.getKey(), (String)entry.getValue());
         }
      }

      Request request = builder.build();
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
         ResponseBody body = response.body();
         if (body != null) {
            return response.body().contentType() != null && response.body().contentType().toString().equals("application/octet-stream") ? Base64.getEncoder().encodeToString(response.body().bytes()) : body.string();
         } else {
            return null;
         }
      } else {
         log.error("request failed response code is " + response.code());
         return null;
      }
   }

   private static OkHttpClient getClient() {
      OkHttpClient build = new OkHttpClient().newBuilder()
              .connectTimeout(5, TimeUnit.SECONDS)
              .callTimeout(5, TimeUnit.SECONDS)
              .writeTimeout(5, TimeUnit.SECONDS)
              .readTimeout(5, TimeUnit.SECONDS)
              .retryOnConnectionFailure(false)
              .sslSocketFactory(SSLUtils.getSSLSocketFactory(), SSLUtils.getX509TrustManager())
              .hostnameVerifier(SSLUtils.getHostnameVerifier())
              .build();
      return build;
   }

   public void closeClient() {
      client.dispatcher().cancelAll();
   }

   static class TencentAuthorization {
      private String signedAlg = "TC3-HMAC-SHA256";
      private String accessKey;
      private String signedDate;
      private String serviceName = "kms";
      private String requestType = "tc3_request";
      private String signedHeaders = "content-type;host";
      private String signedValue;
      private static final Charset UTF8;
      private static final int timeSpan = 300;
      private String host;
      private String timestamp;

      public String getAccessKey() {
         return this.accessKey;
      }

      public TencentAuthorization(String host, String timestamp, String accessKey) {
         this.host = host;
         this.timestamp = timestamp;
         this.accessKey = accessKey;
         LocalDate today = LocalDate.now();
         this.signedDate = today.toString();
      }

      public static String sha256Hex(String s) throws Exception {
         try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(s.getBytes(UTF8));
            return DatatypeConverter.printHexBinary(d).toLowerCase();
         } catch (NoSuchAlgorithmException var3) {
            return null;
         }
      }

      public static String sha256Hex(byte[] b) throws Exception {
         try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(b);
            return DatatypeConverter.printHexBinary(d).toLowerCase();
         } catch (NoSuchAlgorithmException var3) {
            return null;
         }
      }

      public static byte[] hmac256(byte[] key, String msg) throws Exception {
         try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());

            try {
               mac.init(secretKeySpec);
            } catch (InvalidKeyException var5) {
            }

            return mac.doFinal(msg.getBytes(UTF8));
         } catch (NoSuchAlgorithmException var6) {
            return null;
         }
      }

      private String[] splitString(String in, String sp) {
         return in.split(sp);
      }

      private void parserSignature(String in) throws Exception {
         String[] sourceArray = this.splitString(in, "=");
         if (sourceArray.length != 2) {
         }

         if (!sourceArray[0].trim().equals("Signature")) {
         }

         this.signedValue = sourceArray[1].trim();
         if (this.signedValue.length() != 64) {
         }

      }

      private void parserSignHeaders(String in) throws Exception {
         String[] sourceArray = this.splitString(in, "=");
         if (sourceArray.length != 2) {
         }

         if (!sourceArray[0].trim().equals("SignedHeaders")) {
         }

         if (!sourceArray[1].contains("host")) {
         }

         if (!sourceArray[1].contains("content-type")) {
         }

         this.signedHeaders = sourceArray[1];
      }

      private void parserAuthInfo(String in) throws Exception {
         String[] sourceArray = this.splitString(in, "/");
         if (sourceArray.length != 4) {
         }

         this.signedDate = sourceArray[1].trim();
         this.serviceName = sourceArray[2].trim();
         this.requestType = sourceArray[3].trim();
         String[] sourceArray1 = this.splitString(sourceArray[0], " ");
         if (sourceArray1.length != 2) {
         }

         this.signedAlg = sourceArray1[0].trim();
         String[] sourceArray2 = this.splitString(sourceArray1[1], "=");
         if (sourceArray2.length != 2) {
         }

         if (!sourceArray2[0].equals("Credential")) {
         }

         this.accessKey = sourceArray2[1].trim();
      }

      public void parser(String authString) throws Exception {
         String[] sourceArray = this.splitString(authString, ",");
         if (sourceArray.length != 3) {
         }

         this.parserAuthInfo(sourceArray[0]);
         this.parserSignHeaders(sourceArray[1]);
         this.parserSignature(sourceArray[2]);
      }

      public String buildCanonicalRequest(String hashedRequestPayload) throws Exception {
         String canonicalRequest = "";
         String canonicalUri = "/";
         String httpRequestMethod = "POST";
         String canonicalQueryString = "";
         String canonicalHeaders = "content-type:application/json\nhost:" + this.host + "\n";
         canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + this.signedHeaders + "\n" + hashedRequestPayload;
         return canonicalRequest;
      }

      public String buildStringToSign(String hashedCanonicalRequest) throws Exception {
         String credentialScope = this.signedDate + "/" + this.serviceName + "/" + this.requestType;
         return "TC3-HMAC-SHA256\n" + this.timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
      }

      public String getSignStr(String secretKey, byte[] body, int bodyLen) throws Exception {
         byte[] requestPayload = new byte[bodyLen];
         System.arraycopy(body, 0, requestPayload, 0, bodyLen);
         String hashedRequestPayload = sha256Hex(requestPayload);
         String canonicalRequest = this.buildCanonicalRequest(hashedRequestPayload);
         String hashedCanonicalRequest = sha256Hex(canonicalRequest.getBytes(StandardCharsets.UTF_8));
         String stringToSign = this.buildStringToSign(hashedCanonicalRequest);
         byte[] secretDate = hmac256(("TC3" + secretKey).getBytes(StandardCharsets.UTF_8), this.signedDate);
         byte[] secretService = hmac256(secretDate, this.serviceName);
         byte[] secretSigning = hmac256(secretService, this.requestType);
         byte[] signBytes = hmac256(secretSigning, stringToSign);
         return DatatypeConverter.printHexBinary(signBytes).toLowerCase();
      }

      public String getSignedAlg() {
         return this.signedAlg;
      }

      public String getSignedDate() {
         return this.signedDate;
      }

      public String getServiceName() {
         return this.serviceName;
      }

      public String getRequestType() {
         return this.requestType;
      }

      public String getSignedHeaders() {
         return this.signedHeaders;
      }

      public String getSignedValue() {
         return this.signedValue;
      }

      public String getHost() {
         return this.host;
      }

      public String getTimestamp() {
         return this.timestamp;
      }

      public void setSignedAlg(final String signedAlg) {
         this.signedAlg = signedAlg;
      }

      public void setAccessKey(final String accessKey) {
         this.accessKey = accessKey;
      }

      public void setSignedDate(final String signedDate) {
         this.signedDate = signedDate;
      }

      public void setServiceName(final String serviceName) {
         this.serviceName = serviceName;
      }

      public void setRequestType(final String requestType) {
         this.requestType = requestType;
      }

      public void setSignedHeaders(final String signedHeaders) {
         this.signedHeaders = signedHeaders;
      }

      public void setSignedValue(final String signedValue) {
         this.signedValue = signedValue;
      }

      public void setHost(final String host) {
         this.host = host;
      }

      public void setTimestamp(final String timestamp) {
         this.timestamp = timestamp;
      }

      static {
         UTF8 = StandardCharsets.UTF_8;
      }
   }

   static class DigestResponse {
      @SerializedName("Digest")
      @Expose
      private String Digest;
      @SerializedName("RequestId")
      @Expose
      private String RequestId;

      public DigestResponse() {
      }

      public String getDigest() {
         return this.Digest;
      }

      public String getRequestId() {
         return this.RequestId;
      }

      public void setDigest(final String Digest) {
         this.Digest = Digest;
      }

      public void setRequestId(final String RequestId) {
         this.RequestId = RequestId;
      }
   }

   static class DigestRequest {
      @SerializedName("Algorithm")
      @Expose
      private String Algorithm;
      @SerializedName("Message")
      @Expose
      private String Message;

      public DigestRequest() {
      }

      public String getAlgorithm() {
         return this.Algorithm;
      }

      public String getMessage() {
         return this.Message;
      }

      public void setAlgorithm(final String Algorithm) {
         this.Algorithm = Algorithm;
      }

      public void setMessage(final String Message) {
         this.Message = Message;
      }

   }
}
