package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import com.grandlynn.spa.catalogue.dto.AnalysisListDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class AnalysisCatalogueStatisticsResponse extends BaseResponse {
   @ApiModelProperty("组织名")
   private String orgName;
   @ApiModelProperty("系统数")
   private Integer sysNumber;
   @ApiModelProperty("目录数")
   private Integer tableNumber;
   @ApiModelProperty("字段数")
   private Integer fieldNumber;
   @ApiModelProperty("清单目录数量")
   private Integer totalNumber;
   @ApiModelProperty("系统列表")
   List<AnalysisListDto> sysItems;
   @ApiModelProperty("目录列表")
   List<AnalysisListDto> tableItems;

}
