package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@Api("全量比对未匹配的结果")
public class TotalCompareUnmatchDto extends AbstractObject implements Serializable {
   @ApiModelProperty("目录id")
   private Long tableId;
   @ApiModelProperty("目录名")
   private String name;
   @ApiModelProperty("组织机构代码")
   private String nodeNo;
   @ApiModelProperty("组织机构名称")
   private String nodeName;
   @ApiModelProperty("参考清单中对应组织机构下目录数量")
   private Integer count;
}
