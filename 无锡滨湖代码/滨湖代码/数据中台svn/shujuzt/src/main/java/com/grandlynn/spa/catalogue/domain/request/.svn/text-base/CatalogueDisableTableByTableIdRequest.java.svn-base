package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueDisableTableByTableIdRequest extends BaseRequest {
   @ApiModelProperty(
      value = "表id",
      required = true
   )
   private @NotNull(
   message = "表id不能为空"
) Long id;
   @ApiModelProperty(
      value = "备注",
      required = true
   )
   private @NotNull(
   message = "备注"
) String remark;

}
