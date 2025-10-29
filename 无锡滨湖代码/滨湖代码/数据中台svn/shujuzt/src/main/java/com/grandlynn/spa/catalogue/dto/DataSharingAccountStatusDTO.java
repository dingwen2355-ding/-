package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class DataSharingAccountStatusDTO implements Serializable {
   @ApiModelProperty("账户id")
   private Long accountId;
   @ApiModelProperty("2启用，0禁用")
   private Integer status;

}
