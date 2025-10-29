package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 灾情信息报送
 *
 * @author chd
 * @date 2022/6/7
 */
@Data
public class DisasterBean {

    @ApiModelProperty(value = "灾情信息主键")
    String id;

    @ApiModelProperty(value = "灾情信息标题")
    String disName;

    @ApiModelProperty(value = "灾情信息类型id")
    String typeId;

    @ApiModelProperty(value = "灾情信息类型名称")
    String typeName;

    @ApiModelProperty(value = "报送科室名称")
    String repPrecinctName;

    @ApiModelProperty(value = "报送科室id,按逗号分隔")
    String repPrecinctId;

    @ApiModelProperty(value = "报送人id")
    String repPersonId;

    @ApiModelProperty(value = "报送人名称")
    String repPersonName;

    @ApiModelProperty(value = "报送时间")
    String repTime;

    @ApiModelProperty(value = "报送人所属科室id")
    String precinctId;

    @ApiModelProperty(value = "创建人id")
    String createId;

    @ApiModelProperty(value = "创建人名称")
    String createName;

    @ApiModelProperty(value = "信息状态：0：未报送 1：已报送")
    String status;

    @ApiModelProperty(value = "内容")
    String remark;

    @ApiModelProperty(value = "突发事件id")
    List<String> eventIds;

    @ApiModelProperty(value = "预警信息id")
    List<String> warnIds;

    List<String> fileUrls;

    @ApiModelProperty(value = "相关资料")
    List<DisasterDataBean> disasterDataBeans;

    @ApiModelProperty(value = "突发事件详情")
    List<EventBean> eventBeans;

    @ApiModelProperty(value = "预警信息详情")
    List<WarnBean> warnBeans;

    @ApiModelProperty(value = "编辑")
    Integer edit = 0;
}
