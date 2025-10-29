package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("foreign_city_resource_type")
public class ForeignCityResourceTypeDO {
   @TableField("code")
   private String code;
   @TableField("name")
   private String name;
   @TableField("type")
   private String type;

}
