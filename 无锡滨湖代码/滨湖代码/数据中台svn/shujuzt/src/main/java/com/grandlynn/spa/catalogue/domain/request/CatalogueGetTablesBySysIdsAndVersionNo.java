package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
@Data
@ApiModel
public class CatalogueGetTablesBySysIdsAndVersionNo extends BaseRequest {
   @ApiModelProperty("版本号")
   private String versionNo;
   @ApiModelProperty("系统id数组")
   private Long[] sysIds;

}
