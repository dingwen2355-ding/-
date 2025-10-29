package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 救援队伍信息实体
 *
 * @title: RescueTeamInfo
 * @Author yf
 * @Date: 2022/4/9 16:08
 *
 */

@Data
public class RescueTeamInfo {

    /**
     * 队伍id
     */
    @JSONField(name = "ID")
    private Integer id;

    /**
     * 队伍名称
     *
     */
    @JSONField(name = "TEAM_NAME")
    private String teamName;

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
    private String region;

    /**
     * 管理类型（队伍类型）
     *
     */
    @JSONField(name = "BE_MANAGED_TYPE")
    private int beManagedType;

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
     * 队员人数
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
     * 队伍简介
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
     * 队伍分队信息
     *
     */
    private List<TeamGroupInfo> teamGroupInfos;
    /**
     * 队伍核心队员信息
     *
     */
    private List<ResourceTeamMember> resourceTeamMembers;

    public static final List<String> LEVEL_DICT = Arrays.asList("国家", "省", "市", "区县", "基层");

    /**
     * 队伍年度训练情况
     */
    private List<AnnualTrainingInfo> annualTrainingInfos;

    public static final List<String> EVENT_DICT = Arrays.asList("自然灾害", "事故灾害", "公共卫生", "社会安全");
    /**
     * 参与重大灾害救援及获得资质证书情况
     */
    private List<ResourceTeamAptitude> resourceTeamAptitudes;

    /**
     * 救援类别名称
     *
     */
    @JSONField(name = "HELP_TYPE_NAME")
    private String helpTypeName;

    /**
     * 区域名称
     *
     */
    @JSONField(name = "REGION_NAME")
    private String regionName;

    /**
     * 擅长领域名称
     *
     */
    @JSONField(name = "SPECIALITIES_NAME")
    private String specialitiesName;

    /**
     * 管理类型名称
     *
     */
    @JSONField(name = "BE_MANAGED_TYPE_NAME")
    private String beManagedTypeName;

    /**
     * 管理级别名称
     *
     */
    @JSONField(name = "STORAGE_LEVEL_NAME")
    private String storageLevelName;
}
