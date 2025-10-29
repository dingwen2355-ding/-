package com.jslc.common.hsm;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

/**
 *
 * */
public class TencentAuthorization {
    private String signedAlg = "TC3-HMAC-SHA256";

    public String getAccessKey() {
        return accessKey;
    }

    private String accessKey;
    private String signedDate;

    private String serviceName = "kms";
    private String requestType = "tc3_request";
    private String signedHeaders = "content-type;host";
    private String signedValue;
    private static final Charset UTF8;
    private static final int timeSpan = 5*60; // 5分钟

    static {
        UTF8 = StandardCharsets.UTF_8;
    }

    private String host;
    private String timestamp;

    public TencentAuthorization(String host, String timestamp, String accessKey){
        this.host = host;
        this.timestamp = timestamp;
        this.accessKey = accessKey;
        LocalDate today = LocalDate.now();
        this.signedDate = today.toString();
    }

    public static String sha256Hex(String s) throws Exception {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(s.getBytes(UTF8));
            return DatatypeConverter.printHexBinary(d).toLowerCase();
        } catch (NoSuchAlgorithmException var3) {
            //throw new Exception("SHA-256 is not supported." , var3.getMessage());
        }
        return null;

    }

    public static String sha256Hex(byte[] b) throws Exception {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(b);
            return DatatypeConverter.printHexBinary(d).toLowerCase();
        } catch (NoSuchAlgorithmException var3) {
            //throw new Exception("SHA-256 is not supported." , var3.getMessage());
        }
        return null;
    }

    public static byte[] hmac256(byte[] key, String msg) throws Exception {
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
            try {
                mac.init(secretKeySpec);
            } catch (InvalidKeyException var5) {
                //throw new Exception(var5.getClass().getName() , var5.getMessage());
            }
            return mac.doFinal(msg.getBytes(UTF8));
        } catch (NoSuchAlgorithmException var6) {
            //throw new Exception("HmacSHA256 is not supported." , var6.getMessage());
        }
        return  null;
    }


    private String[] splitString(String in, String sp) {
        return in.split(sp);
    }

    private void parserSignature(String in) throws Exception {
        String[] sourceArray = splitString(in, "=");
        if (sourceArray.length != 2) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization " + in);
        }

        if (!sourceArray[0].trim().equals("Signature")) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization value " + sourceArray[0]);
        }

        this.signedValue = sourceArray[1].trim();
        if (this.signedValue.length() != 64) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization parameter signature value " + this.signedValue);
        }
    }

    // SignedHeaders=content-type;host
    private void parserSignHeaders(String in) throws Exception {
        String[] sourceArray = splitString(in, "=");
        if (sourceArray.length != 2) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization " + in);
        }

        if (!sourceArray[0].trim().equals("SignedHeaders")) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization value " + sourceArray[0]);
        }

        if (!sourceArray[1].contains("host")) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization the host does not exist");
        }

        if (!sourceArray[1].contains("content-type")) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization the content-type does not exist");
        }
        this.signedHeaders = sourceArray[1];
    }

    // TC3-HMAC-SHA256 Credential=AKIDjEZdV7nx41i9KGLP5WhdJir65rOfJp2x/2021-05-18/kms/tc3_request
    private void parserAuthInfo(String in) throws Exception {
        String[] sourceArray = splitString(in, "/");
        if (sourceArray.length != 4) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization " + in);
        }

        this.signedDate = sourceArray[1].trim();

        this.serviceName = sourceArray[2].trim();

        this.requestType = sourceArray[3].trim();

        //  TC3-HMAC-SHA256 Credential=AKIDjEZdV7nx41i9KGLP5WhdJir65rOfJp2x
        String[] sourceArray1 = splitString(sourceArray[0], " ");
        if (sourceArray1.length != 2) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization " + sourceArray[0].trim());
        }
        this.signedAlg = sourceArray1[0].trim();

        // Credential=AKIDjEZdV7nx41i9KGLP5WhdJir65rOfJp2x
        String[] sourceArray2 = splitString(sourceArray1[1], "=");
        if (sourceArray2.length != 2) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization " + sourceArray1[0].trim());
        }

        if (!sourceArray2[0].equals("Credential")) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization value " + sourceArray2[0]);
        }

        this.accessKey = sourceArray2[1].trim();

    }

    public void parser(String authString) throws Exception {
        // authString: TC3-HMAC-SHA256 Credential=AKIDjEZdV7nx41i9KGLP5WhdJir65rOfJp2x/2021-05-18/kms/tc3_request, SignedHeaders=content-type;host, Signature=bb06081fcf63e7f7970589d15ba408d91fc0290196f85cdc8af72137aa6ad74e;
        String[] sourceArray = splitString(authString, ",");
        if (sourceArray.length != 3) {
            //throw new Exception(TenErrorCode.InvalidAuthorization, "invalid authorization " + authString);
        }
        parserAuthInfo(sourceArray[0]);
        parserSignHeaders(sourceArray[1]);
        parserSignature(sourceArray[2]);
    }

    public String buildCanonicalRequest(String hashedRequestPayload) throws Exception {
        String canonicalRequest = "";
        String canonicalUri = "/";
        String httpRequestMethod;
        String canonicalQueryString;

        httpRequestMethod = "POST";
        canonicalQueryString = "";
        String canonicalHeaders = "content-type:"+"application/json" + "\n" + "host:" + host + "\n";
        canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + this.signedHeaders + "\n" + hashedRequestPayload;
        return canonicalRequest;
    }

    public String buildStringToSign(String hashedCanonicalRequest) throws Exception {
        String credentialScope = this.signedDate + "/" + this.serviceName + "/" + this.requestType;
        return "TC3-HMAC-SHA256\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
    }

    public String getSignStr(String secretKey, byte[] body, int bodyLen) throws Exception {


        byte[] requestPayload = new byte[bodyLen];
        System.arraycopy(body, 0, requestPayload, 0, bodyLen);
        String hashedRequestPayload = sha256Hex(requestPayload);

        // 拼接canonicalRequest，hash
        String canonicalRequest = buildCanonicalRequest(hashedRequestPayload);
        String hashedCanonicalRequest = sha256Hex(canonicalRequest.getBytes(StandardCharsets.UTF_8));

        // 拼接stringToSign
        String stringToSign = buildStringToSign(hashedCanonicalRequest);

        // TC3+ secretKey  date hmac =secretDate
        byte[] secretDate = hmac256(("TC3" + secretKey).getBytes(StandardCharsets.UTF_8), this.signedDate);

        // hmac(secretDate, service(kms))  = secretService
        byte[] secretService = hmac256(secretDate, this.serviceName);

        // hmac(secretService, tc3_request)   = secretSigning
        byte[] secretSigning = hmac256(secretService, this.requestType);

        // hmac(secretSigning, strToSign)     = signature
        byte[] signBytes = hmac256(secretSigning, stringToSign);

        return DatatypeConverter.printHexBinary(signBytes).toLowerCase();
    }


}
