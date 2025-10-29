package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

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
public class RescueTeamInfoZn extends RescueTeamInfo{

    /**
     * 序号
     */
    @JSONField(name = "序号")
    private String row;

    /**
     * 队伍id
     */
    @JSONField(name = "ID")
    private Integer id;

    /**
     * 队伍名称
     *
     */
    @JSONField(name = "队伍名称")
    private String teamName;

    /**
     * 管理单位
     *
     */
    @JSONField(name = "管理单位")
    private String belongUnitName;

    /**
     * 所属地市
     */
    @JSONField(name = "CITY")
    private int city = 1;

    /**
     * 所属辖区
     *
     */
    @JSONField(name = "所在辖区")
    private String regionName;

    /**
     * 管理类型（队伍类型）
     *
     */
    @JSONField(name = "管理类型")
    private String beManagedTypeName;


    /**
     * 专兼职类型
     *
     */
    @JSONField(name = "专兼类型")
    private String apTypeName;

    /**
     * 管理级别
     *
     */
    @JSONField(name = "管理级别")
    private String storageLevelName;

    /**
     * 联系人
     *
     */
    @JSONField(name = "联系人")
    private String manager;

    /**
     * 联系人电话
     *
     */
    @JSONField(name = "联系电话")
    private String managerPhone;

    /**
     * 主要负责人
     *
     */
    @JSONField(name = "主要责任人")
    private String mainPeople;

    /**
     * 值班电话
     *
     */
    @JSONField(name = "值班电话")
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
    @JSONField(name = "队员人数")
    private int memberNumber;

    /**
     * 经度
     *
     */
    @JSONField(name = "经度")
    private String longitude;

    /**
     * 纬度
     *
     */
    @JSONField(name = "纬度")
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
    @JSONField(name = "擅长领域")
    private String specialities;

    /**
     * 救援类别
     *
     */
    @JSONField(name = "救援类别")
    private String helpTypeName;


    /**
     * 详细地址
     *
     */
    @JSONField(name = "详细地址")
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
    private String isUseAble = "Y";

    /**
     * 队伍核心队员信息
     *
     */
    private List<ResourceTeamMember> resourceTeamMembers;

    /**
     * 队伍年度训练情况
     */
    private List<AnnualTrainingInfo> annualTrainingInfos;
}
