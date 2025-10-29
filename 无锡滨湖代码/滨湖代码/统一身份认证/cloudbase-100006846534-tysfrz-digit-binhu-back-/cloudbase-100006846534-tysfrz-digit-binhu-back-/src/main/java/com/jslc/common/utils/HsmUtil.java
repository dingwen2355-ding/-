package com.jslc.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.jslc.common.hsm.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: szbh-code
 * @ClassName: HsmUtil
 * @description: 国密
 * @author: AlanMa
 * @create: 2024-05-07 15:37
 */
@Component
@Slf4j
@Data
public class HsmUtil {
    @Value("${hsm.host:''}")
    private String host;
    //服务地址
    @Value("${hsm.url:''}")
    private String url;
    @Value("${hsm.ak:''}")
    private String ak;
    @Value("${hsm.sk:''}")
    private String sk;
    //SM是否开启加密验证
    @Value("${hsm.openEncrypt:false}")
    private boolean openEncrypt;
    @Value("${hsm.keyId:''}")
    private String keyId;

    public String encryptSM3(String plaintext) {
        if(!openEncrypt){
            return plaintext;
        }

        String digest = null;
        try {
            //HMAC
            DigestRequest digestRequest = new DigestRequest();
            digestRequest.setAlgorithm("SM3");
            digestRequest.setMessage(Base64.getEncoder().encodeToString(plaintext.getBytes()));
            String body = JSONObject.toJSON(digestRequest).toString();
            JSONObject jsonObject = JSONObject.parseObject(post(url, body, getBaseHeader("Digest", body.getBytes())));
            log.info("SM3 response:{}", JSONObject.toJSON(jsonObject.toString()).toString());
            DigestResponse digestResponse = jsonObject.getObject("Response", DigestResponse.class);
            digest = digestResponse.getDigest();
        } catch (Exception e) {
            log.error("SM3 error:{}", e.getMessage());
            digest ="调用机密服务失败";
        }
        return digest;
    }
    /**
     * SM4加密
     * @param plaintext
     * @return
     */
    @Deprecated
    public String encryptDeprecatedSM4(String plaintext) {
        return plaintext;
    }

    /**
     * SM4解密
     * @param ciphertext
     * @return
     */
    @Deprecated
    public String decryptDeprecatedSM4(String ciphertext) {
        return ciphertext;
    }

    /**
     * SM4加密
     * @param plaintext
     * @return
     */
    @Deprecated
    public String encryptSM4(String plaintext) {
        if(!openEncrypt){
            return plaintext;
        }
        String digest = null;
        try {
            EncryptRequest encryptRequest = new EncryptRequest();
            encryptRequest.setKeyId(keyId);
            encryptRequest.setAlgorithm("SM4_ECB_PKCS7PADDING");
            encryptRequest.setPlaintext(Base64.getEncoder().encodeToString(plaintext.getBytes()));
            String body = JSONObject.toJSON(encryptRequest).toString();
            JSONObject jsonObject = JSONObject.parseObject(post(url,body,getBaseHeader("Encrypt",body.getBytes())));
            log.info("ENCRYPTSM4 response:{}", JSONObject.toJSON(jsonObject.toString()).toString());
            EncryptResponse encryptResponse = jsonObject.getObject("Response",EncryptResponse.class);
            digest = encryptResponse.getCiphertextBlob();
        } catch (Exception e) {
            log.error("ENCRYPTSM4 error:{}", e.getMessage());
        }
        return digest;
    }

    /**
     * SM4解密
     * @param ciphertext
     * @return
     */
    @Deprecated
    public String decryptSM4(String ciphertext) {
        if(!openEncrypt){
            return ciphertext;
        }
        String digest = null;
        try {
            DecryptRequest decryptRequest = new DecryptRequest();
            decryptRequest.setCiphertextBlob(ciphertext);
            String body = JSONObject.toJSON(decryptRequest).toString();
            JSONObject jsonObject = JSONObject.parseObject(post(url,body,getBaseHeader("Decrypt",body.getBytes())));
            DecryptResponse decryptResponse = jsonObject.getObject("Response",DecryptResponse.class);
            log.info("DECRYPTSM4 response:{}", JSONObject.toJSON(decryptResponse));
            digest = new String(Base64.getDecoder().decode(decryptResponse.getPlaintext()));
        } catch (Exception e) {
            log.error("DECRYPTSM4 error:{}", e.getMessage());
        }
        return digest;
    }

    private Map<String, String> getBaseHeader(String command, byte[] body) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        TencentAuthorization auth = new TencentAuthorization(host, timestamp, sk);
        Map<String, String> headers = new HashMap<>();
        headers.put("X-TC-Action", command);
        headers.put("X-TC-Version", "2019-01-18");
        headers.put("X-TC-Timestamp", String.valueOf(timestamp));
        headers.put("content-type", "application/json");
        String signStr = auth.getSignStr(sk, body, body.length);
//        String authStr = "TC3-HMAC-SHA256 Credential=" + ak + "/2024-05-07/kms/tc3_request, SignedHeaders=content-type;host, Signature=" + signStr + "";
        String authStr = "TC3-HMAC-SHA256 Credential=" + ak + "/" + DateUtil.formatDate(new Date()) + "/kms/tc3_request, SignedHeaders=content-type;host, Signature=" + signStr + "";
        headers.put("Authorization", authStr);
        return headers;
    }

    private String post(String url, String body, Map<String, String> headers) throws IOException {
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("JSON"), body);
            Request.Builder builder = new Request.Builder().url(url).post(requestBody);
            return request(headers, builder);
        } catch (Exception e) {
            log.error("Post Request error,", e);
            throw e;
        }
    }

    private String request(Map<String, String> headers, Request.Builder builder) throws IOException {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();
        Response response = getClient().newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                if (response.body().contentType() != null && response.body().contentType().toString().equals("application/octet-stream")) {
                    return Base64.getEncoder().encodeToString(response.body().bytes());
                }
                return body.string();
            }
            return null;
        } else {
            log.error("request failed response code is " + response.code());
            return null;
        }
    }

    private OkHttpClient getClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .sslSocketFactory(SSLUtil.getSSLSocketFactory(), SSLUtil.getX509TrustManager())
                .hostnameVerifier(SSLUtil.getHostnameVerifier()).build();
    }
}
