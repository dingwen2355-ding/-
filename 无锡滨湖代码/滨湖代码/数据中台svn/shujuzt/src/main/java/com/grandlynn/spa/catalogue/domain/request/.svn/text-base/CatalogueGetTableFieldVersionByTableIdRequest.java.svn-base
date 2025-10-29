package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueGetTableFieldVersionByTableIdRequest extends PageRequest {
   @ApiModelProperty(
      value = "表id",
      required = true
   )
   private @NotNull(
   message = "表id不能为空"
) Long sysTableId;
   @ApiModelProperty(
      value = "版本号",
      required = true
   )
   private @NotBlank(
   message = "版本号不能为空"
) String versionNo;
   @ApiModelProperty("英文名称")
   private String fieldName;
   @ApiModelProperty("中文名称")
   private String fieldNameCh;
   @ApiModelProperty("字段状态")
   private Integer fieldState;

}
