package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueFileImportRequest extends BaseRequest {
   @ApiModelProperty(
      value = "导入位置：1.组织导入ORG;2.系统管理导入SYS_MANAGE;3.目录编制导入CATALOGUE_MANAGE;4.数据源接入管理导入DATA",
      required = true
   )
   private @NotBlank(
   message = "导入位置：1.组织导入ORG;2.系统管理导入SYS_MANAGE;3.目录编制导入CATALOGUE_MANAGE;4.数据源接入管理导入DATA"
) String importPosition;

}
