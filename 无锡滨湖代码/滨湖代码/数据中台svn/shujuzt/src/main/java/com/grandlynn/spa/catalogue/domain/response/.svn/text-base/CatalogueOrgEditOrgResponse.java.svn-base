package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueOrgEditOrgResponse extends BaseResponse {
   private String nodeName;
   private String nodeCode;
   private String nodeType;
   private Object enable;
   @ApiModelProperty("父节点主键id，默认顶级父节点的父节点主键id为0")
   private Long parentId;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("当前节点：1（仅允许添加子节点，不允许挂载系统），0（仅允许挂载系统，不允许添加子节点）")
   private String leaf;
   @ApiModelProperty("数据对接联系人")
   private String contact;
   @ApiModelProperty("数据对接联系方式")
   private String phone;
   @ApiModelProperty("节点简称")
   private String nodeNameSimplify;
   @ApiModelProperty("节点代码（节点业务代码）如11210400001120370J2")
   private String nodeNo;
   @ApiModelProperty("来自第三方的oa_id")
   private Long oaId;

}
