package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案数字化相关资料bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class PlanNumDataBean {
    String id;

    String planNumId;

    String fileName = "";

    String fileUrl = "";

    String createTime;

    String remark;
}
