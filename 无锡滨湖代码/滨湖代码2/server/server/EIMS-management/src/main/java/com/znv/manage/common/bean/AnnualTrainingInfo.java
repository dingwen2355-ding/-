package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @title: AnnualTrainingInfo
 * @Author yf
 * @Date: 2022/4/11 14:10
 */

@Data
public class AnnualTrainingInfo {

    /**
     * ID
     */
    @JSONField(name = "id")
    private int id;

    /**
     * 年份
     */
    @JsonFormat(pattern = "yyyy", timezone = "GMT+8")
    @JSONField(name = "year")
    private String year;

    /**
     * 标题
     */
    @JSONField(name = "title")
    private String title;

    /**
     * 训练情况简介
     */
    @JSONField(name = "intro")
    private String intro;

    /**
     * 所属单位编号
     */
    @JSONField(name = "belong_team_id")
    private int belongTeamId;

    /**
     * 是否可用
     */
    @JSONField(name = "is_use_able")
    private String isUseAble;

    /**
     * 参与者数量
     */
    @JSONField(name = "participants")
    private Integer participants;

    /**
     * 级别(国家、省、市、区县、基层)
     */
    @JSONField(name = "level")
    private String level;

    /**
     * 上传文档（word、pdf、jpg、png）
     */
    @JSONField(name = "fileUrl")
    private String fileUrl;
}