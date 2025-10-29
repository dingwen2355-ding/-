package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel
public class CatalogueUpdateVersionUrlRequest extends BaseRequest {
   @ApiModelProperty("文件路径")
   private String fullPath;
   @ApiModelProperty("起始时间")
   private Date startDate;
   @ApiModelProperty("截止时间")
   private Date endDate;
   @ApiModelProperty("版本号")
   private String versionNo;

}
