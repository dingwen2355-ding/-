package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SpecialSitePlanInfo {
    private String id;
    //企业id
    private String belongCompanyId;
    //标题
    private String title;
    //事故类型
    private String accidentType;

    //事故类型名称
    private String accidentTypeName;
    //危险程度
    private String riskLevel;
    //发生区域
    private String occurrenceArea;
    //影响范围
    private String scopeInfluence;
    //事故征兆
    private String accidentSign;
    //次生衍生事故
    private String secondaryDerivativeAccident;
    //发生季节
    private String occurrenceSeason;
    //应急小组
    private String emergencyTeam;
    //应急小组职责
    private String emergencyTeamDuty;
    //注意事项
    private String cautionDesc;
    //处置措施
    private String disposalMeasures;
}
