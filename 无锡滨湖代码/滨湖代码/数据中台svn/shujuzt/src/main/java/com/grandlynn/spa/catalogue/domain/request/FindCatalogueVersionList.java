package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class FindCatalogueVersionList extends BaseRequest {
   @ApiModelProperty("系统id")
   private List<Long> sysIds;
   @ApiModelProperty("版本号")
   private String versionNo;
   @ApiModelProperty("组织ids")
   private List<Long> orgIds;
   public FindCatalogueVersionList() {
   }

   public FindCatalogueVersionList(final List<Long> sysIds, final String versionNo, final List<Long> orgIds) {
      this.sysIds = sysIds;
      this.versionNo = versionNo;
      this.orgIds = orgIds;
   }
}
