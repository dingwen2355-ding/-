package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案数字化应急响应表
 *
 * @author chd
 * @date 2022/5/10
 */
@Data
public class PlanNumEmergencyResponseBean {
    String id;

    String planNumId;

    String levelId;

    String levelName;

    String startCondition;

    String responseAction;
}
