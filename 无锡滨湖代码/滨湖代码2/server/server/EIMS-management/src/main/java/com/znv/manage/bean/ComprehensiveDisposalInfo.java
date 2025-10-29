package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ComprehensiveDisposalInfo {
    private String id;
    //企业id
    private String belongCompanyId;
    //标题
    private String title;
    //主要负责人/电话
    private String mainManager;
    //企业紧急联系人/电话
    private String emergencyContact;
    //处置原则
    private String disposalPrinciple;
    //风险分析
    private String riskAnalysis;
    //注意事项
    private String cautionDesc;
    //危险单元
    private String hazardousUnit;
}
