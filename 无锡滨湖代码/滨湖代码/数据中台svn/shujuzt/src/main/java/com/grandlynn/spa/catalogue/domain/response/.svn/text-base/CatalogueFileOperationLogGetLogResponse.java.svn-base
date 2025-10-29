package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueFileOperationLogGetLogResponse extends BaseResponse {
   @ApiModelProperty("操作类型：1.export;2.import")
   private Object operationType;
   @ApiModelProperty("操作模块类型:1组织管理导入；2目录编制导入；3接入管理导入；4组织管理导出；5目录编制导出；6版本管理导出")
   private Object operationFrom;
   @ApiModelProperty("文件在ftp服务器保存的相对地址")
   private String fileUrl;
   @ApiModelProperty("包含后缀的文件名")
   private String fileName;
   @ApiModelProperty("文件后缀.xls;.docx")
   private String fileSuffix;
   @ApiModelProperty("操作信息：操作失败记录详细的错误信息;操作成功信息：格式 {\"operateTime\":\"操作时间\",\"operateResult\":\"操作结果okerror\",\"operateMsg\":\"操作msg，详细记录错误信息或者记录ok\"}\n")
   private Object operationMsg;

}
