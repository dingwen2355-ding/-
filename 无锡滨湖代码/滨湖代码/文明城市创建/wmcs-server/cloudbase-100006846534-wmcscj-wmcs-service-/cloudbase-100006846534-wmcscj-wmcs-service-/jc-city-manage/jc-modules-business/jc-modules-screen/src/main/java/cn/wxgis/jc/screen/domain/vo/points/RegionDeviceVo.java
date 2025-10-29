package cn.wxgis.jc.screen.domain.vo.points;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "RegionDeviceVo", description = "区域视频信息")
@Data
public class RegionDeviceVo {

    @ApiModelProperty(value = "区域i编码")
    private String regionCode;

    @ApiModelProperty(value = "区域名称")
    private String regionName;

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "通道id")
    private String channelId;

    @ApiModelProperty(value = "坐标，x")
    private String gpsX;

    @ApiModelProperty(value = "坐标，y")
    private String gpsY;

}
