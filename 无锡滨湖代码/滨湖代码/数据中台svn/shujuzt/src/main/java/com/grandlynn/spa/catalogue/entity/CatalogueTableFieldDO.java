package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import lombok.Data;

@Data
@TableName("catalogue_table_field")
public class CatalogueTableFieldDO extends BaseEntity {
   @TableField("sys_table_id")
   private Long sysTableId;
   @TableField(
      exist = false
   )
   private String sysTableName;
   @TableField("field_name")
   private String fieldName;
   @TableField("field_alias")
   private String fieldAlias;
   @TableField("field_type")
   private String fieldType;
   @TableField("field_length")
   private String fieldLength;
   @TableField("field_version")
   private String fieldVersion;
   @TableField("field_state")
   private Integer fieldState;
   @TableField("field_flag")
   private Integer fieldFlag;
   @TableField("field_encoded")
   private String fieldEncoded;
   @TableField("field_obtuse")
   private String fieldObtuse;
   @TableField("field_primary_key")
   private String fieldPrimaryKey;
   @TableField("field_position")
   private String fieldPosition;
   @TableField("field_desc")
   private String fieldDesc;
   @TableField("field_total_log")
   private String fieldTotalLog;
   @TableField("field_total_memory")
   private String fieldTotalMemory;
   @TableField("intermediate")
   private Integer intermediate;
   @TableField("version_no")
   private String versionNo;
   @TableField("field_name_ch")
   private String fieldNameCh;
   @TableField("dict_state")
   private String dictState;
   @TableField("dict_table")
   private String dictTable;
   @TableField("dict_standard")
   private String dictStandard;
   @TableField("share_type")
   private String shareType;
   @TableField("share_condition")
   private String shareCondition;
   @TableField("law_dependency")
   private String lawDependency;
   @TableField("law_dependency_attach")
   private String lawDependencyAttach;
   @TableField("allow_null")
   private String allowNull;
   @TableField("allow_index")
   private String allowIndex;
   @TableField("publisher")
   private Long publisher;
   @TableField("field_precision")
   private Integer fieldPrecision;
   @TableField("data_level")
   private Integer dataLevel;
   @TableField("is_norm")
   private Integer isNorm;
   @TableField(
      value = "column_id",
      fill = FieldFill.INSERT
   )
   private String columnId;

}
