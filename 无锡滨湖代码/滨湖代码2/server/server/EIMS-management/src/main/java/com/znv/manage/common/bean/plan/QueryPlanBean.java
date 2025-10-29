package com.znv.manage.common.bean.plan;

import io.swagger.annotations.ApiModelProperty;
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
    String planId;

    String name;

    String eventTypeId;

    String emergencyTypeId;

    String levelId;

    String createId;

    String shared;

    String eventId;

    List<String> status;

    String createName;

    Integer pageNum = 1;

    Integer pageSize = 10;


    @ApiModelProperty(value = "发布单位分类id")
    String unitTypeId;

    @ApiModelProperty(value = "发布单位名称")
    String unitName;

    @ApiModelProperty(value = "开始发布时间")
    String beginTime;

    @ApiModelProperty(value = "结束发布时间")
    String endTime;

    @ApiModelProperty(value = "所属辖区")
    String regionId;
}
