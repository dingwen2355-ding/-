package cn.wxgis.jc.synch.common.constant;

public class OauthUrlConstant {

    /**
     * 获取统一身份平台 oauth认证信息 接口地址
     * POST
     */
    public static String getOAuthCode = "/oauth/authorize?client_id=%s&client_secret=%s&redirect_uri=%s&response_type=%s&scope=%s";

    /**
     * 获取统一身份平台 登录token信息 接口地址
     * POST
     */
    public static String getOAuthLoginToken = "/oauth/token?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&scope=%s";

    /**
     * 获取统一身份平台 获取用户信息
     * GET
     */
    public static String getOAuthUser  = "/user/token?token=%s";

    /**
     * 获取统一身份平台 退出接口
     * GET
     */
    public static String logout  = "/logout?logoutUrl=%s";

}
