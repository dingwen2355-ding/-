package com.znv.manage.common.bean.plan;


import lombok.Data;

/**
 * 预案数字化应急响应终止
 *
 * @author chd
 * @date 2022/5/10
 */
@Data
public class PlanNumEmergencyStopBean {
    String id;

    String planNumId;

    String terminationCondition;

    String terminateProgram;
}
