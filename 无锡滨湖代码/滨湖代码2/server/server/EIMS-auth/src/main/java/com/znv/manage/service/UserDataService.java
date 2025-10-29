package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import javax.servlet.http.HttpServletRequest;

public interface UserDataService {
    Result getUserList();

    Result getPrecinctList();

    /**
     * @param account 账号
     * @return
     */
    Result getOneUser(String account);

    String getToken();

    Result sendMessage(String mobile, String msg);

    /**
     * 获取token
     *
     * @param code    授权码
     * @param request
     * @return
     */
    Result oAuthToken(String code, String requestAppId, String requestAppSecret, String requestRedirectUrl, HttpServletRequest request);

    Result refreshToken(String refreshToken);

    Result getUserInfo(String token);


}
