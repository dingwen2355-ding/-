package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueFileOperationLogGetCurrentStatusResponse extends BaseResponse {
   @ApiModelProperty("操作类型：1.export;2.import")
   private Integer operationType;
   @ApiModelProperty("操作模块类型:1组织管理导入；2目录编制导入；3接入管理导入；4组织管理导出；5目录编制导出；6版本管理导出")
   private Integer operationFrom;
   @ApiModelProperty("包含后缀的文件名")
   private String fileName;
   @ApiModelProperty("文件状态:1上传文件;2文件校验;3解析入库;4完成;")
   private Integer fileStatus;
   @ApiModelProperty("文件操作状态:1成功;2异常")
   private Integer operationStatus;
   @ApiModelProperty("操作信息：操作失败记录详细的错误信息;操作成功信息：格式 {\"operateTime\":\"操作时间\",\"operateResult\":\"操作结果okerror\",\"operateMsg\":\"操作msg，详细记录错误信息或者记录ok\"}\n")
   private String operationMsg;

}
