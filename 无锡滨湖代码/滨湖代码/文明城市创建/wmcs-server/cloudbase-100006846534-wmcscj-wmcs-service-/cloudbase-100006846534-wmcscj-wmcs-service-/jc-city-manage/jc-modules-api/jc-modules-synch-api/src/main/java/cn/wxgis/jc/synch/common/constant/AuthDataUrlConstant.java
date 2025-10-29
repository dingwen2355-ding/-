package cn.wxgis.jc.synch.common.constant;

public class AuthDataUrlConstant {

    /**
     * 获取统一身份认证平台 验证token 接口地址
     * GET
     */
    public static String getTokenUrl = "/api/squirrel-szbh/rest/getToken?appId=%s&appSecret=%s";

    /**
     * 获取统一身份认证平台 部门列表 接口地址
     * GET
     */
    public static String getDeptList = "/api/squirrel-szbh/rest/departmentList?appId=%s&token=%s";

    /**
     * 获取统一身份认证平台 用户列表 接口地址
     * GET
     */
    public static String getUserList =  "/api/squirrel-szbh/rest/userList?appId=%s&token=%s";

    /**
     * 获取统一身份认证 用户详情 接口地址
     * GET
     */
    public static String getUserInfo  = "/api/squirrel-szbh/rest/userInfo";

}
