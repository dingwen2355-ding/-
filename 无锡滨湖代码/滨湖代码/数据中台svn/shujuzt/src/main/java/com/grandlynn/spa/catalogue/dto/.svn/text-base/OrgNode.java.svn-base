package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("CatalogueOrgQueryOrgTreeResponseOrgNode")
public class OrgNode extends BaseResponse {
   @ApiModelProperty("当前节点名称")
   private List<OrgNode> childOrgList;
   @ApiModelProperty("当前节点名称")
   private String nodeName;
   @ApiModelProperty("当前节点编码")
   private String nodeCode;
   @ApiModelProperty("用户自定义当前节点类型")
   private String nodeType;
   @ApiModelProperty("当前节点：1 启用，0 停用")
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
   @ApiModelProperty("组织简称")
   private String nodeNameSimplify;
   @ApiModelProperty("组织代码（组织业务代码）如11210400001120370J2 改为 统一社会信用代码")
   private String nodeNo;
   @ApiModelProperty("来自第三方的oa_id")
   private Long oaId;
   @ApiModelProperty("组织节点挂载系统内容")
   @JsonInclude(Include.NON_NULL)
   private List<SysContentDTO> sysContent;
   @ApiModelProperty("组织节点下子节点数量统计")
   private long count;
   @ApiModelProperty("对应版本表的旧组织id")
   private Long oldId;
   @ApiModelProperty("排序值")
   private Integer sort;
   @ApiModelProperty("表数据")
   private Integer tableCount;
   @ApiModelProperty("分类名称")
   private String orgTypeName;
   @ApiModelProperty("分类id")
   private Long orgTypeId;
}
