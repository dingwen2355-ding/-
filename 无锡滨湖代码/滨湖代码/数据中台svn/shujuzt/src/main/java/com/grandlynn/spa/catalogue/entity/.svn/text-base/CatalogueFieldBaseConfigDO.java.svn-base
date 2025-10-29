package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@TableName("catalogue_field_base_config")
@ApiModel("字段参数配置")
public class CatalogueFieldBaseConfigDO extends BaseEntity {
   @ApiModelProperty(
      value = "关联系统配置菜单id",
      required = true
   )
   private @NotNull(
   message = "未关联系统配置菜单"
) Long sysOrgId;
   @ApiModelProperty(
      value = "中文名称",
      required = true
   )
   private @NotNull(
   message = "中文名称不能为空"
) String fieldNameCh;
   @ApiModelProperty(
      value = "英文名称",
      required = true
   )
   private @NotNull(
   message = "英文名称不能为空"
) String fieldNameEn;
   @ApiModelProperty(
      value = "字段类型(1输入框,2下拉框)",
      required = true
   )
   private @NotNull(
   message = "字段类型不能为空"
) Integer fieldType;
   @ApiModelProperty(
      value = "字段长度",
      required = true
   )
   private @NotNull(
   message = "字段长度不能为空"
) String fieldLength;

}
