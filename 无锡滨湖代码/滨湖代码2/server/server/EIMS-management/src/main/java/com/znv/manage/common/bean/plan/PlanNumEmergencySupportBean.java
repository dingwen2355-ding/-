package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

/**
 * 预案数字化应急保障表
 *
 * @author chd
 * @date 2022/5/10
 */
@Data
public class PlanNumEmergencySupportBean {
    String id;

    String planNumId;

    /**
     * 通信保障
     */
    String communication;

    /**
     * 通信保障详情
     */
    List<SafeguardBean> communicationInfos;

    /**
     * 应急队伍保障
     */
    String emergencyTeamSupport;

    /**
     * 应急队伍保障详情
     */
    List<EmergencyTeamSupportInfo> emergencyTeamSupportInfos;

    /**
     * 应急物资保障
     */
    String emergencySupplies;

    /**
     * 应急物资保障详情
     */
    List<CommandTransfer> emergencySuppliesInfos;

    /**
     * 人员转移保障
     */
    String personTransfer;

    /**
     * 获取人员转移保障详情
     */
    List<CommandShelter> personTransferInfos;

    /**
     * 供电保障
     */
    String powerSupply;

    /**
     * 供电保障详情
     */
    List<SafeguardBean> powerSupplyInfos;

    /**
     * 能源保障
     */
    String energySecurity;

    /**
     * 能源保障详情
     */
    List<SafeguardBean> energySecurityInfos;

    /**
     * 交通运输保障
     */
    String transportation;

    /**
     * 交通运输保障详情
     */
    List<SafeguardBean> transportationInfos;

    /**
     * 医疗保障
     */
    String medicalInsurance;

    /**
     * 医疗保障保障详情
     */
    List<SafeguardBean> medicalInsuranceInfos;

    /**
     * 治安保障
     */
    String security;

    /**
     * 治安保障详情
     */
    List<SafeguardBean> securityInfos;

    /**
     * 资金保障
     */
    String financialSecurity;

    /**
     * 资金保障详情
     */
    List<SafeguardBean> financialSecurityInfos;
}
