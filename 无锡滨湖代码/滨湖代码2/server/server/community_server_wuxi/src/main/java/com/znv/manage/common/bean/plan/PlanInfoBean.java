package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 预案基本信息bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class PlanInfoBean {
    String id;

    String name;

    String levelId;

    String levelName;

    String eventTypeId;

    String eventTypeName;

    String emergencyTypeId;

    String emergencyTypeName;

    String status;

    String shared;

    String sharedTime;

    String auditOpinion;

    String createId;

    String createName;

    String sharerId;

    String sharerName;

    String auditorId;

    String auditorName;

    String createTime;

    String modifyTime;

    String releaseTime;

    String rewardDuty;

    List<String> fileUrls;

    List<Map<String, Object>> fileData;

    List<PlanGroupBean> planGroupBeans;

    List<PlanDataBean> planDataBeans;
}
