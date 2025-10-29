package com.znv.manage.filter;

import com.znv.manage.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class RequestFilter implements Filter {


    private static final Set<String> ALLOW_PATHS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList("", "/swagger", "/debug", "/queryDevice/queryDanbinList",
                    "/sso/logout", "/sso/sendSmsCode", "/sso/checkToken", "/sso/login")));

    private final static String NO_TOKEN_MSG = "No token information.";

    private final static String TOKEN_ZNV = "12CBD9B708D887A41AFAB97DAC46AAC6";

    @Resource
    RedisService redisService;

    @Value("${login.isOnoff:true}")
    private boolean isOnoff;

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

        String path = request.getRequestURI()
                .substring(request.getContextPath().length())
                .replaceAll("[/]+$", "");
        boolean allowPath = ALLOW_PATHS.contains(path);

        // 排除swagger
        boolean isSwagger = "swagger".equals(String.valueOf(session.getAttribute("Authorization")));
        String refer = request.getHeader("Referer");
        if (!StringUtils.isEmpty(refer)) {
            isSwagger = isSwagger || refer.contains("swagger");
        }

        if (allowPath || isSwagger) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 鉴权开关
        if (!isOnoff) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String accessToken = request.getHeader("Cauthorization");
        if (StringUtils.isEmpty(accessToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Cauthorization error");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (Boolean.TRUE.equals(redisService.hasKey(accessToken))) {
            //延长过期时间
            redisService.expireThirdToken(accessToken);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }



        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, NO_TOKEN_MSG);
    }

    @Override
    public void destroy() {

    }
}