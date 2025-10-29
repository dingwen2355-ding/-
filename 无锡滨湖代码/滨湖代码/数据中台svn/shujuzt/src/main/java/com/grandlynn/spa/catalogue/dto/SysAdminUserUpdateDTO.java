package com.grandlynn.spa.catalogue.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SysAdminUserUpdateDTO implements Serializable {
   private static final long serialVersionUID = -78869918849574860L;
   @ApiModelProperty("id")
   private Long id;
   @ApiModelProperty("登录密码")
   private String password;
   @ApiModelProperty("用户名")
   private String name;
   @ApiModelProperty("电话号码")
   @TableField("tel")
   private String tel;
   @ApiModelProperty("角色ID")
   private List<Long> roleIdList;
}
