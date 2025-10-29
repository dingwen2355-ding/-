package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案工作组bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class PlanGroupBean {
    String id;

    String planId = "";

    String groupType = "1";

    String groupName = "";

    String remark = "";

    String extendRemark = "";

    String levelId = "992";

    String levelName = "";
}
