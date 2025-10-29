package cn.wxgis.jc.screen.domain.vo.points;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskPointsVo {

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "区划编码")
    private String regionCode;

    @ApiModelProperty(value = "点位类型id")
    private String typeId;

    @ApiModelProperty(value = "点位类型名称")
    private String typeName;

    @ApiModelProperty(value = "点位id")
    private String id;

    @ApiModelProperty(value = "点位名称")
    private String name;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "状态")
    private String status;
}
