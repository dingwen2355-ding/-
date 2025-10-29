package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel
public class CatalogueDatasourceQueryRequest extends PageRequest {
   @ApiModelProperty("组织Id")
   private Long orgId;
   @ApiModelProperty("数源编号")
   private String code;
   @ApiModelProperty("数源名称")
   private String name;
   @ApiModelProperty("接入方式(1:数据库, 0:Api)")
   private Integer pluginType;
   @ApiModelProperty("状态(0:已创建,1:可联通,2:异常)")
   private Integer state;
   @ApiModelProperty("开始时间")
   private Date beginTime;
   @ApiModelProperty("结束时间")
   private Date endTime;

}
