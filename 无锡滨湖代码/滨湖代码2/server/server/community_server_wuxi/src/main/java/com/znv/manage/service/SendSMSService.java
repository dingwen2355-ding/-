package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

public interface SendSMSService {

    Result sendSMS(String phones, String code, String appName, String action);

}
