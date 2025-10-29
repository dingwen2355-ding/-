package com.grandlynn.spa.catalogue.dto.sso;

import lombok.Data;

import java.io.Serializable;
@Data
public class AppDepList implements Serializable {
   private String address;
   private String fullName;
   private String telephone;
   private int sort;
   private String briefIntroduction;
   private int type;
   private int parentId;
   private int partyUserLimit;
   private String organizationCode;
   private String domainName;
   private String name;
   private int id;
   private String shortName;
}
