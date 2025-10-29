package com.ruoyi.common.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * 三方服务URL
 */
@Slf4j
public class ServerUrlCon {


    /**
     * 获取应用超市  URL
     */
//    private static final String apiServer = "http://2.22.51.26:30000/ebus/wxbhtysf/";
    private static final String apiServer = "http://2.21.137.200/";
    /**
     * 单点URL
     */
    private static final String casServer = "http://2.22.51.26:30002/";

    public static final String client_id = "c3piaGdldHRva2Vu";
    public static final String client_secret = "b933b14b5fe60b1cf3b4cb347eae831c";

    public static final String ownPageUrl = "https://2.21.138.89:11511/binhudsj";

    /**
     * 里约单点登录headers
     */
    public static final String x_rio_paasid="wxbhdzcs";

    public static final String paasid_token="7fc3360882f8e971014ccdfd4194cbe5";

    //第一方api
    public static final String firstServer = "http://2.21.137.154:88/wechatApp/api/";

    public static final class firstApiUrl{
        public static String getToken = firstServer + "user/getToken";
        public static String getFlowInfo = firstServer + "wechat/flow/findFlowInfo";
    }

    /**
     * 单点接口
     */
    public static class CasUrl {
        /**
         * 单点获取token
         * 请求地址：http://serverUrl/oauth/token
         * 请求方式：post
         */
        public static String token = casServer + "oauth/token";
        /**
         *
         */
        public static String authorize = casServer + "oauth/authorize";


    }

    /**
     * 获取应用超市
     */
    public static class ApiUrl {

        /**
         * 获取token接口
         */
        public final static String getToken = apiServer + "api/squirrel-szbh/rest/getToken";
        /**
         * 验证用户密码是否是初始密码
         */
        public final static String applicationList = apiServer + "api/squirrel-szbh/rest/applicationList";

        /**
         * 修改密码
         */
        public final static String checkInitPassword = apiServer + "api/squirrel-szbh/rest/checkInitPassword";

        /**
         * 获取所有应用
         */
        public final static String changePassword = apiServer + "api/squirrel-szbh/rest/changePassword";

        /**
         * 获取用户的应用
         */
        public final static String userApplication = apiServer + "api/squirrel-szbh/rest/userApplication";


        /**
         * 获取所有应用分类
         * <p>
         * 请求方式：
         * GET
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	list	用户对应部门职位信息
         * id	string	用户分类id
         * name	string	用户分类名称
         */

        public final static String applicationClassification = apiServer + "api/squirrel-szbh/rest/applicationClassification";

        /**
         * 获取用户已授权的所有应用的单位
         * <p>
         * <p>
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	list	用户对应部门职位信息
         * id	string	用户单位id
         * name	string	用户单位名称
         */
        public final static String applicationDept = apiServer + "api/squirrel-szbh/rest/applicationDept";

