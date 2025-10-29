package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@TableName("public.sys_menu")
@ApiModel(
   value = "SysMenu",
   description = "管理端菜单信息"
)
public class SysMenu extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("菜单名称")
   @TableField("name")
   private @NotNull(
   message = "名称不能为空"
) String name;
   @ApiModelProperty("页面链接")
   @TableField("url")
   private @NotNull(
   message = "链接未配置"
) String url;
   @ApiModelProperty("父ID")
   @TableField("parent_id")
   private Long parentId;
   @ApiModelProperty("编码")
   @TableField("code")
   private String code;
   @ApiModelProperty("排序")
   @TableField("sort")
   private Integer sort;
   @ApiModelProperty("图标")
   @TableField("icon")
   private String icon;
   @ApiModelProperty("类型(0模块、1页面、2按钮)")
   @TableField("type")
   private @NotNull(
   message = "类型不能为空"
) Integer type;
   @ApiModelProperty("系统类型：1目录编制系统 2目录资源管理系统")
   @TableField("sol_system")
   private Integer solSystem;


}
