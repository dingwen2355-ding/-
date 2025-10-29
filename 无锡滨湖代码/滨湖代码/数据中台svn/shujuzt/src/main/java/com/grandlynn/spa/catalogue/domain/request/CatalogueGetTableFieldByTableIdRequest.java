package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueGetTableFieldByTableIdRequest extends PageRequest {
   @ApiModelProperty(
      value = "表id",
      required = true
   )
   private @NotNull(
   message = "表id不能为空"
) Long sysTableId;
   @ApiModelProperty("英文名称")
   private String fieldName;
   @ApiModelProperty("中文名称")
   private String fieldNameCh;
   @ApiModelProperty("字段状态")
   private Integer fieldState;

}
