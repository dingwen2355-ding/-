package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysDictTypeSelect", description = "字典类型查询 [传参]")
@Data
public class SysDictTypeSelect extends PageParam {

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典类型状态：0启用；1不启用")
    private Integer status;

}
