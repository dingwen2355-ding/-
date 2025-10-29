package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueFileTemplateDownloadRequest extends BaseRequest {
   @ApiModelProperty(
      value = "excel模板类型：1.组织模板ORG;2.系统管理模板SYS_MANAGE;3目录编制-编目模板CATALOGUE_MANAGE;4.数据源接入模板DATA",
      required = true
   )
   private @NotBlank(
   message = "excel模板类型：1.组织模板ORG;2.政务系统模板SYS;3.数据源接入模板DATA"
) String templateName;

}
