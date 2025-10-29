package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueGetFieldDetailByFieldIdRequest extends PageRequest {
   @ApiModelProperty(
      value = "字段表主键id",
      required = true
   )
   private @NotNull(
   message = "字段表主键id不能为空"
) Long id;

}
