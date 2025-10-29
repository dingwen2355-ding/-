package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
@Data
@ApiModel
public class CataloguePublishAllCatalogueRequest extends BaseRequest {
   @ApiModelProperty("系统主键id数组")
   private Long[] sysId;

}
