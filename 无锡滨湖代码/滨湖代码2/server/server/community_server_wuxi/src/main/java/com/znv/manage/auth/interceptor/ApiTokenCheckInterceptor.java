package com.znv.manage.auth.interceptor;

import com.znv.manage.auth.annotation.ApiTokenCheck;
import com.znv.manage.auth.config.ApiTokenConfig;
import com.znv.manage.auth.service.ApiTokenService;
import com.znv.manage.common.encrypt.AESNormalUtil;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yufei on 2020/6/30.
 */

@Component
@Slf4j
public class ApiTokenCheckInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ApiTokenConfig apiTokenConfig;

    @Autowired
    ApiTokenService apiTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断总体开关，关闭则跳过鉴权；
        if (!apiTokenConfig.isEnable()) {
            return super.preHandle(request, response, handler);
        }
        // 判断是否有session，有则跳过鉴权（用于本地swagger接口调试）；
        if (!StringUtils.isEmpty(request.getSession().getAttribute(apiTokenConfig.getDebugSessionKey()))) {
            return super.preHandle(request, response, handler);
        }

        // 配置该注解，说明需要进行用户拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ApiTokenCheck classAnnotation = handlerMethod.getBeanType().getAnnotation(ApiTokenCheck.class);
        ApiTokenCheck methodAnnotation = handlerMethod.getMethodAnnotation(ApiTokenCheck.class);
        if (classAnnotation == null && methodAnnotation == null) {
            return super.preHandle(request, response, handler);
        }
        boolean isTokenEncrypt = false;
        if (classAnnotation != null) {
            isTokenEncrypt = classAnnotation.isTokenEncrypt();
        }
        if (methodAnnotation != null) {
            isTokenEncrypt = methodAnnotation.isTokenEncrypt();
        }
        String token = request.getHeader(apiTokenConfig.getHeader());
        // 校验token
        if (!validateToken(request,token,isTokenEncrypt)) {
            throw new BusinessException(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getName());
        }
        return super.preHandle(request, response, handler);
    }

    private boolean validateToken(HttpServletRequest request, String token, boolean isTokenEncrypt) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        // 如果token加密过，需要先解密
        String tempToken = token;
        if (isTokenEncrypt) {
            try {
                tempToken = AESNormalUtil.aesDecrypt(tempToken, apiTokenConfig.getAesKey());
            } catch (Exception e) {
                log.error("解密token异常：{}",e);
                return false;
            }
        }
//        if (request.getSession().getAttribute(tempToken) == null) {
//            return false;
//        }
        if (!apiTokenService.checkToken(tempToken,request.getSession())) {
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
