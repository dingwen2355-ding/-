package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案数字化监测预警
 *
 * @author chd
 * @date 2022/5/7
 */
@Data
public class PlanNumMonitorBean {
    String id;

    String planNumId;

    String monitorGroup;

    String monitorTypeId;

    String monitorTypeName;

    String remark;
}
