package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("catalogue_resource_industry_category")
public class CatalogueResourceIndustryCategoryDO {
   @TableField("code")
   private String code;
   @TableField("name")
   private String name;

}