        /**
         * 简要描述：
         * 根据应用分类应用单位查询应用
         * <p>
         * <p>
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * applicationClassificationId	是	string	应用分类id
         * deptId	是	string	应用单位id
         * keywords	是	string	关键字
         * current	是	number	当前页
         * size	是	number	每页数量
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	list	用户对应部门职位信息
         * records	list	应用列表
         * total	number	全部数量
         * size	number	每页数
         * current	number	当前页
         * name	string	应用名称
         * appId	string	appId
         * appSecret	string	appSecret
         * description	string	应用详情
         * deptId	number	所属部门
         * contacts	string	联系人
         * telephone	string	联系方式
         * url	string	Url
         * protocol	string	协议类型
         * userColumn	string	用户可展示信息
         * intro	string	应用简介
         * manageDeptId	string	应用单位id
         * manageDeptName	string	应用单位名称
         * loginNumber	number	登录次数
         * isGround	number	是否上架，1-是，0-否
         * isRecommend	number	是否推荐，1-是，0-否
         * 返回示例
         * {
         * "code": "200",
         * "message": "成功",
         * "data": {
         * "records": [
         * {
         * "name": "统一运维平台V2",
         * "appId": "1pum5aJK",
         * "appSecret": "8d14b44629a4c11263cfbfd6ec39a58d603ab7d7",
         * "description": "平台通过智能发现策略，实现设备的统一监控管理，在满足设备状态及性能监控的基础上，通过可视化，实现运维的全景管理。运维功能让企事业单位的IT部门日常业务更加高效、合规和安全，从根本上转变了IT部门的工作",
         * "deptId": 373,
         * "contacts": "赵斌",
         * "telephone": "18952756555",
         * "url": "https://2.21.137.226",
         * "protocol": "OAuth 2.0",
         * "userColumn": "用户名称,用户账号,密码,所属主部门,主部门内是否为上级,是否短信通知,是否启用,主部门职位,主部门手机,其他所属部门",
         * "intro": "平台通过智能发现策略，实现设备的统一监控管理",
         * "manageDeptId": "1665957140268908546",
         * "manageDeptName": "滨湖区",
         * "loginNumber": null,
         * "isGround": 1,
         * "isRecommend": 1
         * },
         * {
         * "name": "数字滨湖城市事件管理平台",
         * "appId": "TYHj1FMY",
         * "appSecret": "3d97685f379c601deab937b2e96b0e63ff46914c",
         * "description": "事件管理平台以实现事件“统一采集、统一分拨、统一考核”的管理目标，通过城市事件管理平台，构建跨层级、跨部门、跨业务、跨系统的业务支撑和事件协同处置体系，实现各类事件的集中受理、智能派单、全程督导、核查",
         * "deptId": 373,
         * "contacts": "王辉",
         * "telephone": "18217077572",
         * "url": "http://119.3.88.200:88/index",
         * "protocol": "OAuth 2.0",
         * "userColumn": "用户名称,用户账号,密码,所属主部门,是否短信通知,是否启用,其他所属部门,用户头像,别名,邮箱,电话,对外简称",
         * "intro": "",
         * "manageDeptId": "1665957140268908546",
         * "manageDeptName": "滨湖区",
         * "loginNumber": null,
         * "isGround": 1,
         * "isRecommend": 1
         * }
         * ],
         * "total": 5,
         * "size": 10,
         * "current": 1,
         * "orders": [],
         * "optimizeCountSql": true,
         * "searchCount": true,
         * "countId": "",
         * "maxLimit": -1,
         * "pages": 1
         * }
         * }
         * 9、获取应用活跃度
         * 简要描述：
         */
        public final static String applicationByClassificationAndDept = apiServer + "api/squirrel-szbh/rest/applicationByClassificationAndDept";

        /**
         * 获取应用活跃度
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * type	是	number	类型，1-全部，2-月度
         * month	是	string	月度，比如2023-06，当查询全部时可不传
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	list	用户对应部门职位信息
         * appId	string	appId
         * name	string	应用名称
         * recordNum	number	活跃度，登录次数
         * rankChange	number	排名变化，0-无变化，正数-排名上升，负数-排名下降
         */
        public final static String applicationActivation = apiServer + "api/squirrel-szbh/rest/applicationActivation";

        /**
         * 获取底座超市总计数量
         * <p>
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	json	数据
         * totalNum	number	总计访问量
         * dayNum	number	今日访问量
         */
        public final static String baseSupermarket = apiServer + "api/squirrel-szbh/rest/baseSupermarket";
        /**
         * 获取应用，工具，组件数量接口
         * <p>
         * 请求方式：
         * GET
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	json	数据
         * yyNum	number	应用数量
         * zjNum	number	组件数量
         * gjNum	number	工具数量
         */
        public final static String appliationCategoryNum = apiServer + "api/squirrel-szbh/rest/appliationCategoryNum";

