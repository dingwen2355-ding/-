package cn.wxgis.jc.workflow.web.vo;

import cn.wxgis.jc.workflow.po.WfBaseNode;
import cn.wxgis.jc.workflow.po.WfBaseRoute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 节点信息
 */
@ApiModel(value = "NodeInfoVO", description = "节点信息")
@Data
public class NodeInfoVO {

    @ApiModelProperty(value = "节点信息对象")
    private WfBaseNode node;

    @ApiModelProperty(value = "路由信息")
    private List<WfBaseRoute> routeList;
}
