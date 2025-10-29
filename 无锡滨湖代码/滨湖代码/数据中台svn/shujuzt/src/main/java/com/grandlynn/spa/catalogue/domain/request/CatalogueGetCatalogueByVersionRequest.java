package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueGetCatalogueByVersionRequest extends PageRequest {
   @ApiModelProperty(
      value = "版本主键id",
      required = true
   )
   private @NotNull(
   message = "版本主键id不能为空"
) Long id;
   @ApiModelProperty(
      value = "版本号",
      required = true
   )
   private @NotBlank(
   message = "版本号不能为空"
) String versionNo;


}
