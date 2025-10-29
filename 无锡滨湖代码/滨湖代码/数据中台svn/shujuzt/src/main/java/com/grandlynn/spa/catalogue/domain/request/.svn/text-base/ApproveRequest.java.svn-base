package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class ApproveRequest extends BaseRequest {
   @ApiModelProperty(
      value = "目录表id",
      required = true
   )
   private @NotNull(
   message = "目录表id不允许为空"
) Long[] ids;
   @ApiModelProperty("驳回原因")
   private String reason;

}
