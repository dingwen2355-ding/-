package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.util.pojo.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("sms")
public class SmsDO extends AbstractObject implements Serializable {
   @TableId

   private Long id;
   @TableField("user_name")
   private String userName;
   @TableField("mobile")
   private String mobile;
   @TableField("message")
   private String message;
   @TableField("send_status")
   private String sendStatus;
   @TableField("send_time")
   private Date sendTime;

}
