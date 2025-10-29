package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

public interface WXService {

    String login();

    String getAccessToken();

    Result getWxURl(String userId,String precinctId);
}
