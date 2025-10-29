package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@Data
@TableName("catalogue_resource_category")
public class CatalogueResourceCategoryDO {

   private Long id;
   @TableField("code")
   private String code;
   @TableField("name")
   private String name;
   @TableField(
      exist = false
   )
   private List<CatalogueResourceItemDO> itemDOS;


}
