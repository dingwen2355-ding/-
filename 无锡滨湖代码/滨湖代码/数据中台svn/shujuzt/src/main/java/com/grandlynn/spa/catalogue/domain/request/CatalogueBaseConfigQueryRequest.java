package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueBaseConfigQueryRequest extends PageRequest {
   @ApiModelProperty("参数名称")
   private String name;

}
