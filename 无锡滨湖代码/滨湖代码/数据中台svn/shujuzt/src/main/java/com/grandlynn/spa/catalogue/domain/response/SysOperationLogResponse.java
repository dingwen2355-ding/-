package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel
public class SysOperationLogResponse extends BaseResponse {
   @ApiModelProperty("自增主键")
   private Long id;
   @ApiModelProperty("用户id")
   private Long userId;
   @ApiModelProperty("用户名称")
   private String userName;
   @ApiModelProperty("模块")
   private String title;
   @ApiModelProperty("功能")
   private String businessType;
   @ApiModelProperty("状态")
   private String status;
   @ApiModelProperty("错误信息")
   private String errorMgs;
   @ApiModelProperty("创建时间")
   private Date createdTime;

}
