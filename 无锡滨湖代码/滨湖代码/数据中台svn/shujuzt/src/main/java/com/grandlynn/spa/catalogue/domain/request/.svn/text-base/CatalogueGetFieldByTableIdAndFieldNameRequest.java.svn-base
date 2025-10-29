package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueGetFieldByTableIdAndFieldNameRequest extends BaseRequest {
   @ApiModelProperty("表主键id")
   private @NotNull(
   message = "编制表主键id不能为空"
) Long sysTableId;
   @ApiModelProperty("字段名")
   private String fieldName;
   @ApiModelProperty("中文字段名")
   private @NotBlank(
   message = "字段名不能为空"
) String fieldNameCh;

}
