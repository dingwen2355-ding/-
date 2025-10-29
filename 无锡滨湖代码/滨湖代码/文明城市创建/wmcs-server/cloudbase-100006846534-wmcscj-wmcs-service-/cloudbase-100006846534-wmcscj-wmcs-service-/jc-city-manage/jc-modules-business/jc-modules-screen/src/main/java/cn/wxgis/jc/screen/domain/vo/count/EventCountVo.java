package cn.wxgis.jc.screen.domain.vo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "EventCountVo", description = "通用返回字段信息")
@Data
public class EventCountVo {

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "数量")
    private Integer count = 0;

    @ApiModelProperty(value = "处理数量")
    private Integer handleCount = 0;

    @ApiModelProperty(value = "板块问题指数")
    private Integer totalCount = 0;

}
