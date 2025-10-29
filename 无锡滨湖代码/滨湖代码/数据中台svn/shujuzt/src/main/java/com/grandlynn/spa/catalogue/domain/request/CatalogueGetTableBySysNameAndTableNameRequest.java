package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueGetTableBySysNameAndTableNameRequest extends BaseRequest {
   @ApiModelProperty("系统全称")
   private @NotBlank(
   message = "系统全称不能为空"
) String sysName;
   @ApiModelProperty("表名")
   private @NotBlank(
   message = "资源名称不能为空"
) String dataResourceName;

}
