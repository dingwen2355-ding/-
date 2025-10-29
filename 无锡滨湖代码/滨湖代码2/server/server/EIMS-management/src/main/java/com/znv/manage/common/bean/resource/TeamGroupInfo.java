package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @title: 队伍分队信息实体类
 * @Author yf
 * @Date: 2022/6/13 14:38
 */
@Data
public class TeamGroupInfo {

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "belong_team_id")
    private int belongTeamId;

    @JSONField(name = "group_name")
    private String groupName;

    @JSONField(name = "longitude")
    private String longitude;

    @JSONField(name = "latitude")
    private String latitude;

    @JSONField(name = "address")
    private String address;

}
