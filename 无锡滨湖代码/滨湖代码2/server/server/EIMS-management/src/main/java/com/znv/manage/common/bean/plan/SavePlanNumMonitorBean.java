package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;


/**
 * 保存预案数字化监测预警
 *
 * @author chd
 * @date 2022/5/7
 */
@Data
public class SavePlanNumMonitorBean {
    List<PlanNumMonitorBean> addPlanNumMonitorBeans;

    List<PlanNumMonitorBean> updatePlanNumMonitorBeans;

    List<String> delIds;
}
