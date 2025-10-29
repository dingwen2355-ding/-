package cn.wxgis.jc.synch.unify.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "UnifyLoginRequest", description = "统一身份登录 [传参]")
@Data
public class UnifyLoginRequest {

    /**
     * 使用的授权模式，必选项，此处的值固定为 authorization_code
     */
    private String grant_type;

    /**
     * 客户端的ID，必选项
     */
    private String client_id;

    /**
     * 客户端密钥，必选项
     */
    private String client_secret;

    /**
     * 重定向URI，必选项
     */
    private String redirect_uri;

    /**
     * 表示获得的授权码，必选项
     */
    private String code;

    /**
     * 申请的权限范围，必选项，此处的值固定为"all"
     */
    private String scope;
}
