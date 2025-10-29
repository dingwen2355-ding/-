
package com.znv.manage.common.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @ClassName: RepeatRequestHandler
* @Description: 拦截器,重放攻击拦截
* @author fxq
* @date 2021/1/07
*
*/
@Component
public class RepeatRequestHandler implements HandlerInterceptor{

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(RepeatRequestHandler.class);

    /**
     * 黑名单
     */
    private static final Set<String> NOT_ALLOW_PATHS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList("","/insertDepartment",
                    "/role/insert","/roleModule/insert")));

    // 利用guava通过CacheBuilder构建一个缓存实例
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000) // 设置缓存的最大容量
            .expireAfterWrite(1, TimeUnit.MINUTES) // 设置缓存在写入一分钟后失效
            .concurrencyLevel(100) // 设置并发级别
            .recordStats() // 开启缓存统计
            .build();
    
    /**
     * 请求开始时间
     */
    ThreadLocal<Long> stThreadLocal = new ThreadLocal<Long>();

    /**
     * 进入controller之前
     * @param request  request
     * @param response response
     * @param handler handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getServletPath();
        String method = request.getMethod();
        long st=System.currentTimeMillis();
        stThreadLocal.set(st);
        //判断是否在拦截列表中(暂时改为拦截全部Post方法请求)
//        if (NOT_ALLOW_PATHS.contains(path)) {
        if("POST".equals(method)){
            //判断重放攻击
            String rand = request.getParameter("r");
            synchronized (this){
                if(StringUtils.isEmpty(rand) || cache.getIfPresent(rand) != null){
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "Please do not repeat the operation!");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return false;
                }else {
                    cache.put(rand,String.valueOf(st));
                    return true;
                }
            }
        } else {
            return true;
        }
    }


    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     *  结果渲染之前
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
        long st = stThreadLocal.get();
        long speedTime = System.currentTimeMillis() - st;
        logger.info("RequestId:{}, 本次请求耗时SpeedTime:{}ms ",st,speedTime);
    }
}
