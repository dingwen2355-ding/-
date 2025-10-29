package cn.wxgis.jc.screen.domain.vo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "EventEndCountVo", description = "工单办结信息")
@Data
public class EventNameCountVo {

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "数量")
    private Integer count = 0;

    @ApiModelProperty(value = "名称")
    private String name;
}
