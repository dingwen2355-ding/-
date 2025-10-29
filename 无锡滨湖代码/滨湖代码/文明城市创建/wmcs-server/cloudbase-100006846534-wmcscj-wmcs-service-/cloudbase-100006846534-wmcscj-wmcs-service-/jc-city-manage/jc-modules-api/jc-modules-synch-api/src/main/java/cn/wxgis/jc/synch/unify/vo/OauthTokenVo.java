package cn.wxgis.jc.synch.unify.vo;

import lombok.Data;

@Data
public class OauthTokenVo {

    private String id;

    private String accessToken;

    private String userInfo;

    private String exp;

    private String refreshToken;

}
