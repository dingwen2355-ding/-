package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueOrgQueryOrgTreeRequest extends BaseRequest {
   @ApiModelProperty("节点名称")
   private String nodeName;
   @ApiModelProperty("节点id")
   private Long id;
   @ApiModelProperty("节点下挂载内容：1系统；不传表示仅查询组织树")
   private Integer contentType;
   @ApiModelProperty("统一社会信用代码")
   private String nodeNo;
   @ApiModelProperty("分类id")
   private Long orgTypeId;
   @ApiModelProperty("组织简称")
   private String nodeNameSimplify;

}
