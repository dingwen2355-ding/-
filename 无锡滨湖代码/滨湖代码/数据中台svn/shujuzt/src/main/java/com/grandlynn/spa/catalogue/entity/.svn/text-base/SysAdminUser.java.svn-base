package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotEmpty;

@Data
@TableName("public.sys_admin_user")
@ApiModel(
   value = "SysAdminUser",
   description = "管理端账户信息"
)
public class SysAdminUser extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("登录名")
   @TableField("login")
   private @NotEmpty(
   message = "登录名不能为空"
) String login;
   @ApiModelProperty("登录密码")
   @TableField("password")
   private String password;
   @ApiModelProperty("用户名")
   @TableField("name")
   private String name;
   @ApiModelProperty("单点登录用户id")
   @TableField("ssoid")
   private String ssoid;
   @ApiModelProperty("用户状态 0：正常 1：冻结")
   @TableField("status")
   private Integer status;
   @ApiModelProperty("电话号码")
   @TableField("tel")
   private String tel;
   @ApiModelProperty("类型 1：管理员  2：普通用户")
   @TableField("type")
   private Integer type;
   @ApiModelProperty("头像(文件路径)")
   @TableField("portrait")
   private String portrait;
   @ApiModelProperty("邮箱")
   @TableField("email")
   private String email;
   @ApiModelProperty("部门Id")
   @TableField(
      exist = false
   )
   private Long[] orgids;
   @ApiModelProperty("部门信息")
   @TableField(
      exist = false
   )
   private List<CatalogueOrgDO> orgs;
   @ApiModelProperty("角色信息")
   @TableField(
      exist = false
   )
   private List<SysRole> roles;
   @ApiModelProperty("是否存在部门限制")
   @TableField(
      exist = false
   )
   private Boolean orgLimit = true;
   @TableField(
      exist = false
   )
   private String token;

   @TableField("authorize_sm3_hmac")
   private String authorizeSm3Hmac;
}
