package com.grandlynn.spa.catalogue.dto.sso;

import com.grandlynn.util.config.Payload;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("单点登录用户信息")
public class SsoUserInfoDTO implements Serializable {
   private int code;
   private boolean success;
   private Payload data;
   private String msg;

   public static class Payload {
      private String jti;
      private String accessToken;
      private SsoUser userInfo;
      private long exp;
      private String refreshToken;

      public Payload() {
      }

      public String getJti() {
         return this.jti;
      }

      public String getAccessToken() {
         return this.accessToken;
      }

      public SsoUser getUserInfo() {
         return this.userInfo;
      }

      public long getExp() {
         return this.exp;
      }

      public String getRefreshToken() {
         return this.refreshToken;
      }

      public void setJti(final String jti) {
         this.jti = jti;
      }

      public void setAccessToken(final String accessToken) {
         this.accessToken = accessToken;
      }

      public void setUserInfo(final SsoUser userInfo) {
         this.userInfo = userInfo;
      }

      public void setExp(final long exp) {
         this.exp = exp;
      }

      public void setRefreshToken(final String refreshToken) {
         this.refreshToken = refreshToken;
      }

   }
   public static class SsoUser {
      @ApiModelProperty("ssoId")
      private Long id;
      @ApiModelProperty("账号")
      private String account;
      @ApiModelProperty("姓名")
      private String name;
      @ApiModelProperty("邮箱")
      private String email;
      @ApiModelProperty("手机号码")
      private String phone;
      @ApiModelProperty("生日")
      private Date birthday;
      @ApiModelProperty("性别")
      private int sex;

      public SsoUser() {
      }

      public Long getId() {
         return this.id;
      }

      public String getAccount() {
         return this.account;
      }

      public String getName() {
         return this.name;
      }

      public String getEmail() {
         return this.email;
      }

      public String getPhone() {
         return this.phone;
      }

      public Date getBirthday() {
         return this.birthday;
      }

      public int getSex() {
         return this.sex;
      }

      public void setId(final Long id) {
         this.id = id;
      }

      public void setAccount(final String account) {
         this.account = account;
      }

      public void setName(final String name) {
         this.name = name;
      }

      public void setEmail(final String email) {
         this.email = email;
      }

      public void setPhone(final String phone) {
         this.phone = phone;
      }

      public void setBirthday(final Date birthday) {
         this.birthday = birthday;
      }

      public void setSex(final int sex) {
         this.sex = sex;
      }
   }
}
