package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 审核我的预案
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class AuditPlanInfoBean {
    String id;

    String audit;

    String auditorId;

    String auditorName;

    String shared;

    String remark;
}
