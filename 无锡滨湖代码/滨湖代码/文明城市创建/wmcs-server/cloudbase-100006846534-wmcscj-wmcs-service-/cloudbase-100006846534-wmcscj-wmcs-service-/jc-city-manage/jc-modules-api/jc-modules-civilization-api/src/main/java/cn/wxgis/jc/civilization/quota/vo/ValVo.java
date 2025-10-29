package cn.wxgis.jc.civilization.quota.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ValVo {

    @ApiModelProperty(value = "最高值")
    private double maxVal;

    @ApiModelProperty(value = "最小值")
    private double minVal;
}
