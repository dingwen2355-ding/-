package com.grandlynn.spa.catalogue.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("分类管理")
public class CatalogueOrgTypeResponse implements Serializable {
   private Long id;
   @ApiModelProperty("系统分类名称")
   private String typeName;
   @ApiModelProperty("关联组织数")
   private Integer orgNum;

}
