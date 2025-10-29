package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysBaseConfigSaveRequest", description = "系统配置 [传参]")
@Data
public class SysBaseConfigSaveRequest {


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "系统名称")
    private String name;

    @ApiModelProperty(value = "是否自动派发")
    private Integer autoGive;

    @ApiModelProperty(value = "处置时限")
    private Integer limitTime;

    @ApiModelProperty(value = "处置时限单位")
    private String limitTimeUnit;
}
