package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class LoginSourceDTO implements Serializable {
   @ApiModelProperty("登录姓名")
   private String login;
   @ApiModelProperty("登录来源")
   private String loginSource;
   @ApiModelProperty("上次登录时间")
   private String lastLoginTime;
}
