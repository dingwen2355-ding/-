package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class FindCatalogueListRequest extends BaseRequest {
   @ApiModelProperty("系统id")
   private List<Long> sysIds;
   @ApiModelProperty("数据形态")
   private Integer intermediate;
   @ApiModelProperty("组织ids")
   private List<Long> orgIds;

   public List<Long> getSysIds() {
      return this.sysIds;
   }

   public Integer getIntermediate() {
      return this.intermediate;
   }

   public List<Long> getOrgIds() {
      return this.orgIds;
   }

   public void setSysIds(final List<Long> sysIds) {
      this.sysIds = sysIds;
   }

   public void setIntermediate(final Integer intermediate) {
      this.intermediate = intermediate;
   }

   public void setOrgIds(final List<Long> orgIds) {
      this.orgIds = orgIds;
   }

   public FindCatalogueListRequest(List<Long> sysIds, Integer intermediate, List<Long> orgIds) {
      this.sysIds = sysIds;
      this.orgIds = orgIds;
      this.intermediate = intermediate;
   }
}
