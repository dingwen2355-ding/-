package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
@Data
@ApiModel
public class CatalogueGetTableRequest extends PageRequest {
   @ApiModelProperty("系统id")
   private Long orgSysId;
   @ApiModelProperty("组织id")
   private Long orgId;
   @ApiModelProperty("表名称")
   private String tableName;
   @ApiModelProperty("数据资源名称")
   private String dataResourceName;
   @ApiModelProperty("表状态")
   private Integer[] tableState;
   @ApiModelProperty("数据资源code")
   private String resourceCode;

}
