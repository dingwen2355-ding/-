package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@ApiModel
public class SysOperationLogRequest extends PageRequest {
   @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm:ss"
   )
   @ApiModelProperty("开始时间")
   private Date startTime;
   @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm:ss"
   )
   @ApiModelProperty("结束时间")
   private Date endTime;
   @ApiModelProperty("模块")
   private String title;
   @ApiModelProperty("用户名称")
   private String userName;
   @ApiModelProperty("功能")
   private String businessType;
   @ApiModelProperty("状态")
   private String status;

}
