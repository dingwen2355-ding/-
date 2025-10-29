package com.ruoyi.system.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class InsightFlow {
    String flowNo;
    @JSONField(name = "$class_1$")
    String class1;
    @JSONField(name = "common_deal_group_report_data")
    String department;
    @JSONField(name = "NikeName")
    String nickName;
    @JSONField(name = "$source_flow_no$")
    String sourceFlowNno;
    @JSONField(name = "flowSource")
    String flowSource;
    @JSONField(name= "$close_date$")
    String closeDate;
    String flowStatus;
    @JSONField(name = "$urgency$")
    String urgency;
}
