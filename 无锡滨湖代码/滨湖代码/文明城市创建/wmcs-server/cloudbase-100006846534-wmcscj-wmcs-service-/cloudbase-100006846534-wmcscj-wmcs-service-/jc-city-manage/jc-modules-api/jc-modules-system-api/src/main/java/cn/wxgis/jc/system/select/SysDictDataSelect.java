package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysDictDataSelect", description = "字典数据查询 [传参]")
@Data
public class SysDictDataSelect extends PageParam {

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "字典数据状态：0启用；1不启用")
    private Integer status;

}
