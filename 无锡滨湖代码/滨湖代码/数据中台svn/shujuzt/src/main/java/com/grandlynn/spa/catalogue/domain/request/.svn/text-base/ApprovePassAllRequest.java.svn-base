package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class ApprovePassAllRequest extends BaseRequest {
   @ApiModelProperty(
      value = "4:初审全部通过;5:复审通过全部",
      required = true
   )
   private @NotNull(
   message = "请指定初审通过4或复审通过5;"
) Integer passCode;
   @ApiModelProperty("审批意见")
   private String passMsg;

}
