package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueGetTablesAndFieldsBySysIdRequest extends PageRequest {
   @ApiModelProperty("系统主键id")
   private Long sysId;
   @ApiModelProperty("版本号")
   private String versionNo;
   @ApiModelProperty("能否导出 1、能 0、不能")
   private Long canExport;

}
