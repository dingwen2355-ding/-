package com.grandlynn.spa.catalogue.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class SmsToDeptRequest {
   @ApiModelProperty("orgId")
   private Integer orgId;
   @ApiModelProperty("code")
   private String code;
   @ApiModelProperty("传递参数")
   private List<String> params;


   public SmsToDeptRequest() {
   }

   public SmsToDeptRequest(final Integer orgId, final String code, final List<String> params) {
      this.orgId = orgId;
      this.code = code;
      this.params = params;
   }
}
