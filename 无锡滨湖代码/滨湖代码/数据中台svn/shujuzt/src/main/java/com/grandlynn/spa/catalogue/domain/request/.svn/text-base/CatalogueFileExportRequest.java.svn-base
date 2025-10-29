package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueFileExportRequest extends BaseRequest {
   @ApiModelProperty(
      value = "导出位置：1.组织导出ORG;2.系统管理导入SYS_MANAGE;3.目录编制导入CATALOGUE_MANAGE;3.版本导出VERSION",
      required = true
   )
   private @NotBlank(
   message = "导出位置：1.组织导出ORG;2.系统管理导入SYS_MANAGE;3.目录编制导入CATALOGUE_MANAGE;4.版本导出VERSION"
) String exportPosition;
   @ApiModelProperty("组织节点id(目录编制导出指定组织节点下的所有目录编制,不传导出全部)")
   private Long orgId;
   @ApiModelProperty("系统id")
   private Long orgSysId;
   @ApiModelProperty("由版本管理导出word和excel时版本号参数")
   private String versionNo;
   @ApiModelProperty("导出文件类型：1.word文件;其他或不传导出excel文件")
   private Integer fileType;
   @ApiModelProperty("组织节点id数组（仅限导出word使用,支持选中多个组织节点id）")
   private Long[] orgIds;
   @ApiModelProperty("全量导出word标志")
   private Boolean exportAll;

}
