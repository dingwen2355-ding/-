package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("public.foreign_oa_org")
@ApiModel(
   value = "ForeignOaOrg",
   description = "OA组织部门信息"
)
public class ForeignOaOrg extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("部门名称")
   @TableField("name")
   private String name;
   @ApiModelProperty("部门编码")
   @TableField("parent_code")
   private Integer code;
   @ApiModelProperty("上级部门编码")
   @TableField("parent_code")
   private Integer parentCode;
   @ApiModelProperty("组织机构代码")
   @TableField("node_no")
   private String nodeNo;


}
