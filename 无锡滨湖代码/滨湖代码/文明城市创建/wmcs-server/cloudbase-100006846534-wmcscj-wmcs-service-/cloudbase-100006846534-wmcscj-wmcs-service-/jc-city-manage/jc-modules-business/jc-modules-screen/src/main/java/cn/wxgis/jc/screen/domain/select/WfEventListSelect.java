package cn.wxgis.jc.screen.domain.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WfEventListSelect extends PageParam {

    @ApiModelProperty(value = "考核周期")
    private String cycle;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "模糊搜索")
    private String name;
}
