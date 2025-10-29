package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataSharingAccountQueryDTO extends PageRequest {
   @ApiModelProperty("认证账户")
   private String account;
   @ApiModelProperty("默认 1未使用，2已使用，0已禁用")
   private Integer status;
   @ApiModelProperty("使用方式")
   private Integer useType;
   @ApiModelProperty("使用组织id")
   private Long orgId;
   @ApiModelProperty("认证名称")
   private String accountName;

}
