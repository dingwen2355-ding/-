package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 * 保存我的预案分组
 *
 * @author chd
 * @date 2022/5/7
 */
@Data
public class SavePlanGroupBean {
    List<PlanGroupBean> addPlanGroupBeans;

    List<PlanGroupBean> updatePlanGroupBeans;

    List<String> delIds;
}
