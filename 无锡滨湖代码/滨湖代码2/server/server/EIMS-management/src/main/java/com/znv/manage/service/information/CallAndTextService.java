package com.znv.manage.service.information;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 6x7
 * @date 2022/05/31 17:32
 * @description
 */
public interface CallAndTextService {

    void setToken(String token);

    JSONObject login();

    JSONObject call(String region, String phoneNum, String microPhoneCaller);

    JSONObject hangUp(String phoneNum);

    JSONObject text(String phoneNum, String content);

    String getToken();
}
