package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysDeviceSelect", description = "设备查询 [传参]")
@Data
public class SysDeviceSelect  extends PageParam {

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备编号")
    private String code;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "联系方式")
    private String phonenumber;

}
