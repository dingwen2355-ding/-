package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetResourceCodeRequest extends BaseRequest {
   @ApiModelProperty("数据领域")
   private String domain;
   @ApiModelProperty("资源分类")
   private String type;
   @ApiModelProperty("组织机构代码(节点代码)")
   private String nodeNo;

   public GetResourceCodeRequest(String dataDomain, String resourceType, String nodeNo) {
      this.domain = dataDomain;
      this.type = resourceType;
      this.nodeNo = nodeNo;
   }

}
