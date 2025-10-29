package com.znv.manage.application;

import com.znv.manage.controller.HuaweiController;
import com.znv.manage.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    private DahuaServiceImpl dahuaService;

    @Autowired
    private PhoneServiceImpl phoneService;

    @Autowired
    private UserGpsServiceImpl userGpsService;

    @Autowired
    private HuaweiController huaweiController;

    @Autowired
    private PublicOpinionServiceImpl publicOpinionService;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        /**
         * 舆情登陆
         */
        //publicOpinionService.setToken();

        //登录、认证，获取cookie
        // huaweiController.huaweiLogin();

        /**
         * 初始化大华token
         */
        //dahuaService.handleToken();

        /**
         * 大华 龙泉驿区单兵订阅gps
         */
        //dahuaService.subscribeGpsEvent();

        /**
         * 初始化电话token
         */
        phoneService.login();

        /**
         * 初始化勤务人员接口token
         */
        //userGpsService.handleToken();
    }
}
