package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 社会力量核心人员实体类
 *
 * @title: SocialForcesMember
 * @Author yf
 * @Date: 2022/4/18
 *
 */
@Data
public class SocialForcesMember {

    @JSONField(name = "ID")
    private Integer id;

    @JSONField(name = "MEMBER_ICON")
    private String memberIcon;

    @JSONField(name = "BELONG_SOCIETY_TEAM_ID")
    private Integer belongSocietyTeamId;

    @JSONField(name = "MEMBER_NAME")
    private String memberName;

    @JSONField(name = "MEMBER_SEX")
    private String memberSex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(name = "MEMBER_BIRTHDAY")
    private Date memberBirthday;

    @JSONField(name = "WORK_STATION")
    private String workStation;

    @JSONField(name = "HELP_TYPE")
    private String helpType;

    @JSONField(name = "PHONE")
    private String phone;

    @JSONField(name = "ID_SPECIAL")
    private String isSpecial;

    @JSONField(name = "IS_USE_ABLE")
    private String isUseAble;

    @JSONField(name = "SPECIAL_MAJOR")
    private String specialMajor;

    @JSONField(name = "CONTENTS")
    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(name = "working_date")
    private Date workingDate;

    @JSONField(name = "SPECIALITIES")
    private String specialities;

    @JSONField(name = "rescue_info")
    private String rescueInfo;

    @JSONField(name = "aptitude_info")
    private String aptitudeInfo;

    private String age;

}