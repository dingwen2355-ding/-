package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("TableMatterDTO")
public class TableMatterDTO extends AbstractObject {
   private String id;
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
   @ApiModelProperty("实施主体")
   private String DEPT_NAME;
   @ApiModelProperty("事项状态")
   private String TASK_STATE;
   @ApiModelProperty("1 为历史数据，0 为在用数据 ")
   private String IS_HISTORY;
}
