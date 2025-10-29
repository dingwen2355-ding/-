package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueDeleteSysBySysIdRequest extends BaseRequest {
   @ApiModelProperty(
      value = "系统id",
      required = true
   )
   private @NotNull(
   message = "系统id不能为空"
) Long id;

}
