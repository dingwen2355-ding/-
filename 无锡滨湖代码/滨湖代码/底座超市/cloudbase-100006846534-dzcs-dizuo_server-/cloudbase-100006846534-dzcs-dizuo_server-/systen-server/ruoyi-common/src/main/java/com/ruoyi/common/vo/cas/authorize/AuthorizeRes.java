package com.ruoyi.common.vo.cas.authorize;

import com.ruoyi.common.constant.ServerUrlCon;
import lombok.Getter;

import static com.ruoyi.common.constant.ServerUrlCon.ownPageUrl;

@Getter
public class AuthorizeRes {


    private String client_id= ServerUrlCon.client_id;

    private String client_secret=ServerUrlCon.client_secret;

    private String redirect_uri=ownPageUrl;

    private String response_type="code";

    private String scope="all";
}
