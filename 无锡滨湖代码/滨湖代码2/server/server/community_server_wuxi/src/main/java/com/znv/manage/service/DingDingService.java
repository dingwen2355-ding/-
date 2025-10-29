package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.List;


/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */

public interface DingDingService {

    Result syncDepartment(String deptId);

    Result syncCommunicationData(String deptId);

    Result createVideoConferences(List<String> unionIds,String titleName);
}
