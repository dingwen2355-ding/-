package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 共享我的预案分组
 *
 * @author chd
 * @date 2022/5/7
 */
@Data
public class SharePlanInfoBean {
    String id;

    String shared;

    String sharedTime;

    String sharerId;

    String sharerName;
}
