package cn.wxgis.jc.workflow.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfEventAppealSelect", description = "流程申诉申请查询 [传参]")
@Data
public class WfEventAppealSelect extends PageParam {

    @ApiModelProperty(value = "状态")
    private Integer status;

}
