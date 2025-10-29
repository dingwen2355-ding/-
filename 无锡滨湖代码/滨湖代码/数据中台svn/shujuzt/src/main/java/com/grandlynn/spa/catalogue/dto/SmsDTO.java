package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel("SmsDTO")
public class SmsDTO implements Serializable {
   @ApiModelProperty("id")
   private Long id;
   @ApiModelProperty("接收人name")
   private String userName;
   @ApiModelProperty("手机号")
   private String mobile;
   @ApiModelProperty("消息")
   private String message;
   @ApiModelProperty("状态")
   private String sendStatus;
   @ApiModelProperty("发送时间")
   private Date sendTime;
}
