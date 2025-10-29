package com.grandlynn.spa.catalogue.domain.response.report;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ReportCatalogueResponse extends AbstractObject {
   @ApiModelProperty("市共享平台返回的目录id，uuid")
   private String cascadeguid;

}
