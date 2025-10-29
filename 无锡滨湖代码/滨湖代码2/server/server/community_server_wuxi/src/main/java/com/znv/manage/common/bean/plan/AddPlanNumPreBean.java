package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 * 保存我的预案相关资料
 *
 * @author chd
 * @date 2022/5/7
 */
@Data
public class AddPlanNumPreBean {
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

    List<SavePlanNumGroupBean> savePlanNumGroupBeans;

    List<PlanNumSystemBean> planNumSystemBeans;

}
