package cn.wxgis.jc.system.model;

import cn.wxgis.jc.system.po.SysDictData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysDictDataVO {

    @ApiModelProperty(value = "字典编码")
    private String dictType;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private List<SysDictData> dict;
}
