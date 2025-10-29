package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import lombok.Data;

@Data
@TableName("catalogue_org_type")
public class CatalogueOrgTypeDO extends BaseEntity {
   @TableField("type_name")
   private String typeName;
   @TableField(
      exist = false
   )
   private Long orgNum;
   @TableField(
      exist = false
   )
   private static final long serialVersionUID = 1L;

}
