package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("public.sys_admin_user_role")
@ApiModel(
   value = "SysAdminUserRole",
   description = "管理端用户角色"
)
public class SysAdminUserRole extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("用户ID")
   @TableField("user_id")
   private Long userId;
   @ApiModelProperty("角色ID")
   @TableField("role_id")
   private Long roleId;

   public SysAdminUserRole(Long userId, Long roleId) {
      this.userId = userId;
      this.roleId = roleId;
   }

   public SysAdminUserRole() {
   }
}
