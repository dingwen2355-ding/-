package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;

public interface SsoLoginService {

    public JSONObject sendSmsCode(String phoneNumber,String precinctId);
    public JSONObject login(String authType,String loginId,String auth,String precinctId);
    public JSONObject logout(String accessToken,String userId,String precinctId);
    public JSONObject checkToken(String accessToken,String userId,String precinctId);
}
