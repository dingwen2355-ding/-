package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案数字化关联系统bean
 *
 * @author chd
 * @date 2022/5/9
 */
@Data
public class PlanNumSystemBean {
    String id;

    String planNumId;

    String systemUnit;

    String systemName;

    String systemEnvironment;

    String systemUrl;
}
