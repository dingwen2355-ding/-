package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("data_sharing_account")
public class DataSharingAccount implements Serializable {
   @TableId(
      value = "account_id",
      type = IdType.AUTO
   )
   private Long accountId;
   @TableField("account")
   private String account;
   @TableField("account_pwd")
   private String accountPwd;
   @TableField("is_deleted")
   private Short isDeleted;
   @TableField("created_by")
   private String createdBy;
   @TableField("updated_by")
   private String updatedBy;
   @TableField("created_time")
   private Date createdTime;
   @TableField("updated_time")
   private Date updatedTime;
   @TableField("status")
   private Integer status;
   @TableField("use_type")
   private Integer useType;
   @TableField("org_id")
   private Integer orgId;
   @TableField(
      exist = false
   )
   private Integer orgName;
   @TableField("account_name")
   private String accountName;
   @TableField(
      exist = false
   )
   private static final long serialVersionUID = 1L;

}
