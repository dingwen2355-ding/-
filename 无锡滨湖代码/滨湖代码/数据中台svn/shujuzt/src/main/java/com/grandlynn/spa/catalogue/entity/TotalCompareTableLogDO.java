package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@TableName("public.total_compare_table_log")
public class TotalCompareTableLogDO extends BaseEntity {
   @ApiModelProperty("组织机构代码")
   @TableField("node_no")
   private String nodeNo;
   @ApiModelProperty("目录ID")
   @TableField("cata_table_id")
   private Long cataTableId;
   @ApiModelProperty("目录名称")
   @TableField("cata_table_name")
   private String cataTableName;
   @ApiModelProperty("全量清单目录Id")
   @TableField("total_table_id")
   private Long totalTableId;
   @ApiModelProperty("全量清单目录名称")
   @TableField("total_table_name")
   private String totalTableName;
   @ApiModelProperty("相似度")
   @TableField("route")
   private BigDecimal route;
   @ApiModelProperty("全量清单表字段")
   @TableField(
      exist = false
   )
   private String totalFields;

   public TotalCompareTableLogDO(CatalogueSysTableDO table, String nodeNo, ForeignTotalListDO total, BigDecimal route) {
      this.nodeNo = nodeNo;
      this.cataTableId = table.getId();
      this.cataTableName = table.getDataResourceName();
      this.totalTableId = total.getId();
      this.totalTableName = total.getTableName();
      this.route = route;
      this.totalFields = total.getField();
   }

   public TotalCompareTableLogDO(CatalogueSysTableDO table) {
      this.cataTableId = table.getId();
      this.cataTableName = table.getDataResourceName();
      this.totalTableName = table.getDataResourceName();
      this.route = new BigDecimal(100.0);
   }

   public TotalCompareTableLogDO() {
   }
}
