package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueOrgAddChildOrgRequest extends BaseRequest {
   @ApiModelProperty("来自第三方的oa_id")
   private Long oaId;
   @ApiModelProperty(
      value = "选中节点主键id,作为新数据的parentId入库",
      required = true
   )
   private @NotNull(
   message = "选中节点主键id必传"
) Long id;
   @ApiModelProperty(
      value = "节点代码如11210400001120370J2",
      required = true
   )
   private @NotBlank(
   message = "组织代码必填"
) String nodeNo;
   @ApiModelProperty(
      value = "节点全称",
      required = true
   )
   private @NotBlank(
   message = "新建组织节点名字不能为空"
) String nodeName;
   @ApiModelProperty(
      value = "节点简称",
      required = true
   )
   private @NotBlank(
   message = "节点简称必填"
) String nodeNameSimplify;
   @ApiModelProperty(
      value = "节点类型",
      required = true
   )
   private @NotBlank(
   message = "用户自定义当前节点类型必填"
) String nodeType;
   @ApiModelProperty(
      value = "节点属性：1（仅允许添加子节点，不允许挂载系统），0（仅允许挂载系统，不允许添加子节点）",
      example = "0",
      required = true
   )
   private @NotNull(
   message = "节点属性必传"
) String leaf;
   @ApiModelProperty("数据对接联系人")
   private String contact;
   @ApiModelProperty("数据对接联系方式")
   private String phone;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("排序值")
   private Integer sort;
   @ApiModelProperty("分类id")
   private Long orgTypeId;

}
