package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 社会力量信息实体
 *
 * @title: SocialForces
 * @Author yf
 * @Date: 2022/4/18
 *
 */

@Data
public class SocialForces {

    /**
     * id
     */
    @JSONField(name = "ID")
    private Integer id;

    /**
     * 名称
     *
     */
    @JSONField(name = "SOCIETY_TEAM_NAME")
    private String societyTeamName;

    /**
     * 管理类型
     *
     */
    @JSONField(name = "BE_MANAGED_TYPE")
    private int beManagedType;

    /**
     * 管理单位
     *
     */
    @JSONField(name = "BELONG_UNIT")
    private int belongUnit;

    /**
     * 所属地市
     */
    @JSONField(name = "CITY")
    private int city;

    /**
     * 所属辖区
     *
     */
    @JSONField(name = "REGION")
    private int region;

    /**
     * 专兼职类型
     *
     */
    @JSONField(name = "A_P_TYPE")
    private String apType;

    /**
     * 管理级别
     *
     */
    @JSONField(name = "STORAGE_LEVEL")
    private int storageLevel;

    /**
     * 联系人
     *
     */
    @JSONField(name = "MANAGER")
    private String manager;

    /**
     * 联系人电话
     *
     */
    @JSONField(name = "MANAGER_PHONE")
    private String managerPhone;

    /**
     * 主要负责人
     *
     */
    @JSONField(name = "MAIN_PEOPLE")
    private String mainPeople;

    /**
     * 值班电话
     *
     */
    @JSONField(name = "DUTY_PHONE")
    private String dutyPhone;

    /**
     * 服务区域
     *
     */
    @JSONField(name = "service_area")
    private String serviceArea;

    /**
     * 成员人数
     *
     */
    @JSONField(name = "member_number")
    private int memberNumber;

    /**
     * 经度
     *
     */
    @JSONField(name = "LONGITUDE")
    private String longitude;

    /**
     * 纬度
     *
     */
    @JSONField(name = "LATITUDE")
    private String latitude;

    /**
     * 所属行业
     *
     */
    @JSONField(name = "INDUSTRY")
    private String industry;

    /**
     * 擅长领域
     *
     */
    @JSONField(name = "SPECIALITIES")
    private String specialities;

    /**
     * 救援类别
     *
     */
    @JSONField(name = "HELP_TYPE")
    private String helpType;


    /**
     * 详细地址
     *
     */
    @JSONField(name = "ADDRESS")
    private String address;

    /**
     * 机构设置
     *
     */
    @JSONField(name = "org_set")
    private String orgSet;

    /**
     * 简介
     *
     */
    @JSONField(name = "team_info")
    private String teamInfo;

    /**
     * 资质证书
     *
     */
    @JSONField(name = "aptitude_info")
    private String aptitudeInfo;

    /**
     * 是否可用
     */
    @JSONField(name = "is_use_able")
    private String isUseAble;

    /**
     * 核心队员信息
     *
     */
    private List<SocialForcesMember> socialForcesMembers;

    /**
     * 年度训练情况
     */
    private List<SocialForcesAnnualTraining> socialForcesAnnualTrainings;
}
