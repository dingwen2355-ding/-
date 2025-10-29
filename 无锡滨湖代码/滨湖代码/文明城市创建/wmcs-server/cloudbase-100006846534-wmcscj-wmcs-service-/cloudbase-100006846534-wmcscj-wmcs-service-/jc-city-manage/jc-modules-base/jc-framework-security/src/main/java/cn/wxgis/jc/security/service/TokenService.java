package cn.wxgis.jc.security.service;

import cn.wxgis.jc.common.constant.CacheConstants;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.util.IpUtils;
import cn.wxgis.jc.common.util.JwtUtils;
import cn.wxgis.jc.common.util.ServletUtils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.redis.service.RedisService;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.model.LoginUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 */
@AllArgsConstructor
@Component
public class TokenService {

    private final RedisService redisService;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = 24*60;

    private final static long REFRESH_TIME = 120;

    private final static Long MILLIS_MINUTE_TEN = REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        if (StringUtils.isNull(loginUser)) return null;
        String token = IdUtils.fastUUID();
        String userId = loginUser.getUser().getId();
        String userName = loginUser.getUser().getUserName();
        loginUser.setToken(token);
        loginUser.setUserId(userId);
        loginUser.setUserName(userName);
        loginUser.setName(loginUser.getUser().getNickName());
        loginUser.setDeptId(StringUtils.isNotNull(loginUser.getDept()) ? loginUser.getDept().getId() : null);
        loginUser.setDeptName(StringUtils.isNotNull(loginUser.getDept()) ? loginUser.getDept().getName() : null);
        loginUser.setIpAddr(IpUtils.getIpAddr());
        loginUser.setLoginTime(System.currentTimeMillis());
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap,expireTime * MILLIS_MINUTE));
        rspMap.put("expires_in", expireTime);
        rspMap.put("ticket", loginUser.getWechatTicket());
        return rspMap;
    }


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser()
    {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token)
    {
        LoginUser user = null;
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        }
        catch (Exception e)
        {
        }
        return user;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}
