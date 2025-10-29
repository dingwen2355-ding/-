package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel("DropDownWuXiDataDTO")
public class DropDownWuXiDataDTO implements Serializable {
   private static final long serialVersionUID = -1L;
   @ApiModelProperty("下拉数据枚举code")
   private Integer code;
   @ApiModelProperty("下拉数据名称")
   private String name;
   @ApiModelProperty("下拉数据")
   private List<EnumsWuXiDTO> enumsDTOS;


}
