package com.znv.manage.service.impl;

import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.bean.user.User;
import com.znv.manage.common.bean.Precinct;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.RIOUtil;
import com.znv.manage.dao.UserServiceMapper;
import com.znv.manage.precinct.dao.PrecinctServiceMapper;
import com.znv.manage.redis.RedisService;
import com.znv.manage.service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class UserDataServiceImpl implements UserDataService {

    @Value("${userData.getUserList.url}")
    private String getUserListUrl;

    @Value("${userData.getPrecinct.url}")
    private String getPrecinctUrl;

    @Value("${userData.getOneUser.url}")
    private String getOneUserUrl;

    @Value("${userData.getToken.url}")
    private String getToken;

    @Value("${userData.sendMessage.url}")
    private String sendMessageUrl;

    @Value("${userData.appId}")
    private String appId;

    @Value("${userData.appSecret}")
    private String appSecret;

    @Value("${userData.url}")
    private String userDataUrl;

    @Value("${userData.redirect.url}")
    private String redirectUrl;

    @Value("${userData.server.url}")
    private String serverUrl;

    @Value("${userData.onoff}")
    private boolean userDataOnoff;

    @Value("${userData.paasid:wxbhyxgl}")
    String paasid;

    @Value("${userData.paasToken:0e361338083087514abb3e8b9144590d}")
    String paasToken;

    @Autowired
    private UserServiceMapper userServiceMapper;

    @Autowired
    private PrecinctServiceMapper precinctServiceMapper;

    @Autowired
    RedisService redisService;


    @Override
    public Result getUserList() {
        Result result = new Result();
        try {
            //获取token
            String token = getToken();
            if (StringUtils.isEmpty(token)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取token失败！");
                return result;
            }

            //获取Url
            String url = getUrl(appId, token, getUserListUrl, userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            JSONArray userArray = null;
            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONArray("data"))) {
                userArray = response.getJSONArray("data");
            }

            if (CollectionUtils.isEmpty(userArray)) {
                result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                result.setMessage("未获取到用户数据！");
                return result;
            }

            List<User> userList = new ArrayList<>();
            for (int i = 0; i < userArray.size(); i++) {
                JSONObject item = userArray.getJSONObject(i);
                if (CollectionUtils.isEmpty(item)) {
                    log.error("The record ({}) data is empty! skip it!", item);
                    continue;
                }
                User user = getUser(item);
                userList.add(user);
                if (userDataOnoff && userServiceMapper.isExistByUserName(user.getUserName()) == 0) {
                    log.info("insert user,name:{}", user.getUserName());
                    user.setRoleId("1");
                    user.setUserLevel("1");
                    user.setUserState("0");
                    user.setUserType("0");
                    userServiceMapper.insertUser(user);
                }
            }
            result.setData(userList);
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("message"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    @Override
    public String getToken() {
        String token = "";
        try {
            //校验参数
            if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret) || StringUtils.isEmpty(getToken) || StringUtils.isEmpty(userDataUrl)) {
                return "请检查系统配置文件项是否正确";
            }
            // 封装url
            String urlFormat = "%s?appId=%s&appSecret=%s";
            String url = String.format(urlFormat, getToken, appId, appSecret);
            String tokenUrl = url.replace("{url}", userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(tokenUrl, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", tokenUrl, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !StringUtils.isEmpty(response.getString("data"))) {
                token = response.getString("data");
            }
        } catch (Exception e) {
            log.error("Get token url occur an exception! ", e);
        }
        return token;
    }

    @Override
    public Result getPrecinctList() {
        Result result = new Result();
        try {
            //获取token
            String token = getToken();

            if (StringUtils.isEmpty(token)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取token失败！");
                return result;
            }

            //获取Url
            String urlFormat = "%s?appId=%s&token=%s&noFetchChild=0";
            String dataUrl = String.format(urlFormat, getPrecinctUrl, appId, token);
            String url = dataUrl.replace("{url}", userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));

            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            JSONArray precinctArray = null;
            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONArray("data"))) {
                precinctArray = response.getJSONArray("data");
            }

            if (CollectionUtils.isEmpty(precinctArray)) {
                result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                result.setMessage("未获取到组织数据！");
                return result;
            }

            List<Precinct> precinctList = new ArrayList<>();
            for (int i = 0; i < precinctArray.size(); i++) {
                JSONObject item = precinctArray.getJSONObject(i);
                if (CollectionUtils.isEmpty(item)) {
                    log.error("The record ({}) data is empty! skip it!", item);
                    continue;
                }
                Precinct precinct = getPrecinct(item);
                precinctList.add(precinct);
                if (userDataOnoff) {
                    precinctServiceMapper.addPrecinct(precinct.getPrecinctId(), precinct.getPrecinctName(), null, precinct.getUpPrecinctId(), precinct.getPrecinctKind(), null, null, null, null, null, null, null, null, null);
                }
            }
            result.setData(precinctList);
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("message"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    private String getUrl(String appId, String token, String url, String userDataUrl) {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(url) || StringUtils.isEmpty(userDataUrl)) {
            return "请检查系统配置文件项是否正确";
        }
        String urlFormat = "%s?appId=%s&token=%s";
        String dataUrl = String.format(urlFormat, url, appId, token);
        return dataUrl.replace("{url}", userDataUrl);
    }

    private User getUser(JSONObject userJson) {
        User user = new User();
        user.setEmail(userJson.getString("email"));
        user.setUserName(userJson.getString("account"));
        user.setPassword("832AA27302AD26A46525E43047254047");
        user.setDepartmentId(userJson.getString("majorDept"));
        user.setPhone(userJson.getString("telephone"));
        user.setMobilePhone(userJson.getString("majorMobile"));
        user.setGender(userJson.getString("gender"));
        user.setTrueName(userJson.getString("name"));
        user.setUserUrl(userJson.getString("avatar"));
        user.setEmployeeId(userJson.getString("majorPosition"));
        user.setPrecinctTreeId(userJson.getString("majorDept"));
        user.setStreetId(userJson.getString("majorDept"));
        return user;
    }

    private Precinct getPrecinct(JSONObject precinctJson) {
        Precinct precinct = new Precinct();
        precinct.setPrecinctId(precinctJson.getString("id"));
        precinct.setUpPrecinctId(precinctJson.getString("parentId"));
        precinct.setPrecinctName(precinctJson.getString("name"));
        if (1 == precinctJson.getInteger("parentId")) {
            precinct.setPrecinctKind(100);
        } else if (373 == precinctJson.getIntValue("parentId")) {
            precinct.setPrecinctKind(110);
        } else {
            precinct.setPrecinctKind(120);
        }

        return precinct;
    }

    @Override
    public Result getOneUser(String account) {
        Result result = new Result();
        try {
            //获取token
            String token = getToken();

            if (StringUtils.isEmpty(token)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取token失败！");
                return result;
            }

            //获取Url
            String urlFormat = "%s?appId=%s&token=%s&account=%s";
            String dataUrl = String.format(urlFormat, getOneUserUrl, appId, token, account);
            String url = dataUrl.replace("{url}", userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));

            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONObject("data"))) {
                result.setData(response.getJSONObject("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("message"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    @Override
    public Result sendMessage(String mobile, String msg) {
        Result result = new Result();
        try {
            //获取token
            String token = getToken();

            if (StringUtils.isEmpty(token)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取token失败！");
                return result;
            }
            //获取Url
            String urlFormat = "%s?appId=%s&token=%s&mobile=%s&msg=%s";
            String dataUrl = String.format(urlFormat, sendMessageUrl, appId, token, mobile, msg);
            String url = dataUrl.replace("{url}", userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !StringUtils.isEmpty(response.getString("data"))) {
                result.setData(response.getString("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("message"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    @Override
    public Result oAuthToken(String code, String requestAppId, String requestAppSecret, String requestRedirectUrl, HttpServletRequest request) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(code)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取授权码失败！");
                return result;
            }
            String urlFormat = "%s/oauth/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&scope=all";
            String url = String.format(urlFormat, serverUrl, requestAppId, requestAppSecret, code, requestRedirectUrl);


            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.POST, appId, paasid, paasToken, null, null, null));

            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONObject("data"))) {
                redisService.addThirdToken(response.getJSONObject("data").getString("accessToken"), response.getJSONObject("data").getJSONObject("userInfo").getString("name"));
                result.setData(response.getJSONObject("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("msg"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    @Override
    public Result refreshToken(String refreshToken) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(refreshToken)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取刷新token的值失败！");
                return result;
            }
            String urlFormat = "%s/oauth/token?grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s";
            String url = String.format(urlFormat, serverUrl, refreshToken, appId, appSecret);

            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.POST, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONObject("data"))) {
                result.setData(response.getJSONObject("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("msg"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    @Override
    public Result getUserInfo(String token) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(token)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取刷新token的值失败！");
                return result;
            }
            String urlFormat = "%s/user/token?token=%s";
            String url = String.format(urlFormat, serverUrl, token);

            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response)
                    && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONObject("data"))) {
                result.setData(response.getJSONObject("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("msg"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }
}
