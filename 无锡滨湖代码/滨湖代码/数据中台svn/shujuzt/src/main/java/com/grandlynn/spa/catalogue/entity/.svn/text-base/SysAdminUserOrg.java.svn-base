package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("public.sys_admin_user_org")
@ApiModel(
   value = "SysAdminUserOrg",
   description = "管理端用户部门"
)
public class SysAdminUserOrg extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("用户ID")
   @TableField("user_id")
   private Long userId;
   @ApiModelProperty("角色ID")
   @TableField("org_id")
   private Long orgId;

   public SysAdminUserOrg() {
   }

   public SysAdminUserOrg(final Long userId, final Long orgId) {
      this.userId = userId;
      this.orgId = orgId;
   }
}
