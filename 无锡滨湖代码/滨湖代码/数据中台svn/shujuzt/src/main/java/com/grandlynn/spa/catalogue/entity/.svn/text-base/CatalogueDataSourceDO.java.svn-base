package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("数据源")
@TableName("catalogue_data_source")
public class CatalogueDataSourceDO extends BaseEntity {
   @ApiModelProperty("组织id")
   private @NotNull(
   message = "未关联组织"
) Long orgId;
   @ApiModelProperty("系统id")
   private @NotNull(
   message = "未关联系统"
) Long sysId;
   @ApiModelProperty("组织名称")
   @TableField(
      exist = false
   )
   private String orgName;
   @ApiModelProperty("系统名称")
   @TableField(
      exist = false
   )
   private String sysName;
   @ApiModelProperty("接入方式(1:数据库, 0:Api)")
   private @NotNull(
   message = "接入方式不能为空"
) Integer pluginType;
   @ApiModelProperty("编码")
   private String code;
   @ApiModelProperty("名称")
   private @NotNull(
   message = "名称不能为空"
) String name;
   @ApiModelProperty("状态(0:已创建,1:可联通,2:异常)")
   private Integer state;
   @ApiModelProperty("Api类型数源")
   @TableField(
      exist = false
   )
   private @Valid CatalogueDataSourceApiDO api;
   @ApiModelProperty("数据库类型数源")
   @TableField(
      exist = false
   )
   private @Valid CatalogueDataSourceDatabaseDO database;
   @ApiModelProperty("（表）目录id")
   private Long tableId;

}
