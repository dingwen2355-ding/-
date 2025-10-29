package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysRegionSelect", description = "区划查询 [传参]")
@Data
public class SysRegionSelect extends PageParam {

    @ApiModelProperty(value = "区划id")
    private String id;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

}
