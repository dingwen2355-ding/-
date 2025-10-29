package cn.wxgis.jc.screen.domain.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WfEventSelect extends PageParam {

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "点位名称")
    private String pointName;

    @ApiModelProperty(value = "查询状态：1-待办；2-延期；3-办结")
    private Integer statusMark;

    @ApiModelProperty(value = "今日查询状态：1-今日待办；2-今日办结")
    private Integer todayStatus;

    @ApiModelProperty(value = "类型名称")
    private String eventTypeName;

}
