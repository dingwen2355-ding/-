package com.znv.manage.common.encrypt;

import java.util.Base64;

public class Base64Util {

    /**
     * Base64加密
     */
    public static String encodeBase64(String str) {
        byte[] bytes = str.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }

    /**
     * Base64解密
     */
    public static String decoderBase64(String encoded) {
        //Base64 解密
        byte[] decoded = Base64.getDecoder().decode(encoded);
        String decodeStr = new String(decoded);
        return decodeStr;
    }
}
