package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueOrgVersionQueryOrgTreeRequest extends BaseRequest {
   @ApiModelProperty("节点名称")
   private String nodeName;
   @ApiModelProperty("节点id")
   private Long id;
   @ApiModelProperty("节点下挂载内容：1系统；不传表示仅查询组织树")
   private Integer contentType;
   @ApiModelProperty(
      value = "版本号",
      required = true
   )
   private @NotBlank(
   message = "版本号不能为空"
) String versionNo;

}
