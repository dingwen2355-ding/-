package cn.wxgis.jc.synch.common.constant;

public class VideoUrl {

    /**
     * 获取视频汇聚 账号验证 接口地址
     * POST
     */
    public static String getAuthorizeUrl = "/videoService/accounts/authorize";

    /**
     * 获取视频汇聚 保持会话 接口地址
     * PUT
     */
    public static String getKeepaliveUrl = "/videoService/accounts/token/keepalive";

    /**
     * 获取视频汇聚 组织信息 接口地址
     * GET
     */
    public static String getTreeUrl = "/videoService/devicesManager/deviceTree?nodeType=1&typeCode=01&page=1&pageSize=3000";

    /**
     * 获取视频汇聚 组织下的设备信息 接口地址
     * GET
     */
    public static String getDeviceUrl = "/videoService/devicesManager/deviceTree?nodeType=1&typeCode=01;1;ALL&page=1&pageSize=3000";

    /**
     * 获取视频汇聚 组织下的通道信息 接口地址
     * GET
     */
    public static String getChannelUrl = "/videoService/devicesManager/deviceTree?nodeType=1&typeCode=01;0;ALL;ALL&page=1&pageSize=3000";

    /**
     * 获取视频汇聚 获取实时监控 接口地址
     * GET
     */
    public static String getRTSPUrl = "/videoService/realmonitor/uri?channelId=%s&scheme=RTSP";

    /**
     * 获取视频汇聚 获取实时监控 接口地址
     * GET
     */
    public static String getFLVUrl = "/videoService/realmonitor/uri?channelId=%s&scheme=FLV_HTTP";

    /**
     * 获取视频汇聚 获取实时监控 接口地址
     * GET
     */
    public static String getHLSUrl = "/videoService/realmonitor/uri?channelId=%s&scheme=HLS";

}
