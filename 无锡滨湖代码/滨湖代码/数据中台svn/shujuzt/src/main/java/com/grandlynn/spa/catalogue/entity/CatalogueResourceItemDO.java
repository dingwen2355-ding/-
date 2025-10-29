package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@Data
@TableName("catalogue_resource_item")
public class CatalogueResourceItemDO {
   @TableField("id")
   private Long id;
   @TableField("code")
   private String code;
   @TableField("name")
   private String name;
   @TableField("category_id")
   private Long categoryId;
   @TableField(
      exist = false
   )
   private List<CatalogueResourceCatalogueDO> catalogueDOS;


}