        /**
         * 简要描述：
         * 获取底座超市总计数量
         * 请求URL：
         * http://{url}/api/squirrel-szbh/rest/smsNotice
         * 请求方式：
         * GET
         * 参数：
         * 参数名	必选	类型	说明
         * token	是	string	token
         * account	否	string	用户账号，可不填，查所有用户
         * start	否	string	开始时间，可不填，比如2023-06-01
         * end	否	string	结束时间，可不填，比如2023-06-01
         * 返回参数说明
         * 参数名	类型	说明
         * code	string	返回码，200成功，其他失败
         * message	string	对返回码的文本描述
         * data	json	数据
         * noticePerson	string	通知人
         * mobile	string	通知人手机号
         * content	string	通知内容
         * noticeTime	string	通知时间
         */
        public final static String smsNotice = apiServer + "api/squirrel-szbh/rest/smsNotice";


        /**
         * •	应用所选的应用分类
         */
        public final static String addApplicationClassification = apiServer + "api/squirrel-szbh/rest/addApplicationClassification";
        /**
         * 应用所选的应用部门
         */
        public final static String addApplicationDept = apiServer + "api/squirrel-szbh/rest/addApplicationDept";
        /**
         * •	上传图片，获取路径
         */
        public final static String uploadFile = apiServer + "api/squirrel-szbh/rest/uploadFile";
        /**
         * •	•	新增应用
         */
        public final static String addApplication = apiServer + "api/squirrel-szbh/rest/addApplication";
        /**
         * •	•	权限申请
         */
        public final static String addUserAuthority = apiServer + "api/squirrel-szbh/rest/addUserAuthority";

        /**
         * •	根据申请人查询应用申请
         */
        public static String applicationApplyForList = apiServer + "/api/squirrel-szbh/rest/applicationApplyForList";
        /**
         * •	根据申请人查询权限申请
         */
        public static String authorityApplyForList = apiServer + "/api/squirrel-szbh/rest/authorityApplyForList";
        /**
         * •	权限申请撤回
         */
        public static String withdrawAuthorityApplyFor = apiServer + "/api/squirrel-szbh/rest/withdrawAuthorityApplyFor";

        /**
         * 验证用户是否是管理员角色
         */
        public static String checkIsAdmin = apiServer + "/api/squirrel-szbh/rest/checkIsAdmin";
        /**
         * 新增和修改已认证过的应用
         */
        public static String addAuthApplication = apiServer + "/api/squirrel-szbh/rest/addAuthApplication";
        /**
         * 上架或者下架已认证的应用
         */
        public static String onOffShelvesApp = apiServer + "/api/squirrel-szbh/rest/onOffShelvesApp";

        /**
         * 获取单个用户信息
         */
        public static String userInfo = apiServer + "/api/squirrel-szbh/rest/userInfo";

        /**
         * 申请统计数据
         */
        public static String getApplyForStatistics = apiServer + "/api/squirrel-szbh/rest/getApplyForStatistics";
        /**
         * 应用访问统计
         */
        public static String getAccessStatistics = apiServer + "/api/squirrel-szbh/rest/getAccessStatistics";
        /**
         * 获取月应用访问排名
         */
        public static String getAppUseRank = apiServer + "/api/squirrel-szbh/rest/getAppUseRank";
        /**
         * 获取月应用权限申请排名
         */
        public static String getApplyForRank = apiServer + "/api/squirrel-szbh/rest/getApplyForRank";
        /**
         * 获取后台首页审批状态分析
         */
        public static String getHomePageAnalysis = apiServer + "/api/squirrel-szbh/rest/getHomePageAnalysis";

        /**
         * 获取本月申请统计echarts
         */
        public static String getApplyForEcharts = apiServer + "/api/squirrel-szbh/rest/getApplyForEcharts";

        /**
         * 获取本月访问统计echar
         */
        public static String getAccessEcharts = apiServer + "/api/squirrel-szbh/rest/getAccessEcharts";
        /**
         * 获取所有应用访问量申请量
         */
        public static String getApplicationList = apiServer + "/api/squirrel-szbh/rest/getApplicationList";

        public static String sendMessage = apiServer + "/api/squirrel-szbh/sms/sendSms";

        /**
         * 底座用户注册数量
         */
        public static String getTotalNums = apiServer + "/api/squirrel-szbh/rest/getDzcsUserNum";


    }


}
