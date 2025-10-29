package com.grandlynn.spa.catalogue.base;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class BaseRequest extends AbstractObject implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(
      value = "租户id",
      required = true
   )
   private @NotBlank(
   message = "租户信息不能为空"
) String tenantId;
   @ApiModelProperty("接入方id")
   private Long appId;

}
