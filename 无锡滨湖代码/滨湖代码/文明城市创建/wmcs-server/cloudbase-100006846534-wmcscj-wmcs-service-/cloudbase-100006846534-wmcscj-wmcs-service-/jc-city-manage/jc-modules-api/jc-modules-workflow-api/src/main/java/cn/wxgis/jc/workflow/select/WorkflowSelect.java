package cn.wxgis.jc.workflow.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WorkflowSelect", description = "流程查询 [传参]")
@Data
public class WorkflowSelect extends PageParam {

    @ApiModelProperty(value = "事件编号")
    private String code;

    @ApiModelProperty(value = "周期")
    private String cycleId;

    @ApiModelProperty(value = "点位类型")
    private String typeId;

    @ApiModelProperty(value = "事发点位")
    private String checkPoints;

    @ApiModelProperty(value = "事件问题")
    private String content;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ColumnComment("开始时间")
    private String beginTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ColumnComment("结束时间")
    private String endTime;

    @ApiModelProperty(value = "节点处理人id")
    private String handlerid;

    @ApiModelProperty(value = "节点处理部门id")
    private String handleDeptid;

    @ApiModelProperty(value = "上报人id")
    private String reporterId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态数组")
    private Integer[] statusArray;

    @ApiModelProperty(value = "办结类型")
    private Integer endType;

    @ApiModelProperty(value = "事件来源")
    private String eventSource;

    @ApiModelProperty(value = "办结审核")
    private Integer endExamine;

}
