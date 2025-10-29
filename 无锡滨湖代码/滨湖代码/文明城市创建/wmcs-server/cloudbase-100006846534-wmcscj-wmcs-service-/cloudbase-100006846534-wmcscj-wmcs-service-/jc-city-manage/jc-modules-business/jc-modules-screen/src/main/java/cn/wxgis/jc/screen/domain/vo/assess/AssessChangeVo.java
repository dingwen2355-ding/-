package cn.wxgis.jc.screen.domain.vo.assess;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssessChangeVo {

    @ApiModelProperty(value = "涨幅最大区划")
    private String riseRegion;

    @ApiModelProperty(value = "涨幅分值")
    private BigDecimal riseScore;

    @ApiModelProperty(value = "跌幅最大区划")
    private String fallRegion;

    @ApiModelProperty(value = "跌幅分值")
    private BigDecimal fallScore;

}
