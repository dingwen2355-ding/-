package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysDeviceBindUserRequest", description = "设备绑定用户保存 [传参]")
@Data
public class SysDeviceBindUserRequest {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

}
