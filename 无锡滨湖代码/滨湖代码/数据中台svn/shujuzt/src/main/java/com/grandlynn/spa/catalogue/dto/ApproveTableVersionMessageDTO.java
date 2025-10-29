package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
@Data
@ApiModel
public class ApproveTableVersionMessageDTO extends AbstractObject implements Serializable {
   private static final long serialVersionUID = -1L;
   @ApiModelProperty("租户")
   private String tenantId;
   @ApiModelProperty("appId")
   private Long appId;
   @ApiModelProperty("生成版本的目录id")
   private Long[] tableIds;
   @ApiModelProperty("版本号")
   private String versionNo;

}
