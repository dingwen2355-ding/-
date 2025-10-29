package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class CatalogueGetSystemsByOrgIdResponse extends BaseResponse {
   @ApiModelProperty("组织节点挂载系统内容")
   @JsonInclude(Include.NON_NULL)
   private List<SysContentDTO> sysContent;

}
