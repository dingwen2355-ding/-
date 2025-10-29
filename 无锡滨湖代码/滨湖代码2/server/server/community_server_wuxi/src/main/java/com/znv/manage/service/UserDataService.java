package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import javax.servlet.http.HttpServletRequest;

public interface UserDataService {

    String getToken();

    Result refreshToken(String refreshToken);

    Result getUserInfo(String token);

}
