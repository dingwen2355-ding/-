package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("catalogue_resource_catalogue")
public class CatalogueResourceCatalogueDO {
   @TableField("id")
   private Long id;
   @TableField("code")
   private String code;
   @TableField("name")
   private String name;
   @TableField("item_id")
   private Long itemId;

}
