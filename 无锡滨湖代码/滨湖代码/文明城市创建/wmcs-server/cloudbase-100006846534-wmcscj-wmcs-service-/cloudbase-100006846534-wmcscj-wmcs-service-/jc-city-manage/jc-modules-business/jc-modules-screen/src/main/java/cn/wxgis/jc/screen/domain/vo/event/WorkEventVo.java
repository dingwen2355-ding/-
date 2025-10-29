package cn.wxgis.jc.screen.domain.vo.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkEventVo {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private String code;

    @ApiModelProperty(value = "检查名称")
    private String checkName;

    @ApiModelProperty(value = "检查值")
    private Integer checkValue;

    @ApiModelProperty(value = "点位名称")
    private String pointName;

    @ApiModelProperty(value = "点位类型名称")
    private String typeName;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "来源：")
    private Integer eventSource;
}