package com.znv.manage.auth.service;

import javax.servlet.http.HttpSession;

/**
 * @Auther: yf
 * @Date: 2020/7/1
 * @Description:
 */
public interface ApiTokenService {

    // 存储token
    void storeToken(String token, HttpSession session);

    // 校验token有效性
    boolean checkToken(String token, HttpSession session);
}
