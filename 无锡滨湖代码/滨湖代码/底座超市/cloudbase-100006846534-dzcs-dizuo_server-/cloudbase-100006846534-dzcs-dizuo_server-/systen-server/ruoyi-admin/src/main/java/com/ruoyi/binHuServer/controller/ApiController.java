package com.ruoyi.binHuServer.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.binHuServer.controller.vo.AppUseRankVo;
import com.ruoyi.binHuServer.controller.vo.ApplyForRankVo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.vo.AddApplicationReq;
import com.ruoyi.common.vo.AddUserAuthorityReq;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.common.vo.api.ApplicationByClassificationAndDeptParam;
import com.ruoyi.common.vo.api.SmsNoticeReq;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.system.customer.domain.CmApp;
import com.ruoyi.system.customer.service.HouseInfoService;
import com.ruoyi.system.customer.service.RequestService;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.enums.SendMessageEnum.authorityWithdraw;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ApiController {

    private final static String initUserPwd = "C>V4[VEZE}k?";

    private final RequestService requestService;
    private final ISysUserService sysUserService;

    private SysLoginService loginService;
    @Autowired
    private HouseInfoService houseInfoService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    /**
     * 获取用户信息
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/getCasUserInfo")
    public JSONObject getCasUserInfo(String code) throws Exception {
        String commonRes = requestService.getCasTokenInfoObj(code);
        JSONObject obj = JSONObject.parseObject(commonRes);
        if (obj.getInteger("code") == 200) {
            JSONObject data = obj.getJSONObject("data");
            JSONObject userInfo = data.getJSONObject("userInfo");
            String accessToken = getAccessToken(userInfo);
            userInfo.put("apiToken", accessToken);
            data.put("userInfo", userInfo);
            obj.put("data", data);
        }
        return obj;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/getbUserInfo")
    public JSONObject getUserInfo(String account) throws Exception {
        String commonRes = requestService.getUserInfo(account);
        JSONObject obj = JSONObject.parseObject(commonRes);
//        if (obj.getInteger("code") == 200) {
//            JSONObject data = obj.getJSONObject("data");
//            JSONObject userInfo = data.getJSONObject("userInfo");
//            String accessToken = getAccessToken(userInfo);
//            userInfo.put("apiToken", accessToken);
//            data.put("userInfo", userInfo);
//            obj.put("data", data);
//        }
        return obj;
    }

    @GetMapping(value = "/sendMessage")
    public CommonRes sendMessage(@RequestParam String mobile, @RequestParam String msg) throws Exception {
        String[] mobiles = mobile.contains(",") ? mobile.split(",") : new String[]{mobile};
        return requestService.sendMessage(msg, mobiles);
    }

    private String getAccessToken(JSONObject userInfo) {

        String account = userInfo.getString("account");
        SysUser sysUser = sysUserService.selectUserByUserName(account);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setUserName(account);
            sysUser.setEmail(userInfo.getString("email"));
            sysUser.setPassword(SecurityUtils.encryptPassword(initUserPwd));
            sysUser.setPhonenumber(userInfo.getString("phone"));
            sysUser.setNickName(userInfo.getString("name"));
        }

        Long[] roles = null;

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        try {
            CommonRes commonRes = requestService.checkIsAdmin(map);
            log.error("checkIsAdmin:{},{}", account, commonRes);
            if (commonRes.getCode() == 200) {
                // 管理员
                if (StringUtils.equals(commonRes.getData().toString(), "1")) {
                    roles = new Long[]{5L};
                    userInfo.put("adminFlag", true);
                } else {
                    // 非管理员
                    roles = new Long[]{2L};
                    userInfo.put("adminFlag", false);
                }
            }
        } catch (Exception e) {
            roles = new Long[]{2L};
        }
        // 设置默认角色类型是管理员
        sysUser.setRoleIds(roles);
        if (sysUser.getUserId() != null) {
            sysUserService.updateUser(sysUser);
        } else {
            sysUserService.insertUser(sysUser);
        }
        return loginService.login(sysUser.getUserName(), initUserPwd, null, null);
    }

    @GetMapping(value = "/getApiToken")
    public JSONObject getApiToken(@RequestParam String account) throws Exception {
        String commonRes = requestService.getApiToken(true);
        JSONObject obj = JSONObject.parseObject(commonRes);
        return obj;
    }

    /**
     * 验证用户密码是否是初始密码
     *
     * @param account
     * @return
     */
    @GetMapping(value = "/checkInitPassword")
    public CommonRes checkInitPassword(String account) throws Exception {
        CommonRes commonRes = requestService.checkInitPassword(account);
        return commonRes;
    }

    /**
     * 修改密码
     *
     * @param account
     * @param newPassword
     * @return
     */
    @GetMapping(value = "/changePassword")
    public CommonRes changePassword(String account, String newPassword, String oldPassword) throws Exception {
        CommonRes commonRes = requestService.changePassword(account, newPassword, oldPassword);
        return commonRes;
    }

    /**
     * 获取所有应用
     *
     * @return
     */
    @GetMapping(value = "/applicationList")
    public CommonRes applicationList() throws Exception {
        CommonRes commonRes = requestService.applicationList();
        return commonRes;
    }

    /**
     * 申请统计数据
     *
     * @return
     */
    @GetMapping(value = "/getApplyForStatistics")
    public CommonRes getApplyForStatistics() throws Exception {
        CommonRes commonRes = requestService.getApplyForStatistics();
        return commonRes;
    }

    /**
     * 应用访问统计
     *
     * @return
     */
    @GetMapping(value = "/getAccessStatistics")
    public CommonRes getAccessStatistics() throws Exception {
        List<CmApp> lists = houseInfoService.getAllAppListForAdmin(null);
        CommonRes commonRes = requestService.getAccessStatistics();
        Set<String> casAppIdSet = lists.stream().map(CmApp::getCasAppId).filter(Objects::nonNull).collect(Collectors.toSet());
        Assert.notNull(commonRes, "commonRes is null");
        Assert.notNull(commonRes.getData(), "data is null");
        JSONArray arrays = JSONArray.from(commonRes.getData());
        List<Object> id = arrays.stream().filter(x -> {
            JSONObject data = JSONObject.from(x);
            String appSecret = data.getString("id");
            return casAppIdSet.contains(appSecret);
        }).collect(Collectors.toList());
        commonRes.setData(id);
        return commonRes;
    }


    @GetMapping(value = "/getAppUseRank")
    public CommonRes getAppUseRank() throws Exception {

        CommonRes accessStatistics = requestService.getAccessStatistics();
        if (accessStatistics.getCode() != 200) {
            return accessStatistics;
        }
        List<AppUseRankVo> tmp = new ArrayList<>();
        List<JSONObject> jsonObjects = JSONArray.parseArray(JSONArray.toJSONString(accessStatistics.getData()), JSONObject.class);
        for (JSONObject item : jsonObjects) {
            tmp.add(AppUseRankVo.builder()
                    .name(item.getString("applicationName"))
                    .number(item.getIntValue("viewNum"))
                    .percent("")
                    .build());
        }
        if (!CollectionUtils.isEmpty(tmp)) {
            tmp = tmp.stream().sorted(Comparator.comparing(AppUseRankVo::getNumber).reversed()).limit(8).collect(Collectors.toList());
        }
        accessStatistics.setData(tmp);
        return accessStatistics;
    }

    /**
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getApplyForRank")
    public CommonRes getApplyForRank() throws Exception {

        CommonRes applyForStatistics = requestService.getApplyForStatistics();

        if (applyForStatistics.getCode() != 200) {
            return applyForStatistics;
        }

        List<ApplyForRankVo> tmp = new ArrayList<>();
        List<JSONObject> jsonObjects = JSONArray.parseArray(JSONArray.toJSONString(applyForStatistics.getData()), JSONObject.class);

        for (JSONObject item : jsonObjects) {
            tmp.add(ApplyForRankVo.builder()
                    .name(item.getString("applicationName"))
                    .value(item.getIntValue("applyForNum"))
                    .build());
        }
        if (!CollectionUtils.isEmpty(tmp)) {
            tmp = tmp.stream().sorted(Comparator.comparing(ApplyForRankVo::getValue).reversed()).limit(8).collect(Collectors.toList());
        }
        applyForStatistics.setData(tmp);
        return applyForStatistics;
    }

    /**
     * 获取后台首页审批状态分析
     *
     * @return
     */
    @GetMapping(value = "/getHomePageAnalysis")
    public CommonRes getHomePageAnalysis() throws Exception {
        CommonRes commonRes = requestService.getHomePageAnalysis();
        return commonRes;
    }

    /**
     * 获取本月访问统计echar
     *
     * @return
     */
    @GetMapping(value = "/getAccessEcharts")
    public CommonRes getAccessEcharts() throws Exception {
        CommonRes commonRes = requestService.getAccessEcharts();
        return commonRes;
    }

    /**
     * 获取本月申请统计echarts
     *
     * @return
     */
    @GetMapping(value = "/getApplyForEcharts")
    public CommonRes getApplyForEcharts() throws Exception {
        CommonRes commonRes = requestService.getApplyForEcharts();
        return commonRes;
    }

    /**
     * 获取本月申请统计echarts
     *
     * @return
     */
    @GetMapping(value = "/getApplicationList")
    public CommonRes getApplicationList() throws Exception {
        CommonRes commonRes = requestService.getApplicationList();
        return commonRes;
    }


    /**
     * 获取当前用户的应用菜单
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/userApplication")
    public CommonRes userApplication(@RequestParam(required = false) String code,
                                     @RequestParam(required = false) String account,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String current,
                                     @RequestParam(required = false) String size
    ) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.userApplication(code, account, keyword, current, size);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取当前菜单列表异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 获取所有应用分类
     *
     * @return
     */
    @GetMapping(value = "/applicationClassification")
    public CommonRes applicationClassification() {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.applicationClassification();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取所有应用分类异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 获取用户已授权的所有应用的单位
     *
     * @return
     */
    @GetMapping(value = "/applicationDept")
    public CommonRes applicationDept() {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.applicationDept();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取用户已授权的所有应用的单位异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 根据应用分类应用单位查询应用
     *
     * @param applicationByClassificationAndDeptParam
     * @return
     */
    @GetMapping(value = "/applicationByClassificationAndDept")
    public CommonRes applicationByClassificationAndDept(ApplicationByClassificationAndDeptParam applicationByClassificationAndDeptParam) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.applicationByClassificationAndDept(applicationByClassificationAndDeptParam);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("根据应用分类应用单位查询应用异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 获取应用活跃度
     * token	是	string	token
     * type	是	number	类型，1-全部，2-月度
     * month	是	string	月度，比如2023-06，当查询全部时可不传
     *
     * @return
     */
    @GetMapping(value = "/applicationActivation")
    public CommonRes applicationActivation(@RequestParam Integer type, @RequestParam String month) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.applicationActivation(type, month);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取应用活跃度异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 底座超市
     *
     * @return
     */
    @GetMapping(value = "/baseSupermarket")
    public CommonRes baseSupermarket() {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.baseSupermarket();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("底座超市异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 参数：
     * 参数名	必选	类型	说明
     * token	是	string	token
     * 返回参数说明
     * 参数名	类型	说明
     * code	string	返回码，200成功，其他失败
     * message	string	对返回码的文本描述
     * data	list	应用信息
     * id	string	节点id
     * parentId	string	父节点id
     * title	string	应用名称
     * nodeType	string	节点类型，1-应用分类，2-应用
     * isGround	string	是否上架，0-否，1-是
     * isRecommend	string	是否推荐，0-否，1-是
     * url	string	Url
     * intro	string	应用简介
     * description	string	应用描述
     * logo	string	应用图片
     * deptId	string	应用单位id
     * deptName	string	应用单位名称
     * children	string	子节点
     *
     * @return
     */
    @GetMapping(value = "/appliationCategoryNum")
    public CommonRes appliationCategoryNum() {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.appliationCategoryNum();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("底座超市异常");
            return commonRes;
        }

        return commonRes;
    }

    @GetMapping(value = "/smsNotice")
    public CommonRes smsNotice(SmsNoticeReq smsNoticeReq) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.smsNotice(smsNoticeReq);
            commonRes = requestService.smsNotice(smsNoticeReq);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取底座超市总计数量异常");
            return commonRes;
        }

        return commonRes;
    }

    @GetMapping(value = "/addApplicationClassification")
    public CommonRes addApplicationClassification() {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.addApplicationClassification();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("应用所选的应用分类异常");
            return commonRes;
        }

        return commonRes;
    }

    @GetMapping(value = "/addApplicationDept")
    public CommonRes addApplicationDept() {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.addApplicationDept();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("应用所选的应用部门异常");
            return commonRes;
        }

        return commonRes;
    }

    @PostMapping(value = "/uploadFile")
    public CommonRes uploadFile(MultipartFile file) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.uploadFile(file);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("上传图片异常");
            return commonRes;
        }

        return commonRes;
    }

    @PostMapping(value = "/addApplication")
    public CommonRes addApplication(@RequestBody AddApplicationReq param) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.addApplication(param);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("新增应用异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 权限申请
     *
     * @return
     */
    @PostMapping(value = "/addUserAuthority")
    public CommonRes addUserAuthority(@RequestBody AddUserAuthorityReq addUserAuthorityReq) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.addUserAuthority(addUserAuthorityReq);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("权限申请异常");
            return commonRes;
        }


        try {
            SysUser sysUser = sysUserService.selectUserById(SecurityUtils.getUserId());
            Map<String, String> param = addUserAuthorityReq.getParam();
            sysNoticeService.insertNotice(SysNotice.getCmAppMessageDetailForAuthApply(SendMessageEnum.authorityCreate, param.get("appName"), sysUser.getUserId().toString(), sysUserService.selectUserById(SecurityUtils.getUserId()), new Date()));

        } catch (Exception e) {
            log.error("addUserAuthority 短信错误....");
        }
        return commonRes;
    }

    /**
     * •	根据申请人查询应用申请
     *
     * @param param
     * @return
     */
    @GetMapping(value = "/applicationApplyForList")
    public CommonRes applicationApplyForList(@RequestParam Map<String, Object> param) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.applicationApplyForList(param);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("权限申请异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * •	根据申请人查询权限申请
     *
     * @param param
     * @return
     */
    @GetMapping(value = "/authorityApplyForList")
    public CommonRes authorityApplyForList(@RequestParam Map<String, Object> param) {

        CommonRes commonRes = null;
        try {
            commonRes = requestService.authorityApplyForList(param);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("权限申请异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * •	权限申请撤回异常
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/withdrawAuthorityApplyFor")
    public CommonRes withdrawAuthorityApplyFor(
           @RequestBody Map<String,String> params
    ) {
        CommonRes commonRes = null;
        if (CollectionUtils.isEmpty(params)){
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("参数不能为空");
            return commonRes;
        }

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("id", params.get("id"));
            commonRes = requestService.withdrawAuthorityApplyFor(param);

            SysUser sysUser = sysUserService.selectUserById(SecurityUtils.getUserId());
            // 给申请用户发消息
            sysNoticeService.insertNotice(
                    SysNotice.getAuthorityMessageDetail(authorityWithdraw, sysUser.getUserId(), sysUser.getNickName(), params.get("appName")));

        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage(e.getMessage());
            return commonRes;
        }

        return commonRes;
    }

    //登录状态，0是未登录状态（根据自己的业务做判断）
    static int loginSatatus = 0;
}
