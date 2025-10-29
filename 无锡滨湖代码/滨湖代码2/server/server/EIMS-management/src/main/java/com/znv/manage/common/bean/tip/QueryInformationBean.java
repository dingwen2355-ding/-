package com.znv.manage.common.bean.tip;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资讯查询
 *
 * @author chd
 * @date 2022/8/29
 */
@Data
public class QueryInformationBean {
    @ApiModelProperty(value = "标题/发布人,模糊搜索")
    String text;

    @ApiModelProperty(value = "发布时间，开始时间")
    String beginTime;

    @ApiModelProperty(value = "发布时间，结束时间")
    String endTime;

    @ApiModelProperty(value = "状态：0:暂存 1:已发布 2:已禁用")
    String status;

    @ApiModelProperty(value = "分页页数")
    Integer pageNum;

    @ApiModelProperty(value = "分页大小")
    Integer pageSize;
}
