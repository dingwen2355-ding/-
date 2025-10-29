package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取信息发布日志
 *
 * @author chd
 * @date 2022/6/7
 */
@Data
public class GetMesRelLogBean {
    @ApiModelProperty(value = "标题/内容")
    String text;

    @ApiModelProperty(value = "类型")
    String typeId;

    @ApiModelProperty(value = "发布开始时间")
    String relBeginTime;

    @ApiModelProperty(value = "发布结束时间")
    String relEndTime;

    @ApiModelProperty(value = "分页页数")
    Integer pageNum = 1;

    @ApiModelProperty(value = "分页大小")
    Integer pageSize = 10;
}
