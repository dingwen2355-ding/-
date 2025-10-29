package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotEmpty;
@Data
@ApiModel
public class CatalogueFileOperationLogGetCurrentStatusRequest extends BaseRequest {
   @ApiModelProperty(
      value = "文件id列表",
      required = true
   )
   private @NotEmpty(
   message = "文件id不能为空"
) List<Long> idList;

}
