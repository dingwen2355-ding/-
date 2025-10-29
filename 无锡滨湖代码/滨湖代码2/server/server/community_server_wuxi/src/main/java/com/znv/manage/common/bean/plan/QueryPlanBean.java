package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 * 查询预案bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class QueryPlanBean {
    String name;

    String eventTypeId;

    String emergencyTypeId;

    String levelId;

    String createId;

    String shared;

    List<String> status;

    String createName;

    Integer pageNum = 1;

    Integer pageSize = 10;
}
