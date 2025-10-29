package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class CatalogueGetTablesBySysIdsRequest extends PageRequest {
   @ApiModelProperty("系统ids")
   private List<Long> orgSysIds;

}
