package cn.wxgis.jc.synch.common.constant;

public class EventApiUrl {

    /**
     * 认证登录
     * POST
     */
    public static String getToken = "/wechatApp/api/user/getToken";

    /**
     * 工单推送
     * POST
     */
    public static String customCreateFlowZdfx = "/wechatApp/api/wechat/flow/customCreateFlowZdfx";

    /**
     * 工单分派
     */
    public static String submitFlowForZdfx = "/wechatApp/api/wechat/flow/submitFlowForZdfx";

}
