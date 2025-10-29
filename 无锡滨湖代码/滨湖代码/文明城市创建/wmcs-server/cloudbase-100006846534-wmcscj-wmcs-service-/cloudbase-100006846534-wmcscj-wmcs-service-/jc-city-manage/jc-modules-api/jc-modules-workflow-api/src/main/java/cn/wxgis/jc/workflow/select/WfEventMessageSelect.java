package cn.wxgis.jc.workflow.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfEventMessageSelect", description = "事件消息 [传参]")
@Data
public class WfEventMessageSelect extends PageParam {

    @ApiModelProperty(value = "状态")
    private Integer readFlag;


    @ApiModelProperty(value = "接收人id")
    private String reviceUserId;

}
