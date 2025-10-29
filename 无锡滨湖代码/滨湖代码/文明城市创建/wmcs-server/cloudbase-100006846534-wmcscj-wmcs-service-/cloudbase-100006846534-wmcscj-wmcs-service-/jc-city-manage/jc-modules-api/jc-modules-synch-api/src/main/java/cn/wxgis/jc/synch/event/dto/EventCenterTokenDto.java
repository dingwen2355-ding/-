package cn.wxgis.jc.synch.event.dto;

import lombok.Data;

@Data
public class EventCenterTokenDto {

    private String loginType = "sso";

    private String username;

    private String password;


}
