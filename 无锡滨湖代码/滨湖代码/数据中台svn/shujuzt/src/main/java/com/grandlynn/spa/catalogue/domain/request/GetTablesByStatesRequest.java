package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
@Data
public class GetTablesByStatesRequest extends BaseRequest {
   @ApiModelProperty("审批状态集合")
   private List<Integer> approveState;
   @ApiModelProperty("OA用户状态集合")
   private List<Integer> oaState;
   @ApiModelProperty("发布用户Id")
   private Long publisher;
   @ApiModelProperty("部门集合")
   private Long[] orgIds;

   public GetTablesByStatesRequest() {
   }

   public GetTablesByStatesRequest(final List<Integer> approveState, final List<Integer> oaState, final Long publisher, final Long[] orgIds) {
      this.approveState = approveState;
      this.oaState = oaState;
      this.publisher = publisher;
      this.orgIds = orgIds;
   }
}
