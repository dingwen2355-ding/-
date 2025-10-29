package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AnalysisCatalogueRankingResponse extends BaseResponse {
   @ApiModelProperty("部门id")
   private Long orgId;
   @ApiModelProperty("部门名称")
   private String orgName;
   @ApiModelProperty("系统数")
   private Integer sysNumber;
   @ApiModelProperty("目录数")
   private Integer cataNumber;

}
