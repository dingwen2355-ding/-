package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@Data
@TableName("foreign_city_resource_domain")
public class ForeignCityResourceDomainDO {
   @TableField("code")
   private String code;
   @TableField("name")
   private String name;
   @TableField("type")
   private String type;
   @TableField(
      exist = false
   )
   private List<ForeignCityResourceTypeDO> child;

}
