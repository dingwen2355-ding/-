package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("public.foreign_total_list")
public class ForeignTotalListDO {
   @TableId

   private Long id;
   @TableField("node_no")
   private String nodeNo;
   @TableField("node_name")
   private String nodeName;
   @TableField("table_name")
   private String tableName;
   @TableField("field")
   private String field;

}
