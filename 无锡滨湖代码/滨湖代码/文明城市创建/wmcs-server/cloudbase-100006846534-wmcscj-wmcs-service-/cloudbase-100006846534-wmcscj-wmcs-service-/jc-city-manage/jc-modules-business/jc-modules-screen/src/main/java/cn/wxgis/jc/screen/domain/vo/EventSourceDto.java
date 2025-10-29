package cn.wxgis.jc.screen.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventSourceDto {

    @ApiModelProperty(value = "来源")
    private Integer eventSource;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
