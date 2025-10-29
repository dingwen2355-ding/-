package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import lombok.Data;

import java.util.List;
@Data
public class WordModelData extends AbstractObject {
   private String orgName;
   private Long orgId;
   private String resourceCode;
   private String dataResourceName;
   private String tableShareType;
   private String shareStatus;
   private String openType;
   private String tableShareDesc;
   private String updatePeriod;
   private String administrationName;
   private String sysName;
   private String tableRemark;
   private Long tableId;
   private List<WordModelFieldData> fieldList;
}
