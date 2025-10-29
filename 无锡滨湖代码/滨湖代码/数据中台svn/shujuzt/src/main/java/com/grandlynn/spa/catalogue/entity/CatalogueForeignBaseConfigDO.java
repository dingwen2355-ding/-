package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@TableName("catalogue_foreign_base_config")
@ApiModel("外部参数配置")
public class CatalogueForeignBaseConfigDO extends BaseEntity {
   @ApiModelProperty(
      value = "外部系统编号",
      required = true
   )
   private @NotNull(
   message = "外部系统编号"
) String outId;
   @ApiModelProperty(
      value = "内部系统编号",
      required = true
   )
   private @NotNull(
   message = "内部系统编号"
) String innerId;
   @ApiModelProperty(
      value = "外部系统名称",
      required = true
   )
   private @NotNull(
   message = "外部系统名称"
) String outName;
   @ApiModelProperty(
      value = "内部系统名称",
      required = true
   )
   private @NotNull(
   message = "内部系统名称"
) String innerName;
   @ApiModelProperty("状态（0：未匹配，1：已匹配）")
   private Integer state;

}
