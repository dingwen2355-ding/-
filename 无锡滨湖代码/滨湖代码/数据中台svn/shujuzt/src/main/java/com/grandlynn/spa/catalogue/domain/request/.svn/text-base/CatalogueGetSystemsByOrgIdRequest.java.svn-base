package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueGetSystemsByOrgIdRequest extends BaseRequest {
   @ApiModelProperty(
      value = "组织节点主键id",
      required = true
   )
   private Long orgId;
   @ApiModelProperty("系统名称")
   private String sysName;
   @ApiModelProperty("使用状态")
   private String sysStatus;
   @ApiModelProperty("系统类型")
   private String sysType;
   @ApiModelProperty("接入网络类型")
   private String netType;

}
