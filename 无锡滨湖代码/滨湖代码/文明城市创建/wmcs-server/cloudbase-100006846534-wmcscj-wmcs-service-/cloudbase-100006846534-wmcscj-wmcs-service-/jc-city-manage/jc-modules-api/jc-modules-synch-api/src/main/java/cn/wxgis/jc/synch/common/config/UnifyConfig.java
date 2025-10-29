package cn.wxgis.jc.synch.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 配置信息
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "thrid.config")
public class UnifyConfig {

    /**
     * 里约网关鉴权信息
     */
    private String paasid;
    private String paasToken;

    /**
     * APPID
     */
    private String appId;

    /**
     * 验证秘钥
     */
    private String appSecret;

    /**
     * 重定向URI
     */
    private String redirectUri;

    /**
     * 大屏APPID
     */
    private String screenAppId;

    /**
     * 大屏验证秘钥
     */
    private String screenAppSecret;


    /**
     * 大屏重定向URI
     */
    private String screenRedirectUri;

    /**
     * 使用的授权模式
     */
    private String grantType = "authorization_code";


    /**
     * 申请的权限范围
     */
    private String scope = "all";

    /**
     * 授权类型，必选项，此处的值固定为"code"
     */
    private String responseType = "code";

    /**
     * 同步数据地址
     */
    private String dataSynchUrl;

    /**
     * 单点登陆api地址
     */
    private String oauthApiUrl;

    /**
     * 单点登陆跳转地址
     */
    private String authorizationUrl;

}
