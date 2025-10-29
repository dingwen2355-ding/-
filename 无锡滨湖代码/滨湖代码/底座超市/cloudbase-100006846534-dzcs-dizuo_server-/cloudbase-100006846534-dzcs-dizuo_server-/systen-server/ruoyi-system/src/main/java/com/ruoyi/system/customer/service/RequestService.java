package com.ruoyi.system.customer.service;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RestTemplateUtils;
import com.ruoyi.common.constant.ServerUrlCon;
import com.ruoyi.common.util.Md5Util;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.vo.AddApplicationReq;
import com.ruoyi.common.vo.AddUserAuthorityReq;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.common.vo.api.ApplicationByClassificationAndDeptParam;
import com.ruoyi.common.vo.api.SmsNoticeReq;
import com.ruoyi.system.customer.domain.ApiToken;
import com.ruoyi.system.customer.mapper.ApiTokenMapper;
import com.ruoyi.system.customer.vo.AddAuthApplicationVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ruoyi.common.constant.ServerUrlCon.*;
import static com.ruoyi.common.constant.ServerUrlCon.CasUrl.token;

@Slf4j
@AllArgsConstructor
@Service
public class RequestService {

    private final ApiTokenMapper apiTokenMapper;
    private final RestTemplateUtils restTemplateUtils;


    /**
     * 根据 授权码 获取当前用户信息及token
     *
     * @param code
     * @return
     */
    public String getCasTokenInfo(String code) {
        // 不存在就请求 cas 接口获取
        List<String> param = new ArrayList<>();
        param.add("grant_type=authorization_code");
        param.add("client_id=" + client_id);
        param.add("client_secret=" + client_secret);
        param.add("code=" + code);
        param.add("redirect_uri=" + ServerUrlCon.ownPageUrl);
        param.add("scope=all");
        String url = token + "?" + String.join("&", param);
        System.out.println(url);
        String commonRes = restTemplateUtils.post(url, null, String.class, new HashMap<>());
        JSONObject account = JSONObject.parseObject(commonRes);

        if (account.getInteger("code") != 200) {
            return null;
        }
        account = account.getJSONObject("data");
        return account.getJSONObject("userInfo").getString("account");
    }

    /**
     * 根据 授权码 获取当前用户信息及token
     *
     * @param code
     * @return
     */
    public String getCasTokenInfoObj(String code) {
        // 不存在就请求 cas 接口获取
        List<String> param = new ArrayList<>();
        param.add("grant_type=authorization_code");
        param.add("client_id=" + client_id);
        param.add("client_secret=" + client_secret);
        param.add("code=" + code);
        param.add("redirect_uri=" + ServerUrlCon.ownPageUrl);
        param.add("scope=all");
        String url = token + "?" + String.join("&", param);
        System.out.println(url);
        return restTemplateUtils.post(url, null, String.class, new HashMap<>());

    }

