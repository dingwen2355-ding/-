package com.grandlynn.spa.catalogue.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ApiModel
public class PageRequest extends BaseRequest implements Serializable {
   private static final long serialVersionUID = -1L;
   @ApiModelProperty(
      value = "页码",
      example = "1"
   )
   private @Min(
   value = -1L,
   message = "页面为-1时查询全部"
) Integer page = 1;
   @ApiModelProperty(
      value = "页大小",
      example = "10"
   )
   private @Min(
   message = "页大小填写错误",
   value = 1L
) @Max(
   value = 1000L,
   message = "单页元素最大为1000"
) Integer size = 10;

}
