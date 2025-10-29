package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
public interface IAlarmInstallService {

    Result selAlarmInstall();

    Result updateAlarmInstall(Integer id,String threshold, String rulesState, String user);
}
