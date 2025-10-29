package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.grandlynn.util.pojo.AbstractObject;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_operation_log")
public class SysOperationLogDO extends AbstractObject implements Serializable {
   @TableId

   private Long id;
   @TableField("user_id")
   private Long userId;
   @TableField("user_name")
   private String userName;
   @TableField("title")
   private String title;
   @TableField("business_type")
   private String businessType;
   @TableField("status")
   private String status;
   @TableField("error_mgs")
   private String errorMgs;

   @TableField(
      value = "created_time",
      fill = FieldFill.INSERT
   )
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createdTime;

   @TableField("sm3_hmac")
   private String sm3Hmac;
}
