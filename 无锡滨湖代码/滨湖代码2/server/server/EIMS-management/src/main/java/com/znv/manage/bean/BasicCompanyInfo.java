package com.znv.manage.bean;

import com.znv.manage.bean.user.EvaluateEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BasicCompanyInfo {
    //企业id
    private String companyId;
    //企业名称
    private String companyName;
    //企业类型
    private String companyType;
    //企业类型名称
    private String companyTypeName;
    //企业经营状态
    private int companyState;
    //企业经营状态名称
    private String companyStateName;
    //所属辖区
    private int regionId;
    //所属名称
    private String regionName;
    //区县驻地
    private int countyStation;
    //区县驻地
    private String countyStationName;
    //标准化级别
    private int standardLevel;
    //标准化级别名称
    private String standardLevelName;
    //组织机构代码
    private String orgaCode;
    //法人姓名
    private String bossManager;
    //法人联系方式
    private String bossPhone;
    //实际控制人
    private String mainManager;
    //实际控制人职务
    private String mainManagerJob;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //所属行业
    private String industry;
    //所属行业名称
    private String industryName;
    //详细地址
    private String location;
    //许可证编号
    private String license;
    //许可证有效期开始时间
    private String expirationStartDate;
    //许可证有效期结束时间
    private String expirationEndDate;
    //许可范围
    private String licenseScope;
    //许可证附件
    private String licenseFile;
    //主要安全人员
    private List<SaferInfo> saferInfos;
    //应急值守信息
    private List<DutyInfo> dutyInfos;
    //重大危险源
    private List<HazardSourceInfo> hazardSources;
    //重点关注
    private List<FocusOnInfo> focusOnInfos;
    //企业平面分布图
    private List<PlanGraphInfo> planGraphInfos;
    //综合预案
    private List<ComprehensiveDisposalInfo> comprehensiveDisposalInfo;
    //专项预案
    private List<SpecialSitePlanInfo> specialPlanInfo;
    //现场预案
    private List<SpecialSitePlanInfo> sitePlanInfo;
    //企业评价
    private List<EvaluateEntity> evaluateEntity;
    //24小时值班电话
    private String dutyPhone;
    //应急电话
    private String emergencyPhone;
}
