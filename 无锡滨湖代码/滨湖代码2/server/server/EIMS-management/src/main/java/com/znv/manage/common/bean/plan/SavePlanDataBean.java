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
public class SavePlanDataBean {
    List<PlanDataBean> addPlanDataBeans;

    List<PlanDataBean> updatePlanDataBeans;

    List<String> delIds;
}
