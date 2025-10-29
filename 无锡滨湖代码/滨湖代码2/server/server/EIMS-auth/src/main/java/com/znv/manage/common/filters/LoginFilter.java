package com.znv.manage.common.filters;

import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.common.utils.ThreadLocalUtil;
import com.znv.manage.controller.VerifyAuthority;
import com.znv.manage.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 登录过滤器
 *
 * @author wr
 * @date 2018.3.7
 */
@Slf4j
@Component
public class LoginFilter implements Filter {

    @Value("${filter.onoff:true}")
    private Boolean filterOnoff;

    @Autowired
    private VerifyAuthority verifyAuthority;

    @Autowired
    RedisService redisService;

    private static final Set<String> ALLOW_PATHS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList("", "/login", "/swagger","/swagger-ui.html",
                    "/logout", "/debug", "/v1/Login", "/verifyAuthority/verifyToken",
                    "/verifyAuthority/getPrecinctIdsByUserId", "/getUserStreetId",
                    "/verifyAuthority/user/queryUserInfoByToken",
                    "/verifyAuthority/user/queryRoleIdByUserId",
                    "/verifyAuthority/user/querySameStreetUser",
                    "/verifyAuthority/user/queryBelongToAreaUser",
                    "/verifyAuthority/username", "/verifyAuthority/refreshTokenTime", "/verifyAuthority/checkToken", "/user/changePasswordByName",
                    "/UserData/getUserInfo", "/UserData/getAuthToken", "/UserData/refreshToken","/user/login","/user/logout","/loginByGWC")));

    private static final String regixStr = ".*/websocket.*";

    // 第三方登录路径匹配
    private static final String regixStr3rd = "/v1/.*";

    private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 过滤，如果例外路径可以通过，如果不是例外则验证是否已经登录，如果没有登录，则返回错误
     *
     * @param servletRequest  请求
     * @param servletResponse 回复
     * @param filterChain     过滤器链
     * @throws IOException      向前端发送错误失败
     * @throws ServletException 过滤失败
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        long st = System.currentTimeMillis();
        ThreadLocalUtil.set(st);
        ThreadContext.put("reqId", String.valueOf(st));

        String path = request.getRequestURI()
                .substring(request.getContextPath().length())
                .replaceAll("[/]+$", "");
//        logger.info("path:"+path);
//        LogUtil.info("RequestURL:"+request.getRequestURI());

//        boolean logined = (session != null && session.getAttribute("username") != null);
        // 内部测试，见DebugController
        boolean isDebug = "admin_debug"
                .equals(session.getAttribute("username"));
        boolean allowPath = ALLOW_PATHS.contains(path);
        boolean matches = Pattern.matches(regixStr, path);
        // 第三方平台登录校验
        boolean matches3rd = Pattern.matches(regixStr3rd, path);

        if (matches3rd) {// 第三方平台登录
//            logger.info("第三方登录");
            // 解决前端调用接口跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "5000");
            response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization,Token");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else if (allowPath || matches || isDebug) {
//            logger.info("allowPath || matches || isDebug");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
//            logger.info("需要鉴权----------");

            // 鉴权是否开启
            if (filterOnoff) {
                try {
                    String accessToken = request.getHeader("Cauthorization");
                    if (!StringUtils.isEmpty(accessToken)) {
                        if (Boolean.FALSE.equals(redisService.hasThirdToken(accessToken))) {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Cauthorization error");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        } else {
                            //延长过期时间
                            redisService.expireThirdToken(accessToken);
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        }
                    } else {
                        String token = request.getHeader("Authorization");
                        //                    logger.info("Authorization token:" + token);
                        if (StringUtils.isEmpty(token) || "12CBD9B708D887A41AFAB97DAC46AAC6".equals(token)) {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Please Login First.");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }
                        ResultBean<String> result = verifyAuthority.verifyToken(token);
                        logger.info("token auth result: {},{},{}", result.getResult(), result.getData(), result.getRemark());
                        // 0表示检验鉴权成功
                        if (0 == result.getResult()) {
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        } else {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Please Login First.");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } else {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Please Login First.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
        boolean matches = Pattern.matches(regixStr, "123/websocket/123");
        System.out.println(matches);
    }
}