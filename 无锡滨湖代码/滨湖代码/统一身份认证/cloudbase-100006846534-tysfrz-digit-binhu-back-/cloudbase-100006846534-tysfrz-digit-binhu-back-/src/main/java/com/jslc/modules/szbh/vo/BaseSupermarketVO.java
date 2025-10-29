package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "用户活跃度", description = "用户活跃度")
public class BaseSupermarketVO {

    @ApiModelProperty(value = "总计访问量")
    private Long totalNum;

    @ApiModelProperty(value = "今日访问量")
    private Long dayNum;


}
