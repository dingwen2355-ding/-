package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("public.sys_read_notice")
@ApiModel(
   value = "SysReadNotice",
   description = ""
)
public class SysReadNotice implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("用户id")
   @TableField("user_id")
   private Integer userId;
   @ApiModelProperty("公告id")
   @TableField("sys_notice_id")
   private String sysNoticeId;

   public Integer getUserId() {
      return this.userId;
   }

   public String getSysNoticeId() {
      return this.sysNoticeId;
   }

   public SysReadNotice setUserId(final Integer userId) {
      this.userId = userId;
      return this;
   }

   public SysReadNotice setSysNoticeId(final String sysNoticeId) {
      this.sysNoticeId = sysNoticeId;
      return this;
   }

}
