package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 突发事件
 *
 * @author chd
 * @date 2022/6/8
 */
@Data
public class EventBean {
    @ApiModelProperty(value = "主键id")
    String id;

    @ApiModelProperty(value = "事件id")
    String eventId;

    @ApiModelProperty(value = "一级事件类型")
    String type;

    @ApiModelProperty(value = "二级事件类型")
    String type2;

    @ApiModelProperty(value = "事件标题")
    String title;

    @ApiModelProperty(value = "详情")
    String details;

    @ApiModelProperty(value = "事件等级")
    String eventLevel;

    @ApiModelProperty(value = "伤亡情况#死亡")
    String casualtyDeath;

    @ApiModelProperty(value = "伤亡情况#受伤")
    String casualtyInjured;

    @ApiModelProperty(value = "伤亡情况#涉险")
    String casualtyDanger;

    @ApiModelProperty(value = "发生时间")
    String happenTime;

    @ApiModelProperty(value = "发生地址")
    String address;

    @ApiModelProperty(value = "上报人")
    String reportPeople;

    @ApiModelProperty(value = "上报人联系方式")
    String reportPeoplePhone;

    @ApiModelProperty(value = "附件地址")
    String attachment;

    @ApiModelProperty(value = "当前状态")
    String status;

    @ApiModelProperty(value = "经度")
    String gpsx;

    @ApiModelProperty(value = "纬度")
    String gpsy;

    @ApiModelProperty(value = "上报时间")
    String reportTime;

    @ApiModelProperty(value = "默认处理")
    String dealResult;
}
