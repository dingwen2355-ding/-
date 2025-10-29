package com.ruoyi.aop;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 *  打印请求参数和返回结果
 */
@Aspect
@Configuration
public class LoggerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 定义切点Pointcut
    @Pointcut("execution(* com.ruoyi.*.controller..*.*(..))")
    public void executeService() {
    }

    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JSONObject.toJSONString(map);
            } else if ("GET".equals(method)) {
                if (StringUtils.isNotBlank(queryString)){
                    params = URLDecoder.decode(queryString, "UTF-8");
                }
            }
        }
        logger.info("==============================start==============================================");
        logger.info("请求开始===uri:" + uri);
        logger.info("请求开始===地址:" + url);
        logger.info("请求开始===类型:" + method);
        logger.info("请求开始===参数:" + params);
        logger.info("===============================end===============================================");

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        logger.info("请求结束===返回值:" + JSONObject.toJSONString(result));
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val;
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }

}