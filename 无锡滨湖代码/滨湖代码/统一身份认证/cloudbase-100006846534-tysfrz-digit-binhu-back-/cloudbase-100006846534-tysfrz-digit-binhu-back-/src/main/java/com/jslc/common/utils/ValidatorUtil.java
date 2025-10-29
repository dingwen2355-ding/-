package com.jslc.common.utils;

import java.util.regex.Pattern;

/**
 *@program: szbh-code
 *@ClassName: ValidatorUtil
 *@description: 验证工具类
 *@author: zhengyongbiao
 *@create: 2024-05-29 13:13
 */
public class ValidatorUtil {
    //验证手机号码
    private static final Pattern CHINA_MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    // 验证座机号码
    private static final Pattern CHINA_TELEPHONE_PATTERN = Pattern.compile("^\\(?(\\d{3,4})\\)?[-]?\\d{7,8}$");
    //邮箱验证
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$");

    /**
     *@创建人 zhengyongbiao
     *@描述 验证是否是手机号码
     *@创建时间 2024/5/29 13:39
     */
    public static boolean isValidMobileNum(String number) {
        return CHINA_MOBILE_PATTERN.matcher(number).matches();
    }
    /**
     *@创建人 zhengyongbiao
     *@描述 验证座机号码
     *@创建时间 2024/5/29 13:39
     */
    public static boolean isValidTelephone(String number) {
        return CHINA_TELEPHONE_PATTERN.matcher(number).matches();
    }

    /**
     *@创建人 zhengyongbiao
     *@描述 验证手机号码
     *@创建时间 2024/5/29 13:43
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }
}
