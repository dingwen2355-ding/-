package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import lombok.Data;

@Data
@TableName("catalogue_org_content_relation")
public class CatalogueOrgContentRelationDO extends BaseEntity {
   @TableField("org_id")
   private Long orgId;
   @TableField("org_no")
   private String orgNo;
   @TableField("content_type")
   private Integer contentType;
   @TableField("content_id")
   private Long contentId;
   @TableField("intermediate")
   private Integer intermediate;
   @TableField("version_no")
   private String versionNo;

}
