package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 *预案数字化工作组组员bean
 *
 * @author chd
 * @date 2022/5/9
 */
@Data
public class GroupPersonBean {
    String id;

    String groupId;

    String planNumId;

    String personName;

    String post;

    String phoneNumber;

    String office;

    String duty;

    String contactPerson;

    String contactDuty;

    String contactOfficePhone;

    String contactCellPhone;
}
