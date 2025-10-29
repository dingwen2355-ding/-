package com.grandlynn.spa.catalogue.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.grandlynn.util.pojo.AbstractObject;
import java.util.Date;

@Data
@ApiModel
public class BaseResponse extends AbstractObject {
   private static final long serialVersionUID = -1L;
   @ApiModelProperty("自增主键")
   @JsonInclude(Include.NON_NULL)
   private Long id;
   @ApiModelProperty("APP_ID")
   @JsonInclude(Include.NON_NULL)
   private Long appId;
   @ApiModelProperty("租户ID")
   @JsonInclude(Include.NON_NULL)
   private String tenantId;
   @ApiModelProperty("创建人")
   @JsonInclude(Include.NON_NULL)
   private String createdBy;
   @ApiModelProperty("更新人")
   @JsonInclude(Include.NON_NULL)
   private String updatedBy;
   @ApiModelProperty("备注")
   @JsonInclude(Include.NON_NULL)
   private String remark;
   @ApiModelProperty("创建时间")
   @JsonInclude(Include.NON_NULL)
   private Date createdTime;
   @ApiModelProperty("更新时间")
   @JsonInclude(Include.NON_NULL)
   private Date updatedTime;

}
