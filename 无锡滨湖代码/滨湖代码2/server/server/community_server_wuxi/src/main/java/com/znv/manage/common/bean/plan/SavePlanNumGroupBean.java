package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 *预案数字化工作组bean
 *
 * @author chd
 * @date 2022/5/9
 */
@Data
public class SavePlanNumGroupBean {
    String id;

    String planNumId;

    String groupName;

    List<GroupPersonBean> addGroupPersonBeans;

    List<GroupPersonBean> updateGroupPersonBeans;

    List<String> delGroupPersonIds;
}
