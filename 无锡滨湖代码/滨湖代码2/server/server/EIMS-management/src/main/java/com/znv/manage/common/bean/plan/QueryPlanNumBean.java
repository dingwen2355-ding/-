package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 预案数字化查询
 *
 * @author chd
 * @date 2022/5/9
 */
@Data
public class QueryPlanNumBean {
    String name;

    String createId;

    Integer pageNum = 1;

    Integer pageSize = 10;
}
