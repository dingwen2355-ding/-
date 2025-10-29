package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueGetVersionsRequest extends PageRequest {
   @ApiModelProperty("目录编制自定义版本号如V1.0.2102010001")
   private String versionNo;
   @ApiModelProperty("当前版本状态：1启用中；2已停用；默认1")
   private Integer versionState;

}
