package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SysAdminUserQueryRequest extends PageRequest {
   @ApiModelProperty("用户名")
   private String name;
   @ApiModelProperty("角色")
   private Long roleId;
   @ApiModelProperty("登录名")
   private String login;

}
