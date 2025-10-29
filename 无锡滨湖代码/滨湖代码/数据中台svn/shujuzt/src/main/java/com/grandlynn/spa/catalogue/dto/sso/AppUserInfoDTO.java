package com.grandlynn.spa.catalogue.dto.sso;

import lombok.Data;

import java.io.Serializable;
@Data
public class AppUserInfoDTO implements Serializable {
   private String name;
   private String account;
   private int majorDept;
   private int isLeaderInDept;
   private String majorPosition;
   private String majorMobile;
   private String otherDept;
   private String isLeaderInOtherDept;
   private String otherPosition;
   private int gender;
   private String avatar;
   private String englishName;
   private String email;
   private String telephone;
   private String externalCorpName;
   private String sort;
}
