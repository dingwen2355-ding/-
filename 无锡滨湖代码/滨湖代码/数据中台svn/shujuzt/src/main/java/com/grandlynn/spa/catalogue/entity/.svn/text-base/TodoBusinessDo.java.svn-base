package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("待办业务")
@TableName("todo_business")
public class TodoBusinessDo extends BaseEntity {
   @ApiModelProperty("主题")
   private String theme;
   @ApiModelProperty("内容")
   private String content;
   @ApiModelProperty("相关业务ID")
   private Long businessId;
   @ApiModelProperty("发送用户id")
   private Long forwardUserId;
   @ApiModelProperty("接收用户id")
   private Long receivedUserId;
   @ApiModelProperty("接收部门id")
   private Long receivedOrgId;
   @ApiModelProperty("接收角色id")
   private Long receivedRoleId;
   @ApiModelProperty("状态（0未读，1已读）")
   private Integer state;
   @TableField(
      exist = false
   )
   @ApiModelProperty("发送用户头像")
   private String forwardUserIcon;
   @TableField(
      exist = false
   )
   @ApiModelProperty("发送用户姓名")
   private String forwardUserName;

   public static TodoBusinessDoBuilder builder() {
      return new TodoBusinessDoBuilder();
   }

   public TodoBusinessDo() {
   }

   public TodoBusinessDo(final String theme, final String content, final Long businessId, final Long forwardUserId, final Long receivedUserId, final Long receivedOrgId, final Long receivedRoleId, final Integer state, final String forwardUserIcon, final String forwardUserName) {
      this.theme = theme;
      this.content = content;
      this.businessId = businessId;
      this.forwardUserId = forwardUserId;
      this.receivedUserId = receivedUserId;
      this.receivedOrgId = receivedOrgId;
      this.receivedRoleId = receivedRoleId;
      this.state = state;
      this.forwardUserIcon = forwardUserIcon;
      this.forwardUserName = forwardUserName;
   }

   public static class TodoBusinessDoBuilder {
      private String theme;
      private String content;
      private Long businessId;
      private Long forwardUserId;
      private Long receivedUserId;
      private Long receivedOrgId;
      private Long receivedRoleId;
      private Integer state;
      private String forwardUserIcon;
      private String forwardUserName;

      TodoBusinessDoBuilder() {
      }

      public TodoBusinessDoBuilder theme(final String theme) {
         this.theme = theme;
         return this;
      }

      public TodoBusinessDoBuilder content(final String content) {
         this.content = content;
         return this;
      }

      public TodoBusinessDoBuilder businessId(final Long businessId) {
         this.businessId = businessId;
         return this;
      }

      public TodoBusinessDoBuilder forwardUserId(final Long forwardUserId) {
         this.forwardUserId = forwardUserId;
         return this;
      }

      public TodoBusinessDoBuilder receivedUserId(final Long receivedUserId) {
         this.receivedUserId = receivedUserId;
         return this;
      }

      public TodoBusinessDoBuilder receivedOrgId(final Long receivedOrgId) {
         this.receivedOrgId = receivedOrgId;
         return this;
      }

      public TodoBusinessDoBuilder receivedRoleId(final Long receivedRoleId) {
         this.receivedRoleId = receivedRoleId;
         return this;
      }

      public TodoBusinessDoBuilder state(final Integer state) {
         this.state = state;
         return this;
      }

      public TodoBusinessDoBuilder forwardUserIcon(final String forwardUserIcon) {
         this.forwardUserIcon = forwardUserIcon;
         return this;
      }

      public TodoBusinessDoBuilder forwardUserName(final String forwardUserName) {
         this.forwardUserName = forwardUserName;
         return this;
      }

      public TodoBusinessDo build() {
         return new TodoBusinessDo(this.theme, this.content, this.businessId, this.forwardUserId, this.receivedUserId, this.receivedOrgId, this.receivedRoleId, this.state, this.forwardUserIcon, this.forwardUserName);
      }

      public String toString() {
         return "TodoBusinessDo.TodoBusinessDoBuilder(theme=" + this.theme + ", content=" + this.content + ", businessId=" + this.businessId + ", forwardUserId=" + this.forwardUserId + ", receivedUserId=" + this.receivedUserId + ", receivedOrgId=" + this.receivedOrgId + ", receivedRoleId=" + this.receivedRoleId + ", state=" + this.state + ", forwardUserIcon=" + this.forwardUserIcon + ", forwardUserName=" + this.forwardUserName + ")";
      }
   }
}
