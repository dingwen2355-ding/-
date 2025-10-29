package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

public interface PhoneService {

    void login();

    void keepAlive();

    Result call(String callee);

    Result hungup(String callee);
}
