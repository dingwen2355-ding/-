package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueOrgGetDetailRequest extends BaseRequest {
   @ApiModelProperty(
      value = "组织id",
      required = true
   )
   private @NotNull(
   message = "组织id不能为空"
) Long id;

}
