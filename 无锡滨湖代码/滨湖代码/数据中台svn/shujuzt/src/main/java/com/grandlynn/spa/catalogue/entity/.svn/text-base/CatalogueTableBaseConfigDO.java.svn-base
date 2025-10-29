package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@TableName("catalogue_table_base_config")
@ApiModel("数据参数配置")
public class CatalogueTableBaseConfigDO extends BaseEntity {
   @ApiModelProperty(
      value = "关联系统配置菜单id",
      required = true
   )
   private @NotNull(
   message = "未关联系统配置菜单"
) Long sysOrgId;
   @ApiModelProperty(
      value = "参数名称",
      required = true
   )
   private @NotNull(
   message = "参数名称不能为空"
) String configName;
   @ApiModelProperty(
      value = "配置类型：1下拉框；0输入框",
      required = true
   )
   private @NotNull(
   message = "类型不能为空"
) Integer configType;
   @ApiModelProperty("下拉值，逗号隔开")
   private String options;
   @ApiModelProperty(
      value = "是否必填：1是；0否",
      required = true
   )
   private @NotNull(
   message = "是否必填不能为空"
) Integer required;
   @ApiModelProperty(
      value = "列宽 50/100 ",
      required = true
   )
   private @NotNull(
   message = "宽度不能为空"
) String columnWidth;
   @ApiModelProperty("启用状态：1启用；0禁用")
   private Integer enabled;

}
