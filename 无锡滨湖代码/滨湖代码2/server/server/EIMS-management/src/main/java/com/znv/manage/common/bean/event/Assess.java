package com.znv.manage.common.bean.event;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TCfgAssess)实体类
 *
 * @author makejava
 * @since 2022-12-12 10:28:58
 */
@Data
public class Assess implements Serializable {
    private static final long serialVersionUID = 758129628128247547L;

    private Integer id;

    @ApiModelProperty(value = "评估表名")
    private String name;

    @ApiModelProperty(value = "关联事件")
    private Integer eventId;

    @ApiModelProperty(value = "关联事件标题")
    private String eventTitle;

    @ApiModelProperty(value = "评估时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date assessTime;

    @ApiModelProperty(value = "总得分")
    private Integer totalScore;

    @ApiModelProperty(value = "应急准备得分")
    private Integer prepareScore;

    @ApiModelProperty(value = "预测预警得分")
    private Integer warningScore;

    @ApiModelProperty(value = "事件接报得分")
    private Integer criminalScore;

    @ApiModelProperty(value = "事件响应得分")
    private Integer responseScore;

    @ApiModelProperty(value = "善后处置得分")
    private Integer afterScore;

    @ApiModelProperty(value = "应急指挥得分")
    private Integer commandScore;

}
