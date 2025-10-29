package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueGetVersionsResponse extends BaseResponse {
   @ApiModelProperty("目录编制自定义版本号如V1.0.2102010001")
   private String versionNo;
   @ApiModelProperty("版本排序字段：使用long类型记录自70-01-01到现在的毫秒数，作为版本排序")
   private Long versionSort;
   @ApiModelProperty("当前版本对应的组织树数据")
   private Object orgData;
   @ApiModelProperty("当前版本全量数据")
   private Object versionContentData;
   @ApiModelProperty("当前版本状态：1启用中；2已停用；默认1")
   private Integer versionState;

}
