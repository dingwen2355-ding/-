package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import lombok.Data;

@Data
@TableName("catalogue_sys_table_resource_serial")
public class CatalogueSysTableResourceSerialDO extends BaseEntity {
   @TableField("org_id")
   private Long orgId;
   @TableField("org_name")
   private String orgName;
   @TableField("serial")
   private Integer serial;

   public CatalogueSysTableResourceSerialDO(String nodeName, Integer serial) {
      this.orgName = nodeName;
      this.serial = serial;
   }

}
