package cn.wxgis.jc.system.auth.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.wxgis.jc.common.constant.CacheConstants;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.synch.common.config.UnifyConfig;
import cn.wxgis.jc.synch.common.constant.OauthUrlConstant;
import cn.wxgis.jc.synch.result.ResultVo;
import cn.wxgis.jc.synch.unify.vo.OauthTokenVo;
import cn.wxgis.jc.synch.unify.vo.UnifyUserInfoVo;
import cn.wxgis.jc.system.auth.enums.AuthorizationTypeEnum;
import cn.wxgis.jc.system.feign.RemoteUserService;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class SysOAuthLoginService {

    private final UnifyConfig unifyConfig;
    private final RemoteUserService remoteUserService;

    public String authorization(Integer type) {
        if (AuthorizationTypeEnum.SCREEN.getCode().equals(type)) {
            String codeUrl = String.format(unifyConfig.getAuthorizationUrl() + OauthUrlConstant.getOAuthCode, unifyConfig.getScreenAppId(), unifyConfig.getScreenAppSecret(), unifyConfig.getScreenRedirectUri(), unifyConfig.getResponseType(), unifyConfig.getScope());
            log.info("请求地址：" + codeUrl);
            return codeUrl;
        }
        String codeUrl = String.format(unifyConfig.getAuthorizationUrl() + OauthUrlConstant.getOAuthCode, unifyConfig.getAppId(), unifyConfig.getAppSecret(), unifyConfig.getRedirectUri(), unifyConfig.getResponseType(), unifyConfig.getScope());
        return codeUrl;
    }
    public String logout() {
        String codeUrl = String.format(unifyConfig.getAuthorizationUrl() + OauthUrlConstant.logout, unifyConfig.getRedirectUri());
        return codeUrl;
    }

    public LoginUser unclassifiedLogin(String code, Integer type) {
        if (StringUtils.isBlank(code))  throw new ServiceException("授权码验证失败");
        String oAuthToken = this.getOAuthToken(code, type);
        /*** 获取第三方鉴权信息 end ***/
        if (StringUtils.isBlank(oAuthToken)) return null;
        OauthTokenVo oauthTokenVo = JSONObject.parseObject(oAuthToken, OauthTokenVo.class);
        String userInfo = oauthTokenVo.getUserInfo();
        if (StringUtils.isBlank(userInfo)) return null;
        UnifyUserInfoVo unifyUserInfo = JSONObject.parseObject(userInfo, UnifyUserInfoVo.class);
        // 根据返回的用户数据查询本系统内的用户
        JsonResult loginUserJsonResult = this.setLoginUser(unifyUserInfo);
        return JSONObject.parseObject(JSON.toJSONString(loginUserJsonResult.getData()), LoginUser.class);
    }

    private JsonResult setLoginUser(UnifyUserInfoVo unifyUserInfo) {
        if (StringUtils.isNull(unifyUserInfo) || StringUtils.isBlank(unifyUserInfo.getAccount())) return null;
        JsonResult<LoginUser> userResult = remoteUserService.findByUserName(unifyUserInfo.getAccount(), SecurityConstants.INNER);
        if (StringUtils.isNotNull(userResult) && StringUtils.isNotBlank(userResult.getCode()) && StringUtils.isNull(userResult.getData())) {
            SysUserSaveRequest user = new SysUserSaveRequest();
            user.setUserName(unifyUserInfo.getAccount());
            user.setNickName(unifyUserInfo.getName());
            user.setPhonenumber(unifyUserInfo.getPhone());
            user.setEmail(unifyUserInfo.getEmail());
            remoteUserService.saveUser(user, SecurityConstants.INNER);
            return remoteUserService.findByUserName(unifyUserInfo.getAccount(), SecurityConstants.INNER);
        }
        return userResult;
    }


    private String getOAuthToken(String code, Integer type) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String random = UUID.randomUUID().toString();
        String signature = SecureUtil.sha256(timestamp + unifyConfig.getPaasToken() + random + timestamp);
        Map<String, String> header = new HashMap<>();
        header.put("x-rio-paasid", unifyConfig.getPaasid());
        header.put("x-rio-timestamp", timestamp);
        header.put("x-rio-nonce", random);
        header.put("x-rio-signature", signature);
        // 如果是大屏则获取的appid为大屏的appid
        log.info("===是否大屏请求" + type);
        if (AuthorizationTypeEnum.SCREEN.getCode().equals(type)) {
            String loginTokenUrl = String.format(unifyConfig.getOauthApiUrl() + OauthUrlConstant.getOAuthLoginToken, unifyConfig.getGrantType(), unifyConfig.getScreenAppId(), unifyConfig.getScreenAppSecret(), code, unifyConfig.getScreenRedirectUri(), unifyConfig.getScope());
            String resultObj = HttpUtil.createPost(loginTokenUrl).addHeaders(header)
                    .timeout(CacheConstants.TIMEOUT).execute().body();
            log.info("===================" + resultObj);
            ResultVo resultVo = JSONObject.parseObject(resultObj, ResultVo.class);
            if (StringUtils.isNull(resultVo) || StringUtils.isBlank(resultVo.getData())) throw new ServiceException("获取第三方授权失败");
            return resultVo.getData();
        }
        String loginTokenUrl = String.format(unifyConfig.getOauthApiUrl() + OauthUrlConstant.getOAuthLoginToken, unifyConfig.getGrantType(), unifyConfig.getAppId(), unifyConfig.getAppSecret(), code, unifyConfig.getRedirectUri(), unifyConfig.getScope());
        String resultObj = HttpUtil.createPost(loginTokenUrl).addHeaders(header)
                .timeout(CacheConstants.TIMEOUT).execute().body();
        log.info("===================" + resultObj);
        ResultVo resultVo = JSONObject.parseObject(resultObj, ResultVo.class);
        if (StringUtils.isNull(resultVo) || StringUtils.isBlank(resultVo.getData())) throw new ServiceException("获取第三方授权失败");
        return resultVo.getData();
    }

}
