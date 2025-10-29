package com.znv.manage.service;

/**
 * @author 6x7
 * @date 2022/06/30 09:58
 * @description
 */
public interface AccessGovWeChatService {

    /**
     * 获取token
     *
     * @return
     */
    String getToken();

/*    *//**
     * 1小时获取一次令牌token
     *//*
    void refreshToken();*/

    /**
     * 获取登录用户统一用户编码(userId)
     *
     * @param code
     * @return
     */
    String getUserIdByCode(String code);

    /**
     * 根据userId获取用户手机号码
     *
     * @param userId
     * @return
     */
    String getUserMobile(String userId);

}
