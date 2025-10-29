package cn.wxgis.jc.system.auth.service;

import cn.hutool.http.HttpUtil;
import cn.wxgis.jc.common.constant.CacheConstants;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.util.wechats.SHA1;
import cn.wxgis.jc.redis.service.RedisService;
import cn.wxgis.jc.synch.common.config.WechatConfig;
import cn.wxgis.jc.synch.common.constant.WechatUrl;
import cn.wxgis.jc.synch.unify.po.UnifyUser;
import cn.wxgis.jc.synch.wechat.vo.*;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import cn.wxgis.jc.system.web.service.SysUserService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class SysWechatService {

    private final RedisService redisService;
    private final WechatConfig wechatConfig;
    private final SysUserService userService;

    public LoginUser wechatLogin(String code) {
        if (StringUtils.isBlank(code))  throw new ServiceException("政务微信授权码错误");
        String token = redisService.getCacheObject(CacheConstants.WECHAT_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = getWechatAccessToken().getAccess_token();
        }
        WechatLoginInfoVo wechatUser = getWechatLoginInfo(token, code);
        log.info("===" + JSON.toJSONString(wechatUser));
        LoginUser user = this.setLoginUser(wechatUser);

        String url = wechatConfig.getAddr() + WechatUrl.getTicketUrl;
        String resultObj = HttpUtil.createGet(String.format(url, token)).timeout(CacheConstants.TIMEOUT).execute().body();
        WechatTicketVo wechatTicketVo = JSONObject.parseObject(resultObj, WechatTicketVo.class);
        if (StringUtils.isNull(wechatTicketVo)) {
            throw new ServiceException("调用政务微信服务查询Ticket信息方法失败");
        }
        user.setWechatTicket(wechatTicketVo);
        return user;
    }



    private LoginUser setLoginUser(WechatLoginInfoVo wechatUser) {
        if (StringUtils.isNull(wechatUser) || StringUtils.isBlank(wechatUser.getUserId())) return null;
        LoginUser loginUser = userService.getLoginUser(wechatUser.getUserId());
        if (StringUtils.isNull(loginUser)) {
            UnifyUser user = userService.findUnifyUserByUsername(wechatUser.getUserId());
            SysUserSaveRequest saveRequest = new SysUserSaveRequest();
            saveRequest.setUserName(user.getAccount());
            saveRequest.setNickName(user.getName());
            saveRequest.setPhonenumber(user.getMajorMobile());
            saveRequest.setSex(user.getGender());
            userService.save(saveRequest);
            loginUser = userService.getLoginUser(wechatUser.getUserId());
        }
        return loginUser;
    }

    private WechatAccessTokenVo getWechatAccessToken() {
        String url = wechatConfig.getAddr() + WechatUrl.getAccessTokenUrl;
        String resultObj = HttpUtil.createGet(String.format(url, wechatConfig.getCorpid(), wechatConfig.getCorpsecret())).timeout(CacheConstants.TIMEOUT).execute().body();
        WechatAccessTokenVo wechatAccessToken = JSONObject.parseObject(resultObj, WechatAccessTokenVo.class);
        if (wechatAccessToken.getErrcode() != 0) {
            throw new ServiceException("调用政务微信服务获取token方法失败");
        }
        redisService.setCacheObject(CacheConstants.WECHAT_TOKEN_KEY, wechatAccessToken.getAccess_token(), wechatAccessToken.getExpires_in(), TimeUnit.SECONDS);
        return wechatAccessToken;
    }

    private WechatLoginInfoVo getWechatLoginInfo(String accessToken, String code) {
        String url = wechatConfig.getAddr() + WechatUrl.getLoginInfoUrl;
        String resultObj = HttpUtil.createGet(String.format(url, accessToken, code)).timeout(CacheConstants.TIMEOUT).execute().body();
        WechatLoginInfoVo wechatUser = JSONObject.parseObject(resultObj, WechatLoginInfoVo.class);
        if (wechatUser.getErrcode() != 0) {
            throw new ServiceException("调用政务微信服务获取登陆用户方法失败");
        }
        return wechatUser;
    }


    private WechatUserInfoVo getWechatUserInfo(String accessToken, String userTicket) {
        String url = wechatConfig.getAddr() + WechatUrl.getUserInfoUrl;
        Map<String, Object> map = new HashMap<>();
        map.put("user_ticket", userTicket);
        JSONObject data = new JSONObject(map);
        String resultObj = HttpUtil.createPost(String.format(url, accessToken))
                .body(JSON.toJSONString(data))
                .timeout(CacheConstants.TIMEOUT).execute().body();
        WechatUserInfoVo wechatUser = JSONObject.parseObject(resultObj, WechatUserInfoVo.class);
        if (StringUtils.isNull(wechatUser)) {
            throw new ServiceException("调用政务微信服务用户详情方法失败");
        }
        return wechatUser;
    }

    public WechatSignatureVo signature() {
        try {
            WechatSignatureVo signature = new WechatSignatureVo();
            // 获取access_token
            String url = wechatConfig.getAddr() + WechatUrl.getAccessTokenUrl;
            String urlObj = HttpUtil.createGet(String.format(String.format(url, wechatConfig.getCorpid(), wechatConfig.getCorpsecret()))).execute().body();
            WechatAccessTokenVo wechatAccessToken = JSONObject.parseObject(urlObj, WechatAccessTokenVo.class);
            if (StringUtils.isNull(wechatAccessToken) || StringUtils.isBlank(wechatAccessToken.getAccess_token())) throw new ServiceException("政务微信调用方法获取信息失败");
            String ticketUrl = wechatConfig.getAddr() + WechatUrl.getTicketUrl;
            String ticketUrlObj = HttpUtil.createGet(String.format(String.format(ticketUrl, wechatAccessToken.getAccess_token()))).execute().body();
            WechatTicketVo wechatTicket = JSONObject.parseObject(ticketUrlObj, WechatTicketVo.class);
            if (StringUtils.isNull(wechatTicket) || StringUtils.isBlank(wechatTicket.getTicket())) throw new ServiceException("政务微信调用方法获取信息失败");
            String random = StringUtils.getRandomString(16);
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            signature.setNoncestr(random);
            signature.setJsapi_ticket(wechatTicket.getTicket());
            signature.setTimestamp(timestamp);
            String resultSign = SHA1.getSHA1("jsapi_ticket=" + signature.getJsapi_ticket(),
                    "noncestr=" + signature.getNoncestr(),
                    "timestamp=" + signature.getTimestamp(),
                    "url=" + wechatConfig.getUrl());
            System.out.println(JSONObject.toJSONString(resultSign));
            signature.setSignature(resultSign);
            return signature;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}


