package cn.wxgis.jc.system.auth.service;

import cn.wxgis.jc.common.constant.CacheConstants;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.domain.res.LoginBodyRes;
import cn.wxgis.jc.common.enums.basedata.SystemEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.enums.system.UserStatusEnum;
import cn.wxgis.jc.common.exception.BaseException;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.Base64Utils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.redis.service.RedisService;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.feign.RemoteUserService;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.web.service.SysUserService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class SysLoginService {

    private final RedisService redisService;
    private final SysRecordLogService recordLogService;
    private final SysUserService userService;

    public LoginUser getLoginUserByAccount(LoginBodyRes loginBody) {
        String username = loginBody.getUsername();
        //获取密码输入错误次数
        Integer pwdErrorCount = redisService.getCacheObject(getCacheKey(username));
        if (StringUtils.isNull(pwdErrorCount)) pwdErrorCount = 0;
        if (pwdErrorCount >= CacheConstants.PASSWORD_MAX_RETRY_COUNT) {
            String errMsg = String.format("密码输入错误%s次，帐户锁定%s分钟", CacheConstants.PASSWORD_MAX_RETRY_COUNT, CacheConstants.PASSWORD_LOCK_TIME);
            recordLogService.recordLogininfor(username, GlobalStatusEnum.PASSWORD_INPUT_ERROR_MAX_EXCEPTION.getCode(), errMsg);
            throw new ServiceException(errMsg);
        }

        LoginUser loginUser = userService.getLoginUser(username);
        if (StringUtils.isNull(loginUser) || StringUtils.isNull(loginUser.getUser())) throw new ServiceException("该账号不存在！");
        if (UserStatusEnum.DELETED.getCode().equals(loginUser.getUser().getDelFlag())) {
            recordLogService.recordLogininfor(username, GlobalStatusEnum.DATA_DELETE_EXCEPTION.getCode(), "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }

        String password = new String(Base64Utils.decode(loginBody.getPassword()));
        if (!matches(loginUser.getUser(), password)) {
            pwdErrorCount = pwdErrorCount + 1;
            redisService.setCacheObject(getCacheKey(username), pwdErrorCount, CacheConstants.PASSWORD_MAX_RETRY_COUNT, TimeUnit.MINUTES);
            recordLogService.recordLogininfor(username, GlobalStatusEnum.PASSWORD_MISTAKE_EXCEPTION.getCode(),  GlobalStatusEnum.PASSWORD_MISTAKE_EXCEPTION.getMsg());
            throw new ServiceException(GlobalStatusEnum.PASSWORD_MISTAKE_EXCEPTION.getMsg());
        }
        clearLoginRecordCache(username);
        recordLogService.recordLogininfor(username, GlobalStatusEnum.STATUS_SUCCESS.getCode(),  GlobalStatusEnum.STATUS_SUCCESS.getMsg());
        return loginUser;
    }


    private String getCacheKey(String username)
    {
        return CacheConstants.USERINFO_INPUT_PWD_ERROR + username;
    }

    public boolean matches(SysUser user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisService.hasKey(getCacheKey(loginName))) {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }

    public void countTotalNumOfLogins(SystemEnum.LoginEnum loginEnum) {
        if (SystemEnum.LoginEnum.SYSTEM_LOGIN.equals(loginEnum)) {
            Integer cacheObject = redisService.getCacheObject(CacheConstants.USER_LOGIN_COUNT);
            cacheObject = null == cacheObject ? 0 : cacheObject;
            redisService.setCacheObject(CacheConstants.USER_LOGIN_COUNT, (cacheObject + 1));
        } else if (SystemEnum.LoginEnum.LOGOUT.equals(loginEnum)) {
            Integer cacheObject = redisService.getCacheObject(CacheConstants.USER_LOGIN_COUNT);
            redisService.setCacheObject(CacheConstants.USER_LOGIN_COUNT, (cacheObject - 1));
        } else {
            throw new BaseException(GlobalStatusEnum.STATUS_ERROR);
        }
    }

}
