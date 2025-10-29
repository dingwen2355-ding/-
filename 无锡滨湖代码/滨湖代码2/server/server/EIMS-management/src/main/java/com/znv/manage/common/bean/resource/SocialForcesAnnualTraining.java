package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 社会力量年度训练情况实体类
 *
 * @title: SocialForcesAnnualTraining
 * @Author yf
 * @Date: 2022/4/18
 */

@Data
public class SocialForcesAnnualTraining {

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
     * 总结
     */
    @JSONField(name = "summary")
    private String summary;

    /**
     * 所属单位编号
     */
    @JSONField(name = "belong_society_team_id")
    private int belongSocietyTeamId;

    /**
     * 是否可用
     */
    @JSONField(name = "is_use_able")
    private String isUseAble;
}