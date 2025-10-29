package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class DisableExportRequest extends BaseRequest {
   @ApiModelProperty("目录Id")
   private @NotNull(
   message = "未选择目录"
) Long tableId;
   @ApiModelProperty("版本号")
   private @NotNull(
   message = "未指定版本"
) String versionNO;
   @ApiModelProperty("能否导出状态码(1.能; 0.不能)")
   private @NotNull(
   message = "未指定状态"
) Integer canExport;

}
