package cn.wxgis.jc.workflow.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfBaseNodeSelect", description = "流程延期申请查询 [传参]")
@Data
public class WfEventExtensionSelect extends PageParam {

    @ApiModelProperty(value = "状态")
    private Integer status;

}