    /**
     * 数据超市获取token
     *
     * @return
     */
    public synchronized String getApiToken(boolean refresh) throws Exception {

        /**
         * 先查询数据库有没有值
         */
        ApiToken apiToken = apiTokenMapper.findOneMax();
        if (apiToken != null && !refresh) {
            if (apiToken.getExpired().compareTo(Calendar.getInstance().getTime()) > 0) {
                return apiToken.getToken();
            }
        }
        String unixTimeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String randomChar = UUID.randomUUID().toString();
        //签名
        String sign = SecureUtil.sha256(unixTimeStamp + paasid_token + randomChar + unixTimeStamp);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rio-paasid", x_rio_paasid);
        headers.add("x-rio-timestamp", unixTimeStamp);
        headers.add("x-rio-nonce", randomChar);
        headers.add("x-rio-signature", sign);
        log.error("aaacccddparmas" + ServerUrlCon.ApiUrl.getToken + "?appId=" + client_id + "&appSecret=" + client_secret, null, CommonRes.class, headers);
//        CommonRes commonRes = restTemplateUtils.get(ServerUrlCon.ApiUrl.getToken + "?appId=" + client_id + "&appSecret=" + client_secret, CommonRes.class);
        CommonRes commonRes = restTemplateUtils.get(ServerUrlCon.ApiUrl.getToken + "?appId=" + client_id + "&appSecret=" + client_secret, null, CommonRes.class, headers);
        log.error("aaaccc获取返回结果" + commonRes);
        if (commonRes.getCode() != 200) {
            log.error("获取 数据超市token 失败{}", commonRes);
            throw new Exception("获取 数据超市token 失败");
        }
        String token = commonRes.getData().toString();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 1800);
        apiTokenMapper.insert(new ApiToken(token, instance.getTime(), Calendar.getInstance().getTime()));
        return token;
    }

    /**
     * 获取所有应用
     *
     * @return
     */
    public CommonRes applicationList() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.applicationList + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 申请统计数据
     *
     *      {
     *     "data": [
     *         {
     *             "id": "1673222396863172609",
     *             "applicationName": " 无锡市滨湖区公共数据资源共享门户",
     *             "appId": "aKzRzTZN",
     *             "appSecret": "",
     *             "applyForNum": "4"
     *         }
     *     ],
     *     "code": 200,
     *     "success": null,
     *     "msg": null,
     *     "message": "成功"
     * }
     *
     * @return
     */
    public CommonRes getApplyForStatistics() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getApplyForStatistics + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 应用访问统计
     *
     *
     *      {
     *     "data": [
     *         {
     *             "id": "1665645246953852929",
     *             "applicationName": "统一运维管理系统",
     *             "appId": "fYwMtGN8",
     *             "appSecret": "0863e8270613a3868f371d7e13922c9ca72e3669",
     *             "viewNum": "421"
     *         }
     *     ],
     *     "code": 200,
     *     "success": null,
     *     "msg": null,
     *     "message": "成功"
     * }
     *
     *
     * @return
     */
    public CommonRes getAccessStatistics() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getAccessStatistics + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 获取月应用访问排名
     * {
     *     "data": [
     *         {
     *             "name": "数字滨湖城市事件管理平台",
     *             "number": 66,
     *             "percent": "20.00"
     *         }
     *     ],
     *     "code": 200,
     *     "success": null,
     *     "msg": null,
     *     "message": "成功"
     * }
     * @return
     */
    public CommonRes getAppUseRank() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getAppUseRank + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 获取月应用权限申请排名
     *      {
     *     "data": [
     *         {
     *             "name": "统一运维管理系统",
     *             "value": 2
     *         },
     *
     *     ],
     *     "code": 200,
     *     "success": null,
     *     "msg": null,
     *     "message": "成功"
     * }
     *
     * @return
     */
    public CommonRes getApplyForRank() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getApplyForRank + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 获取后台首页审批状态分析
     *
     * @return
     */
    public CommonRes getApplyForEcharts() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getApplyForEcharts + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 获取本月访问统计echar
     *
     * @return
     */
    public CommonRes getAccessEcharts() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getAccessEcharts + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 应用访问统计
     *
     * @return
     */
    public CommonRes getHomePageAnalysis() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getHomePageAnalysis + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 获取所有应用访问量申请量
     *
     * @return
     */
    public CommonRes getApplicationList() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.getApplicationList + "?token=" + getApiTokenForReq(), CommonRes.class);
    }

    /**
     * 获取用户的应用
     *
     * @return
     */
    public CommonRes userApplication(String code, String account, String keyword, String current, String size) throws Exception {
        if (StringUtils.isBlank(account)) {
            account = getCasTokenInfo(code);
        }

        List<String> param = new ArrayList<>();
        param.add("token=" + getApiTokenForReq());
        param.add("account=" + account);
        if (StringUtils.isNotBlank(keyword)) {
            param.add("keyword=" + keyword);
        }
        param.add("current=" + current);
        param.add("size=" + size);
        String url = ServerUrlCon.ApiUrl.userApplication + "?" + String.join("&", param);
        return restTemplateUtils.get(url, CommonRes.class);
    }

    /**
     * 验证用户密码是否是初始密码
     *
     * @return
     */
    public CommonRes checkInitPassword(String account) throws Exception {
        String url = ServerUrlCon.ApiUrl.checkInitPassword + "?token=" + getApiTokenForReq() + "&account=" + account;
        return restTemplateUtils.get(url, CommonRes.class);
    }

    /**
     * 修改密码
     *
     * @param account
     * @param newPassword
     * @param oldPassword
     * @return
     */
    public CommonRes changePassword(String account, String newPassword, String oldPassword) throws Exception {
        newPassword = Md5Util.encryption(newPassword);
        oldPassword = Md5Util.encryption(oldPassword);
        String url = ServerUrlCon.ApiUrl.changePassword + "?token=" + getApiTokenForReq() + "&account=" + account + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword;
        CommonRes commonRes = restTemplateUtils.get(url, CommonRes.class);
        if (StringUtils.equals(commonRes.getMessage(), "token无效")) {
            String apiToken = getApiToken(true);
            url = ServerUrlCon.ApiUrl.changePassword + "?token=" + apiToken + "&account=" + account + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword;
            return restTemplateUtils.get(url, CommonRes.class);
        }
        return restTemplateUtils.get(url, CommonRes.class);
    }

    public CommonRes applicationClassification() throws Exception {
        String url = ServerUrlCon.ApiUrl.applicationClassification + "?token=" + getApiTokenForReq();
        return restTemplateUtils.get(url, CommonRes.class);
    }

    public CommonRes applicationDept() throws Exception {
        String url = ServerUrlCon.ApiUrl.applicationDept + "?token=" + getApiTokenForReq();
        return restTemplateUtils.get(url, CommonRes.class);
    }

    public CommonRes applicationByClassificationAndDept(ApplicationByClassificationAndDeptParam applicationByClassificationAndDeptParam) throws Exception {
        String url = ServerUrlCon.ApiUrl.applicationByClassificationAndDept + "?token=" + getApiTokenForReq() + "&applicationClassificationId=" + applicationByClassificationAndDeptParam.getApplicationClassificationId() +
                "&deptId=" + applicationByClassificationAndDeptParam.getDeptId() +
                "&keywords=" + applicationByClassificationAndDeptParam.getKeywords() +
                "&current=" + applicationByClassificationAndDeptParam.getCurrent() +
                "&size=" + applicationByClassificationAndDeptParam.getSize();
        return restTemplateUtils.get(url, CommonRes.class);

    }

    public CommonRes applicationActivation(Integer type, String month) throws Exception {
        String url = ServerUrlCon.ApiUrl.applicationActivation + "?token=" + getApiTokenForReq() + "&type=" + type +
                "&month=" + month;
        return restTemplateUtils.get(url, CommonRes.class);
    }

    public CommonRes baseSupermarket() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.baseSupermarket + "?token=" + getApiTokenForReq(), CommonRes.class);

    }

    public CommonRes appliationCategoryNum() throws Exception {
        return restTemplateUtils.get(ServerUrlCon.ApiUrl.appliationCategoryNum + "?token=" + getApiTokenForReq(), CommonRes.class);

    }

    public CommonRes smsNotice(SmsNoticeReq smsNoticeReq) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.smsNotice + "?token=" + getApiTokenForReq());

        if (StringUtils.isNotBlank(smsNoticeReq.getAccount())) {
            url.append("&account=").append(smsNoticeReq.getAccount());
        }
        if (StringUtils.isNotBlank(smsNoticeReq.getStart())) {
            url.append("&start=").append(smsNoticeReq.getStart());
        }
        if (StringUtils.isNotBlank(smsNoticeReq.getEnd())) {
            url.append("&end=").append(smsNoticeReq.getEnd());
        }
        return restTemplateUtils.get(url.toString(), CommonRes.class);

    }


    /**
     * 应用所选的应用分类
     *
     * @return
     * @throws Exception
     */
    public CommonRes addApplicationClassification() throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.addApplicationClassification + "?token=" + getApiTokenForReq());
        return restTemplateUtils.get(url.toString(), CommonRes.class);
    }

    /**
     * 新增应用所选的应用部门
     *
     * @return
     * @throws Exception
     */
    public CommonRes addApplicationDept() throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.addApplicationDept + "?token=" + getApiTokenForReq());
        return restTemplateUtils.get(url.toString(), CommonRes.class);
    }

    /**
     * 得到注册用户数
     *
     * @return
     * @throws Exception
     */
    public CommonRes getUserTotal() throws Exception {
        StringBuilder url = new StringBuilder(ApiUrl.getTotalNums + "?token=" + getApiTokenForReq());
        return restTemplateUtils.get(url.toString(), CommonRes.class);
    }

    /**
     * 文件上传
     *
     * @return
     * @throws Exception
     */
    public CommonRes uploadFile(MultipartFile file) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.uploadFile + "?token=" + getApiTokenForReq());
        return restTemplateUtils.uploadFile(url.toString(), file);
    }

    public CommonRes uploadFile(String filepath) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.uploadFile + "?token=" + getApiTokenForReq());
        return restTemplateUtils.uploadFile(url.toString(), filepath);
    }

    /**
     * •	新增应用
     *
     * @return
     * @throws Exception
     */
    public CommonRes addApplication(AddApplicationReq param) throws Exception {
        param.setToken(getApiTokenForReq());
        String url = ServerUrlCon.ApiUrl.addApplication;
        String jsonString = JSONObject.toJSONString(param);
        log.error("jsonString:,{}", jsonString);
        return restTemplateUtils.postJson(url, CommonRes.class, jsonString);
    }

    /**
     * 权限申请
     *
     * @return
     * @throws Exception
     */
    public CommonRes addUserAuthority(AddUserAuthorityReq param) throws Exception {
//        param.put("token",getApiTokenForReq());
        param.setToken(getApiTokenForReq());
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.addUserAuthority);
        String jsonString = JSONObject.toJSONString(param);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        jsonObject.remove("param");
        log.error("jsonString: \n {}", jsonString);
        return restTemplateUtils.postJson(url.toString(), CommonRes.class, jsonObject.toString());
    }

    /**
     * 根据申请人查询权限申请
     *
     * @return
     * @throws Exception
     */
    public CommonRes authorityApplyForList(Map<String, Object> param) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.authorityApplyForList);
        paramTranBuffForGet(param, url);
        return restTemplateUtils.get(url.toString(), CommonRes.class, param);
    }
    /**
     * 根据申请人查询权限申请
     *
     * @return
     * @throws Exception
     */
    public CommonRes withdrawAuthorityApplyFor(Map<String, Object> param) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.withdrawAuthorityApplyFor);
        paramTranBuffForGet(param, url);
        return restTemplateUtils.post(url.toString(),null, CommonRes.class);
    }

    /**
     * 应用申请撤回
     * @return
     * @throws Exception
     */
    public CommonRes applicationApplyForList(Map<String, Object> param) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.applicationApplyForList);
        paramTranBuffForGet(param, url);
        return restTemplateUtils.get(url.toString(), CommonRes.class, param);
    }

    /**
     * 根据申请人查询权限申请
     *
     * @return
     * @throws Exception
     */
    public CommonRes checkIsAdmin(Map<String, Object> param) throws Exception {
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.checkIsAdmin);
        paramTranBuffForGet(param, url);
        return restTemplateUtils.get(url.toString(), CommonRes.class);
    }

    /**
     * 根据申请人查询权限申请
     *
     * @return
     * @throws Exception
     */
    public CommonRes addAuthApplication(AddAuthApplicationVo addAuthApplicationVo) throws Exception {
        String apiTokenForReq = getApiTokenForReq();
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.addAuthApplication);
        url.append("?token=").append(apiTokenForReq);
        addAuthApplicationVo.setToken(getApiTokenForReq());
        String jsonString = JSONObject.toJSONString(addAuthApplicationVo);
        log.error("addAuthApplication: \n {}", jsonString);
        return restTemplateUtils.postJson(url.toString(), CommonRes.class, jsonString);
    }

    /**
     * 获取单个用户信息
     *
     * @return
     * @throws Exception
     */
    public String getUserInfo(String account) throws Exception {
        String apiTokenForReq = getApiTokenForReq();
        StringBuilder url = new StringBuilder(ServerUrlCon.ApiUrl.userInfo);
        url.append("?token=").append(apiTokenForReq);
        url.append("&account=").append(account);
        url.append("&appId=").append(client_id);
        log.error("urljieguo: \n {}", url);
        return restTemplateUtils.get(url.toString(), String.class);
    }

    /**
     * @param mobiles 手机号 用逗号分割
     * @param msg     发送消息
     * @return
     * @throws Exception
     */
    public CommonRes sendMessage(String msg, String... mobiles) throws Exception {
        log.error("sendMessage 进入开始发送方法,{},{}", msg, mobiles);
        if (mobiles == null || mobiles.length == 0) {
            return new CommonRes() {
                {
                    setCode(400);
                    setMsg("手机号列表不能为空");
                }
            };
        }
        List<String> matchMobiles = Stream.of(mobiles).distinct().filter(item -> item.matches("^1[0-9]{10}$")).collect(Collectors.toList());

        log.error(" x.matches 后的手机号列表,{}",  mobiles);

        if (CollectionUtils.isEmpty(matchMobiles)) {
            return new CommonRes() {
                {
                    setCode(400);
                    setMsg("手机号格式不正确");
                }
            };
        }
        log.error(" x.matches 开始发送....,{}",  mobiles);
        String mobile = String.join(",", mobiles);
        String apiTokenForReq = getApiTokenForReq();
        String url = ApiUrl.sendMessage + "?token=" + apiTokenForReq +
                "&appId=" + client_id +
                "&mobile=" + mobile +
                "&msg=" + msg;
        CommonRes commonRes = restTemplateUtils.get(url, CommonRes.class);
        log.error("sendMessage end,{}", commonRes);
        return commonRes;
    }
