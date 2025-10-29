package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 信息发布人
 *
 * @author chd
 * @date 2022/6/7
 */
@Data
public class MesRelPersonBean {
    @ApiModelProperty(value = "信息发送状态表主键")
    String id;

    @ApiModelProperty(value = "人员id")
    String personId;

    @ApiModelProperty(value = "人员名称")
    String personName;

    @ApiModelProperty(value = "信息发送状态：0：成功 1：失败  2：待发送 3: 发送中")
    String statusId;

    @ApiModelProperty(value = "信息发送状态：0：成功 1/2/3：失败")
    String status;

    @ApiModelProperty(value = "手机号")
    String phoneNumber;
}

