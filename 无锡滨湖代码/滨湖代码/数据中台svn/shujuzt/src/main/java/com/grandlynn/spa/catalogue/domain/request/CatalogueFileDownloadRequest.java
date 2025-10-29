package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@ApiModel
public class CatalogueFileDownloadRequest extends BaseRequest {
   @ApiModelProperty(
      value = "文件组",
      required = true
   )
   private @NotBlank(
   message = "文件组不能为空"
) String fileGroup;
   @ApiModelProperty(
      value = "文件路径",
      required = true
   )
   private @NotBlank(
   message = "文件路径不能为空"
) String filePath;

}
