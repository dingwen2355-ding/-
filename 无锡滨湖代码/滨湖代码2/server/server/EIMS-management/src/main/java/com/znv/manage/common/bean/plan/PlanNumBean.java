package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 * 预案数字化基本信息bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class PlanNumBean {
    String id;

    String name;

    String emergencyTypeId;

    String emergencyTypeName;

    String levelId;

    String levelName;

    String createId;

    String createName;

    String createTime;

    String modifyTime;

    String rewardDuty;

    List<String> fileUrls;
    
    /**
     * 前期准备
     */
    PlanNumPreBean planNumPreBean;

    /**
     * 预案监测预警
     */
    List<PlanNumMonitorBean> planNumMonitorBeans;

    /**
     * 应急响应
     */
    List<PlanNumEmergencyResponseBean> planNumEmergencyResponseBeans;

    /**
     * 应急保障
     */
    PlanNumEmergencySupportBean planNumEmergencySupportBean;

    /**
     * 应急响应终止
     */
    PlanNumEmergencyStopBean planNumEmergencyStopBean;

    /**
     * 相关资料
     */
    List<PlanNumDataBean> planNumDataBeans;
}
