package cn.wxgis.jc.screen.domain.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CheckPointsSelect extends PageParam {

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "区划编码")
    private String regionCode;

    @ApiModelProperty(value = "点位名称")
    private String name;

    @ApiModelProperty(value = "点位类型id")
    private String typeId;

    @ApiModelProperty(value = "点位类型名称")
    private String typeName;

    @ApiModelProperty(value = "点位类型id集合（切割tyeId中的多点位。用于查询多选的点位列表）")
    private List<String> typeIds;


}
