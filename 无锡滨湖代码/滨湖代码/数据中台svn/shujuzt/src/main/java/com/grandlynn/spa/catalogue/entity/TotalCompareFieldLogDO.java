package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@TableName("public.total_compare_field_log")
public class TotalCompareFieldLogDO extends BaseEntity {
   @ApiModelProperty("目录比对结果Id")
   @TableField("table_log_id")
   private Long tableLogId;
   @ApiModelProperty("目录字段")
   @TableField("cata_field")
   private String cataField;
   @ApiModelProperty("全量清单字段")
   @TableField("total_field")
   private String totalField;
   @ApiModelProperty("相似度(%)")
   @TableField("route")
   private BigDecimal route;

   public TotalCompareFieldLogDO(Long tableLogId, CatalogueTableFieldDO field) {
      this.tableLogId = tableLogId;
      this.cataField = field.getFieldNameCh();
      this.totalField = field.getFieldNameCh();
      this.route = new BigDecimal(100.0);
   }

   public TotalCompareFieldLogDO() {
   }

   public TotalCompareFieldLogDO(final Long tableLogId, final String cataField, final String totalField, final BigDecimal route) {
      this.tableLogId = tableLogId;
      this.cataField = cataField;
      this.totalField = totalField;
      this.route = route;
   }
}
