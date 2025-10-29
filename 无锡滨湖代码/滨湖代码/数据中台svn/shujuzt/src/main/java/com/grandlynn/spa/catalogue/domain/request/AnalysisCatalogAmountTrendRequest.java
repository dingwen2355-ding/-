package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class AnalysisCatalogAmountTrendRequest extends PageRequest {
   @ApiModelProperty("间隔时间段(年:year,月:month,周:week)")
   private String interval;
   @ApiModelProperty("开始时间")
   private Date beginTime;
   @ApiModelProperty("结束时间")
   private Date endTime;

}
