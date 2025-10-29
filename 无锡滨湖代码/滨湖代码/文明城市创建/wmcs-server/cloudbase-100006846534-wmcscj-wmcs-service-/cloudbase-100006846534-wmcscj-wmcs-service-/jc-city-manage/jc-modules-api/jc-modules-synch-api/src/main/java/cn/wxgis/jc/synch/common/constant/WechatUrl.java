package cn.wxgis.jc.synch.common.constant;

public class WechatUrl {

    /**
     * 获取政务微信access_token
     * GET
     */
    public static String getAccessTokenUrl = "/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    /**
     * 获取政务微信 获取登陆信息 接口地址
     * GET
     */
    public static String getLoginInfoUrl = "/cgi-bin/user/getuserinfo?access_token=%s&code=%s";

    /**
     * 获取政务微信 获取用户信息 接口地址
     * POST
     */
    public static String getUserInfoUrl = "/cgi-bin/user/getuserdetail?access_token=%s";

    /**
     * 获取政务微信 获取jsapi_ticket 信息
     * POST
     */
    public static String getTicketUrl = "/cgi-bin/get_jsapi_ticket?access_token=%s";


}
