package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueOrgEditOrgRequest extends BaseRequest {
   @ApiModelProperty(
      value = "组织id",
      required = true
   )
   private @NotNull(
   message = "组织id不能为空"
) Long id;
   @ApiModelProperty("来自第三方的oa_id")
   private Long oaId;
   @ApiModelProperty(
      value = "当前节点名称",
      required = true
   )
   private @NotBlank(
   message = "当前节点名称不能为空"
) String nodeName;
   @ApiModelProperty(
      value = "用户自定义当前节点类型（用户输入）",
      required = true
   )
   private @NotBlank(
   message = "用户自定义当前节点类型（用户输入）"
) String nodeType;
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
   @ApiModelProperty("排序值")
   private Integer sort;
   @ApiModelProperty("分类id")
   private Long orgTypeId;

}
