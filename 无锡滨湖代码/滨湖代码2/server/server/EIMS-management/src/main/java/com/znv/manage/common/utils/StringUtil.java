package com.znv.manage.common.utils;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangye
 * @date 2018/7/24
 */
public class StringUtil {
    /**
     * ArrayList<String>转成String,逗号隔开
     *
     * @param arrayList
     * @return
     */
    public static String arrayList2String(ArrayList<String> arrayList) {
        String result = "";
        if (arrayList != null && arrayList.size() > 0) {
            for (String item : arrayList) {
                if (item.equals("")) {
                    result += item + ",";
                }
            }
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static boolean hasSpecialChar(String str) {
        String regEx = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_]|\n|\r|\t|\\s";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean verifyUserName(String str) {
        String regEx = "^[0-9a-zA-Z_]{2,20}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean verifyPhone(String str) {
        String regEx = "^\\d{0,20}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean notMD5(String str) {
        String regEx = "[^0123456789A-Fa-f]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 手机号格式校验正则
     */
    public static final String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    /**
     * 手机号脱敏筛选正则
     */
    public static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 手机号脱敏替换正则
     */
    public static final String PHONE_BLUR_REPLACE_REGEX = "$1****$2";

    /**
     * 手机号格式校验
     *
     * @param phone
     * @return
     */
    public static final boolean checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }

    /**
     * 手机号脱敏处理
     *
     * @param phone
     * @return
     */
    public static final String blurPhone(String phone) {
        boolean checkFlag = checkPhone(phone);
        if (!checkFlag) {
            throw new IllegalArgumentException("手机号格式不正确!");
        }
        return phone.replaceAll(PHONE_BLUR_REGEX, PHONE_BLUR_REPLACE_REGEX);
    }

}
