package com.znv.manage.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 参与重大灾害救援及获得资质证书情况
 */
@Accessors(chain = true)
@Data
public class ResourceTeamAptitude {

    private Integer id;

    private Integer belongTeamId;

    private String time;

    private String title;

    private String event;

    private String organization;

    private String level;

    private Integer participants;

    private String address;

    private String intro;

    private String fileUrl;

}