package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

public interface DahuaService {

    String handleToken();

    String getToken();

    void keepAlive();

    void updateGps();

    Result syncDevice();
}
