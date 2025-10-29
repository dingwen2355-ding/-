package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("EnumsWuXiDTO")
public class EnumsWuXiDTO implements Serializable {
   private static final long serialVersionUID = -1L;
   @ApiModelProperty("枚举编码")
   private String code;
   @ApiModelProperty("枚举描述")
   private String name;

}
