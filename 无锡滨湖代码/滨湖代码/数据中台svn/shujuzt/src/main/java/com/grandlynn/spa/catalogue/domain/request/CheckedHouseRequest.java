package com.grandlynn.spa.catalogue.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CheckedHouseRequest {
   @ApiModelProperty("姓名")
   private String name;
   @ApiModelProperty("查询类型: 01.房屋所有权证 02.不动产权证 03.商品房合同备案号")
   private String printType;
   @ApiModelProperty("产权证号 或者 商品房合同备案号")
   private String printNo;
   @ApiModelProperty("产权人证件类型 1:身份证 2:港澳台身份证 3:护照 4:户口簿 5:军官证 6:组织机构代码 7:营业执照 8:其他")
   private String certType;
   @ApiModelProperty("产权人证件号")
   private String certNo;
   @ApiModelProperty("区域")
   private String areaName;

}
