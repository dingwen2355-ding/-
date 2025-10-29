package cn.wxgis.jc.civilization.assess.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuotaInfoVo {

    @ApiModelProperty(value = "总分")
    private BigDecimal scoreTotal;

    @ApiModelProperty(value = "当期点位数")
    private int pointsCount;

    @ApiModelProperty(value = "总点位数")
    private int pointsCountTotal;
}
