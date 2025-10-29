package com.grandlynn.spa.catalogue.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CheckedHouseResponse {
   @ApiModelProperty("房源核验结果号或者商品房网签合同号")
   private String houseCheckResultNo;
   @ApiModelProperty("住建房屋登记簿编号")
   private String houseRegisterNo;
   @ApiModelProperty("主产权证号（如果是用商品房合同核验，则为空）")
   private String printNo;
   @ApiModelProperty("主产权证编号（如果是用商品房合同核验，则为空）")
   private String docNo;
   @ApiModelProperty("是否抵押 0:无抵押  1:有抵押")
   private Integer dyState;
   @ApiModelProperty("是否查封 0:无查封  1:有查封")
   private Integer cfState;
   @ApiModelProperty("是否交易 0:有交易 1:无交易")
   private Integer jyState;
   @ApiModelProperty("是否出租(开始签约) 0:未出租 1:已出租")
   private Integer rentState;

}
