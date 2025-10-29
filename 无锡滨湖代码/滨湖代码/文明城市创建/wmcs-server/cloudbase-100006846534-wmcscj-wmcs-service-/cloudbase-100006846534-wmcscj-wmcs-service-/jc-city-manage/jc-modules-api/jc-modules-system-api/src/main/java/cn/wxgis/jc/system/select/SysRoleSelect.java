package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysRoleSelect", description = "角色查询 [传参]")
@Data
public class SysRoleSelect extends PageParam {

    @ApiModelProperty(value = "角色id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
