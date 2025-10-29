package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.extensions.annotation.ExcelNotNull;
import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class ExportOrgTemplateDTO extends AbstractObject implements Serializable {
   private static final long serialVersionUID = -1L;
   @ExcelNotNull
   @ApiModelProperty("组织ID")
   private String nodeCode;
   @ExcelNotNull
   @ApiModelProperty("组织机构代码")
   private String nodeNo;
   @ExcelNotNull
   @ApiModelProperty("节点全称")
   private String nodeName;
   @ExcelNotNull
   @ApiModelProperty("节点简称")
   private String nodeNameSimplify;
   @ExcelNotNull
   @ApiModelProperty("节点类型")
   private String nodeType;
   @ExcelNotNull
   @ApiModelProperty("节点属性：1（仅允许添加子节点，不允许挂载系统）；0（仅允许挂载系统，不允许添加子节点）")
   private String leaf;
   @ExcelNotNull
   @ApiModelProperty("排序")
   private Integer sort;
   @ExcelNotNull
   @ApiModelProperty("所属分类")
   private String orgTypeName;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("错误信息")
   private String warn;
}
