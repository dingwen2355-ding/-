package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("TablesAndFieldsDTO")
public class TablesAndFieldsDTO extends BaseResponse {
   private Long sysId;
   private String sysName;
   private String resourceCode;
   private String dataResourceName;
   private String tableShareType;
   private String shareStatus;
   private String openType;
   private String tableShareDesc;
   private String updatePeriod;
   private String administrationName;
   private String tableRemark;
   private Long tableId;
   private Integer index;
   private String fieldNameCh;
   private String fieldName;
   private String fieldType;
   private String fieldLength;
   private String fieldDesc;
}
