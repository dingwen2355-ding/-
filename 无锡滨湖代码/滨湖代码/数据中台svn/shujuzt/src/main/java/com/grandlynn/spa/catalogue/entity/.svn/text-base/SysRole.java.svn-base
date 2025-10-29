package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("public.sys_role")
@ApiModel(
   value = "SysRole",
   description = "管理端角色信息"
)
public class SysRole extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("角色名称")
   @TableField("name")
   private String name;
   @ApiModelProperty("角色编码")
   @TableField("code")
   private String code;
   @ApiModelProperty("允许编辑,删除(0:允许,1:不允许)")
   @TableField("can_delete")
   private Integer canDelete;

}
