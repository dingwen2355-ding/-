package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WordFirstModelOrgData extends AbstractObject {
   @ApiModelProperty("系统id")
   private Long sysId;
   @ApiModelProperty("序号")
   private Integer index;
   @ApiModelProperty("部门名称")
   private String orgName;
   @ApiModelProperty("部门id")
   private Long orgId;
   @ApiModelProperty("目录表名称")
   private String tableName;
   @ApiModelProperty("目录表主键id")
   private Long tableId;
   @ApiModelProperty("完成时间")
   private String updateTime;
   @ApiModelProperty("备注")
   private String desc;
}
