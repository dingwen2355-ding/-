package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysMenuSelect", description = "菜单查询 [传参]")
@Data
public class SysMenuSelect extends PageParam {

    @ApiModelProperty(value = "菜单id")
    private String id;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "平台")
    private Integer platform;

    @ApiModelProperty(value = "菜单状态:1隐藏;0显示")
    private Integer visible;

}
