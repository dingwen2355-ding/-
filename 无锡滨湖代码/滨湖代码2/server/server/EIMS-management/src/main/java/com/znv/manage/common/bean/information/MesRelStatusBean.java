package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 信息发送
 *
 * @author chd
 * @date 2022/6/1
 */
@Data
public class MesRelStatusBean {
    @ApiModelProperty(value = "信息发送状态表主键")
    String id;

    @ApiModelProperty(value = "信息id")
    String mesId;

    @ApiModelProperty(value = "发布方式：1 融合通讯 2 站内信")
    String relType;

    @ApiModelProperty(value = "人员id")
    String personId;

    @ApiModelProperty(value = "信息发送状态：0：成功 1：失败  2：待发送 3: 发送中")
    String status;

    @ApiModelProperty(value = "手机号")
    String phoneNumber;

    @ApiModelProperty(value = "短息内容")
    String content;

}
