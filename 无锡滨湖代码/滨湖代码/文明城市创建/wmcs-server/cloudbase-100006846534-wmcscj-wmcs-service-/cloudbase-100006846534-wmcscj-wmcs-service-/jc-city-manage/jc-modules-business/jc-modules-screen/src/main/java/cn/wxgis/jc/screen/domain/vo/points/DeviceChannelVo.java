package cn.wxgis.jc.screen.domain.vo.points;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value = "DeviceChannelVo", description = "视频监控信息")
@Data
public class DeviceChannelVo {

    @ApiModelProperty(value = "通道id")
    private String channelId;

    @ApiModelProperty(value = "通道名称")
    private String name;

    @ApiModelProperty(value = "组织编码")
    private String orgCode;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "坐标，x")
    private BigDecimal gpsX;

    @ApiModelProperty(value = "坐标，y")
    private BigDecimal gpsY;

    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

}
