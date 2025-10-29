package cn.wxgis.jc.screen.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventStatusDto {

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "数量")
    private Integer count;

}
