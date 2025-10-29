package cn.wxgis.jc.screen.domain.vo.event;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "WfEventInfoVo", description = "事件详情信息")
@Data
public class WfEventInfoVo extends WfEventVo {

    @ApiModelProperty(value = "流程实例id")
    private String flowinstanceid;

    @ApiModelProperty(value = "事件节点")
    private List<WfEventNodeVo> nodes = new ArrayList<>();

    @ApiModelProperty(value = "附件")
    private List<String> files = new ArrayList<>();
}
