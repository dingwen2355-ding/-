package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysDevice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysDeviceResponse", description = "设备信息")
@Data
public class SysDeviceResponse  extends SysDevice {

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "联系方式")
    private String phonenumber;

}
