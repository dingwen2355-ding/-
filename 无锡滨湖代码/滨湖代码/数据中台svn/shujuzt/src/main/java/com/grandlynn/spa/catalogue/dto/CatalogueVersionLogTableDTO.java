package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CatalogueVersionLogTableDTO")
public class CatalogueVersionLogTableDTO {
   private Integer orgId;
   private Integer tableCount;

}
