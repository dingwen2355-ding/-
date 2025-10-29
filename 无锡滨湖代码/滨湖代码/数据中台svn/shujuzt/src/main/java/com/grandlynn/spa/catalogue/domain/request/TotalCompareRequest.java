package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import javax.validation.constraints.NotNull;
@Data
public class TotalCompareRequest extends PageRequest {
   @ApiModelProperty("系统id")
   private @NotNull(
   message = "未选择系统"
) Long sysId;
   @ApiModelProperty("目录名")
   private String tableName;
   @ApiModelProperty("目录id")
   private Long tableId;
   @ApiModelProperty("全量目录id")
   private Long tableLogId;
   @ApiModelProperty("目录状态")
   private Integer[] tableState;

}
