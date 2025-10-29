package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SysOperationLogAnalysisResponse extends BaseResponse {
   @ApiModelProperty("登录人数")
   private Long loginNumber;
   @ApiModelProperty("提交审批次数")
   private Long submitApplyNumber;
   @ApiModelProperty("审批次数")
   private Long applyNumber;
   @ApiModelProperty("审批比例")
   private Double applyRatio;
   @ApiModelProperty("异常次数")
   private Long abnormalNumber;

}
