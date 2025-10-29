package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysDeviceSaveRequest", description = "设备保存 [传参]")
@Data
public class SysDeviceSaveRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备编号")
    private String code;

    @ApiModelProperty(value = "用户id")
    private String userId;

}
