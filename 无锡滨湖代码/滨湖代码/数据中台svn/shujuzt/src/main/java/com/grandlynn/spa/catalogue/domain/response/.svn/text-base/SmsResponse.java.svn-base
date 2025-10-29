package com.grandlynn.spa.catalogue.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SmsResponse {
   @ApiModelProperty("返回码,200成功,其他失败")
   private String code;
   @ApiModelProperty("对返回码的文本描述")
   private String message;
   @ApiModelProperty("成功 返回ext在获取回执或回复信息时要用到的;失败 返回具体失败原因")
   private String data;

}
