package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 预警信息
 *
 * @author chd
 * @date 2022/6/8
 */
@Data
public class WarnBean {
    @ApiModelProperty(value = "主键id")
    String id;

    @ApiModelProperty(value = "预警id")
    String warnId;

    @ApiModelProperty(value = "预警标题")
    String warningTitle;

    @ApiModelProperty(value = "一级类型")
    String warningType1;

    @ApiModelProperty(value = "二级类型")
    String warningType2;

    @ApiModelProperty(value = "预警级别")
    String warningLevel;

    @ApiModelProperty(value = "发布时间")
    String releaseTime;

    @ApiModelProperty(value = "预警内容")
    String WarningContent;

    @ApiModelProperty(value = "预警影响")
    String warningInfluence;

    @ApiModelProperty(value = "防御建议")
    String defenseAdvice;

    @ApiModelProperty(value = "是否处理 1:已处理，2：未处理")
    String deal;

    @ApiModelProperty(value = "汛限水位'")
    String GRZ;

    @ApiModelProperty(value = "当前水位")
    String CRZ;

    @ApiModelProperty(value = "警戒水位")
    String WRZ;

    @ApiModelProperty(value = "处理结果")
    String dealContent;
}
