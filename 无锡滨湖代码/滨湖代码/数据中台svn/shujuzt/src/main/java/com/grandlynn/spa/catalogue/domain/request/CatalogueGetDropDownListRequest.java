package com.grandlynn.spa.catalogue.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueGetDropDownListRequest {
   @ApiModelProperty("枚举编码")
   private Integer enumCode;

}
