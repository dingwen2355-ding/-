package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import com.grandlynn.spa.catalogue.entity.TotalCompareTableLogDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@ApiModel
public class TotalCompareTableLogBySysResponse extends BaseResponse {
   @ApiModelProperty("系统目录")
   private String tableName;
   @ApiModelProperty("匹配目录")
   private String totalTableName;
   @ApiModelProperty("相似度")
   private BigDecimal route;
   @ApiModelProperty("目录子集")
   private List<TotalCompareTableLogDO> child;

   public TotalCompareTableLogBySysResponse() {
   }

   public TotalCompareTableLogBySysResponse(final String tableName, final String totalTableName, final BigDecimal route, final List<TotalCompareTableLogDO> child) {
      this.tableName = tableName;
      this.totalTableName = totalTableName;
      this.route = route;
      this.child = child;
   }
}
