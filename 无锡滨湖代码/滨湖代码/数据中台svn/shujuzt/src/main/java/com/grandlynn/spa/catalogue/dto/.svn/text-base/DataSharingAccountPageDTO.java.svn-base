package com.grandlynn.spa.catalogue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class DataSharingAccountPageDTO implements Serializable {
   @ApiModelProperty("账户id")
   private Long accountId;
   @ApiModelProperty("认证账户")
   private String account;
   @ApiModelProperty("认证密码")
   private String accountPwd;
   @ApiModelProperty("默认 1未使用，2已使用，0已禁用")
   private Integer status;
   @ApiModelProperty("默认 1未使用，2已使用，0已禁用")
   private String statusName;
   @ApiModelProperty("使用方式")
   private Integer shareMode;
   @ApiModelProperty("使用方式")
   private Integer useType;
   @ApiModelProperty("使用组织id")
   private Integer orgId;
   @ApiModelProperty("使用组织名称")
   private String orgName;
   @ApiModelProperty("使用组织名称（简称）")
   private String orgNameSimplify;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @ApiModelProperty("创建时间")
   private Date createdTime;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @ApiModelProperty("修改时间")
   private Date updatedTime;

}
