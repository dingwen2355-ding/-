package cn.wxgis.jc.synch.wmb.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@ApiModel(value = "WorkEventSelect", description = "历史工单事件查询 [传参]")
@Data
public class WorkEventSelect extends PageParam {

    @ApiModelProperty(value = "考核周期id")
    private String cycleId;
    private String checkName;

    @ApiModelProperty(value = "点位类型")
    private String typeId;
    private String typeName;

    @ApiModelProperty(value = "点位")
    private String pointName;

    @ApiModelProperty(value = "存在问题")
    private String checkInfo;

    @ApiModelProperty(value = "区域")
    private String regionName;

    @ApiModelProperty(value = "责任单位")
    private String departName;

    @ApiModelProperty(value = "检查项")
    private String checkItemName;
}
