package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel
public class ForeignCityResourceTypeDTO extends AbstractObject implements Serializable {
   @ApiModelProperty("资源分类")
   private String name;
   @ApiModelProperty("子集")
   private List<ForeignCityResourceTypeDTO> child;
}
