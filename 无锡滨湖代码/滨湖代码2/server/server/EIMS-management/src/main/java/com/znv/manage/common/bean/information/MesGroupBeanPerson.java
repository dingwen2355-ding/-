package com.znv.manage.common.bean.information;

import lombok.Data;

/**
 *新增我的信息通讯录组组员
 *
 * @author chd
 * @date 2022/6/2
 */
@Data
public class MesGroupBeanPerson {
    String id;

    String groupId;

    String personName;

    String post;

    String phoneNumber;

    String office;

    String duty;
}
