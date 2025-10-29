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
public class PlanChildBean {
    String planId;
    //应急响应
    List<PlanGroupBean> planResponseGroups;
    //终止条件
    List<PlanGroupBean> planStopGroups;
    //应急保障
    PlanSupportGroups planSupportGroups;
    //预案资料
    List<PlanDataBean> planDataBeans;

    @Data
    public static class PlanSupportGroups {
        List<String> experts;
        List<String> teams;
        List<String> stuffs;
        List<String> shelters;
    }
}
