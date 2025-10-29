package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CataloguePublishCatalogueRequest extends BaseRequest {
   @ApiModelProperty(
      value = "部门组织节点主键id",
      required = true
   )
   private Long orgId;
   @ApiModelProperty(
      value = "系统主键id",
      required = true
   )
   private Long sysId;
   @ApiModelProperty(
      value = "目录编制表主键id",
      required = true
   )
   private @NotNull(
   message = "目录编制表主键id不能为空"
) Long[] tableIds;

}
