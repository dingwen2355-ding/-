package cn.wxgis.jc.civilization.assess.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssessVo {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "来源")
    private Integer eventsource;

    @ApiModelProperty(value = "时间")
    private String date;

    @ApiModelProperty(value = "期数")
    private String checkName;

    @ApiModelProperty(value = "检查值")
    private Integer checkValue;

    @ApiModelProperty(value = "责任单位")
    private String deptName;

    @ApiModelProperty(value = "所属街道")
    private String regionName;

    @ApiModelProperty(value = "点位类型")
    private String typeName;

    @ApiModelProperty(value = "点位名称")
    private String pointName;

    @ApiModelProperty(value = "检查日期")
    private String checkDate;

    @ApiModelProperty(value = "检查项")
    private String checkItemName;

    @ApiModelProperty(value = "问题类型")
    private String eventTypeName;

    @ApiModelProperty(value = "问题总数")
    private BigDecimal count;

    @ApiModelProperty(value = "办理总数")
    private BigDecimal handleCount;

    @ApiModelProperty(value = "总分")
    private BigDecimal scoreTotal;

    @ApiModelProperty(value = "办理率")
    private int handleAvg;

    @ApiModelProperty(value = "指标得分")
    private BigDecimal score;

    @ApiModelProperty(value = "存在问题")
    private String content;

}