/*
    public CommonRes sendMessage(String msg,String ... mobiles) throws Exception {
        log.error("sendMessage 开始发送,{},{}",msg,mobiles);
        boolean b = Arrays.stream(mobiles).allMatch(x -> x.matches("^1[0-9]{10}$"));

        log.error(" x.matches 开始发送....,{}",b);
        if(!b) return new CommonRes() {{setCode(400);setMsg("手机号格式不正确");}};
        String mobile= String.join(",", mobiles);
        String apiTokenForReq = getApiTokenForReq();
        String url = ApiUrl.sendMessage + "?token=" + apiTokenForReq +
                "&appId=" + client_id +
                "&mobile=" + mobile +
                "&msg=" + msg;
        CommonRes commonRes = restTemplateUtils.get(url, CommonRes.class);
        log.error("sendMessage end,{}",commonRes);
        return commonRes;
    }
*/

    public static void main(String[] args) throws Exception {
        RequestService requestService = new RequestService(null, null);
//        requestService.sendMessage("asdas", new String[]{"15888888888", "18114865763", "18838767234", "13812087104", "000", "11111111", "19110421245"});


        String[] phones = new String[]{"15888888888", "18114865763", "18838767234", "13812087104", "000", "11111111", "19110421245"};

        List<String> collect = Stream.of(phones).filter(item -> item.matches("^1[0-9]{10}$")).collect(Collectors.toList());
        log.error("{}", collect);
    }

    private void paramTranBuffForGet(Map<String, Object> param, StringBuilder url) throws Exception {
        url.append("?token=" + getApiTokenForReq());
        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            url.append("&").append(next.getKey()).append("=").append(next.getValue());
        }

        log.info("url: {}", url);
    }

    private String getApiTokenForReq() throws Exception {
        String apiToken = null;
        try {
            apiToken = getApiToken(false);
        } catch (Exception e) {
            log.error("获取自作超市接口异常");
            throw new Exception("获取自作超市接口异常");
        }
        return apiToken;
    }

}
