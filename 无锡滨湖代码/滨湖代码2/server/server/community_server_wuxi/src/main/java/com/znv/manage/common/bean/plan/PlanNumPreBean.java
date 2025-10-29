package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 * 预案数字化相关资料
 *
 * @author chd
 * @date 2022/5/10
 */
@Data
public class PlanNumPreBean {
    String id;

    String planNumId;

    String commandSystem;

    String departmentalResponsibilities;

    String associatedTeams;

    String associatedResettlementSites;

    String relatedMaterials;

    String expert;

    String groupNum;

    String personNum;

    String remark;

    List<PlanNumGroupBean> planNumGroupBeans;

    List<PlanNumSystemBean> planNumSystemBeans;

}
