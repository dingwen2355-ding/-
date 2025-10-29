package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.grandlynn.util.pojo.AbstractObject;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TotalCompareDO extends AbstractObject {
   private static final long serialVersionUID = -1L;
   @TableId

   private Long id;
   @TableField(
      value = "tenant_id",
      fill = FieldFill.INSERT
   )
   private String tenantId;
   @TableField(
      value = "app_id",
      fill = FieldFill.INSERT
   )
   private Long appId;
   @TableField("version")
   private Integer version;
   @TableField("remark")
   private String remark;
   @TableLogic
   @TableField("is_deleted")
   private Integer deleted;

   @TableField(
      value = "created_time",
      fill = FieldFill.INSERT
   )
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createdTime;
   @TableField(
      value = "created_by",
      fill = FieldFill.INSERT
   )
   private String createdBy;

   @TableField(
      value = "updated_time",
      fill = FieldFill.INSERT_UPDATE
   )
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updatedTime;
   @TableField(
      value = "updated_by",
      fill = FieldFill.INSERT_UPDATE
   )
   private String updatedBy;
}
