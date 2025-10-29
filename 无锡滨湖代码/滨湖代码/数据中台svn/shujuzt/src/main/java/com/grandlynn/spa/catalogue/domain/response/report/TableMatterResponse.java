package com.grandlynn.spa.catalogue.domain.response.report;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("TableMatterDTO")
public class TableMatterResponse extends AbstractObject {
   @ApiModelProperty("ROWGUID")
   private String ROWGUID;
   @ApiModelProperty("申请事项名称")
   private String TASK_NAME;
   @ApiModelProperty("基本目录编码")
   private String CATALOG_CODE;
   @ApiModelProperty("业务办理项编码")
   private String YWCODE;
   @ApiModelProperty("实施清单编码")
   private String TASK_CODE;
   @ApiModelProperty("实施主体编码（统一社会信用代码）")
   private String DEPT_CODE;

}
