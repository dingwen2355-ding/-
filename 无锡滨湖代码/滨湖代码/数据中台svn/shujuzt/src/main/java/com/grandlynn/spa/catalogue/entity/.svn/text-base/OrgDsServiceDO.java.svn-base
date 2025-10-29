package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
@TableName("org_ds_service")
public class OrgDsServiceDO {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "service_id",
      type = IdType.AUTO
   )
   private Long serviceId;
   private Long deptId;
   private String deptName;
   private Long systemId;
   private String systemName;
   private Long tableId;
   private String tableCnName;
   private String tableEnName;
   private String odsTableName;
   private Integer cycle;
   private Integer shareType;
   private String shareMode;
   private Integer databaseType;
   private String describe;
   private Integer state;
   private String resourceCode;
   private String detyCode;
   private Integer browse;
   private Integer resourceCatalog;
   private String openType;
   private Long tempIdentification;
   private Long dataSize;
   @TableField(
      fill = FieldFill.INSERT
   )
   protected String createdBy;
   @TableField(
      fill = FieldFill.INSERT
   )
   protected Long createdAt;
   @TableField(
      fill = FieldFill.INSERT_UPDATE
   )
   protected String updatedBy;
   @TableField(
      fill = FieldFill.INSERT_UPDATE
   )
   protected Long updatedAt;
   @Version
   @TableField(
      fill = FieldFill.INSERT
   )
   protected Long resourceVersion;
   private String subjectCategory;
   private Integer isDeleted;
   @TableField("category_id")
   private Long categoryId;
   @TableField("item_id")
   private Long itemId;
   @TableField("catalogue_id")
   private Long catalogueId;
   @TableField("database_name")
   private String databaseName;
   @TableField("domain_code")
   private String domainCode;
   @TableField("industry_category_code")
   private String industryCategoryCode;
   @TableField("application_scene_code")
   private String applicationSceneCode;

}
