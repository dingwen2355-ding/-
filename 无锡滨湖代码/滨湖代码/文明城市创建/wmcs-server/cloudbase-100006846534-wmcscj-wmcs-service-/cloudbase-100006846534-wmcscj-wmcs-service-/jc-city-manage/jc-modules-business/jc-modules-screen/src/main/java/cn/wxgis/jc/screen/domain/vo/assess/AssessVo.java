package cn.wxgis.jc.screen.domain.vo.assess;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssessVo {

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "区划")
    private String regionName;

    @ApiModelProperty(value = "分数")
    private BigDecimal score;

}
