package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.util.pojo.AbstractObject;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("table_matter")
public class TableMatterDO extends AbstractObject implements Serializable {
   @TableField("rowguid")
   private String rowguid;
   @TableField("task_name")
   private String taskName;
   @TableField("catalog_code")
   private String catalogCode;
   @TableField("ywcode")
   private String ywcode;
   @TableField("task_code")
   private String taskCode;
   @TableField("dept_code")
   private String deptCode;
   @TableField("dept_name")
   private String deptName;
   @TableField("task_state")
   private String taskState;
   @TableField("is_history")
   private String isHistory;

}
