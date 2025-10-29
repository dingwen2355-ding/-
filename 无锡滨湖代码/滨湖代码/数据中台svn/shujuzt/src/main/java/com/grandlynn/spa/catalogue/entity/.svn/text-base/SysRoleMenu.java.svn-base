package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("public.sys_role_menu")
@ApiModel(
   value = "SysRoleMenu",
   description = "管理端角色权限"
)
public class SysRoleMenu extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("角色ID")
   @TableField("role_id")
   private Long roleId;
   @ApiModelProperty("菜单ID")
   @TableField("menu_id")
   private Long menuId;
   public static String MENU_ID = "menuId";

   public SysRoleMenu(Long roleId, Long menuId) {
      this.roleId = roleId;
      this.menuId = menuId;
   }

   public SysRoleMenu() {
   }
}
