package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 信息发布日志
 *
 * @author chd
 * @date 2022/6/7
 */
@Data
public class MesRelLogBean {
    @ApiModelProperty(value = "信息主键")
    String id;

    @ApiModelProperty(value = "信息标题")
    String mesName;

    @ApiModelProperty(value = "信息发布人")
    String relPerson;

    @ApiModelProperty(value = "信息发布时间")
    String relTime;

    @ApiModelProperty(value = "信息类型id")
    String typeId;

    @ApiModelProperty(value = "信息类型名称")
    String typeName;

    @ApiModelProperty(value = "成功数")
    String successCount;

    @ApiModelProperty(value = "失败数")
    String failCount;
}
