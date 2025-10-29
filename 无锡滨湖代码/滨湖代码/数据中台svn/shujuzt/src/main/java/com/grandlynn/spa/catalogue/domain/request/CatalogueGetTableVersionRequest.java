package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueGetTableVersionRequest extends PageRequest {
   @ApiModelProperty("系统id")
   private Long orgSysId;
   @ApiModelProperty("版本组织id")
   private Long orgId;
   @ApiModelProperty("组织id")
   private Long[] limitOrgIds;
   @ApiModelProperty("表名称")
   private String tableName;
   @ApiModelProperty("数据资源名称")
   private String dataResourceName;
   @ApiModelProperty(
      value = "版本号",
      required = true
   )
   private @NotBlank(
   message = "版本号不能为空"
) String versionNo;

}
