package cn.wxgis.jc.screen.domain.vo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "EventTrendCountVo", description = "事件趋势数据信息")
@Data
public class EventTrendCountVo {

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "数量")
    private Integer count = 0;

    @ApiModelProperty(value = "办结数量")
    private Integer finishCount = 0;

}
