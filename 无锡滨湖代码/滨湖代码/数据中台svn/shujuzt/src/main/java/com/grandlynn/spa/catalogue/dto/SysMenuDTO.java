package com.grandlynn.spa.catalogue.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SysMenuDTO implements Serializable {
   private static final long serialVersionUID = 5333602363801205271L;
   @ApiModelProperty("菜单ID")
   private Long id;
   @ApiModelProperty("菜单名称")
   private String name;
   @ApiModelProperty("页面链接")
   private String url;
   @ApiModelProperty("父ID")
   private Long parentId;
   @ApiModelProperty("是否勾选")
   private Boolean isCheck;
   @ApiModelProperty("图标")
   @TableField("icon")
   private String icon;
   @ApiModelProperty("类型(0模块、1页面、2按钮)")
   @TableField("type")
   private Integer type;
   @ApiModelProperty("子菜单")
   private List<SysMenuDTO> list;
}
