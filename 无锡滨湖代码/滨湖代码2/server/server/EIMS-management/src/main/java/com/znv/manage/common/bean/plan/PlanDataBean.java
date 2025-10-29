package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案相关资料bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class PlanDataBean {
    String id;

    String planId;

    String fileName = "";

    String fileUrl = "";

    String createTime;

    String remark;
}
