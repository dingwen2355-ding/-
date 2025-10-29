package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CatalogueTableFieldDTO")
public class CatalogueTableFieldDTO extends BaseResponse {
   private Long sysTableId;
   private String sysTableName;
   private String fieldName;
   private String fieldAlias;
   private String fieldType;
   private String fieldLength;
   private String fieldVersion;
   private Integer fieldState;
   private Integer fieldFlag;
   private String fieldEncoded;
   private String fieldObtuse;
   private String fieldPrimaryKey;
   private String fieldPosition;
   private String fieldDesc;
   private String fieldTotalLog;
   private String fieldTotalMemory;
   private Integer intermediate;
   private String versionNo;
   private String fieldNameCh;
   private String dictState;
   private String dictTable;
   private String dictStandard;
   private String shareType;
   private String shareCondition;
   private String lawDependency;
   private String lawDependencyAttach;
   private String allowNull;
   private String allowIndex;
   private Long publisher;

}
