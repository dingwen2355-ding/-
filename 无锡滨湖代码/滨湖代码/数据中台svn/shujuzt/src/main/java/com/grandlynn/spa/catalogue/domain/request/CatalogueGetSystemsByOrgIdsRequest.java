package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueGetSystemsByOrgIdsRequest extends BaseRequest {
   @ApiModelProperty(
      value = "组织节点主键id数组",
      required = true
   )
   private @NotNull(
   message = "组织节点主键id数组不能为空"
) Long[] orgIds;
   @ApiModelProperty("版本号")
   private String versionNo;

}
