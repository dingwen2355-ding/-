package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueFileOperationLogGetLogRequest extends PageRequest {
   @ApiModelProperty(
      value = "操作类型：1.export;2.import",
      required = true
   )
   private @NotNull(
   message = "操作类型：1.export;2.import"
) Integer operationType;
   @ApiModelProperty(
      value = "操作模块类型:1组织管理导入；2目录编制导入；3接入管理导入；4组织管理导出；5目录编制导出；6版本管理导出;10全量导出word",
      required = true
   )
   private @NotNull(
   message = "操作模块类型:1组织管理导入；2目录编制导入；3接入管理导入；4组织管理导出；5目录编制导出；6版本管理导出"
) Integer operationFrom;
   @ApiModelProperty("文件后缀xls;docx(注意无.)")
   private String fileSuffix;
   @ApiModelProperty("文件名")
   private String fileName;

}
