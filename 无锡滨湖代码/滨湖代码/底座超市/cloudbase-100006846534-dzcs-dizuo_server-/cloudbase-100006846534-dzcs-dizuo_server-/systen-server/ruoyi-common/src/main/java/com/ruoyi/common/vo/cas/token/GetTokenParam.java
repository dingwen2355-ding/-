package com.ruoyi.common.vo.cas.token;

import com.ruoyi.common.constant.ServerUrlCon;
import lombok.Data;

@Data
public class GetTokenParam {


    /**
     * 使用的授权模式，必选项，此处的值固定为"authorization_code"
     */
    private String grant_type;


    /**
     * 客户端的ID，必选项
     */
    private String client_id= ServerUrlCon.client_id;

    /**
     * 客户端密钥，必选项
     */
    private String client_secret=ServerUrlCon.client_secret;
    /**
     * 重定向URI，必选项
     */
    private String redirect_uri="http://2.21.137.206:11511/binhudsj";
    /**
     *
     */
    private String code;

    private String scope="all";


    private void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    private void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    private void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }


    private void setScope(String scope) {
        this.scope = scope;
    }
}
