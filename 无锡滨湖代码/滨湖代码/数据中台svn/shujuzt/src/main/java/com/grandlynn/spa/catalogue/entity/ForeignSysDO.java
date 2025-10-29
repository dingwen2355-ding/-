package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("public.foreign_sys")
public class ForeignSysDO {
   @TableId
   private Long id;
   @TableField("uuid")
   private String uuid;
   @TableField("name")
   private String name;
   @TableField("org")
   private String org;
   @TableField("level")
   private String level;
   @TableField("state")
   private String state;

}
