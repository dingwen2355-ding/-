package com.grandlynn.config.activiti;

import cn.hutool.core.util.StrUtil;
import com.grandlynn.common.system.vo.LoginUser;
import com.grandlynn.modules.activiti.util.MySecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
@Slf4j
public class ActivitiAop {

    /**
     * 用于activiti登录
     */
    @Autowired
    private MySecurityUtil mySecurityUtil;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(LoginActiviti)")
    private void pointcut() {
    }

    /**
     * 环绕通知 登录到activiti的security
     *
     * @return
     */
    @Around("pointcut()")
    public Object handleLogin(ProceedingJoinPoint pjp) throws Throwable {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Method method = ((MethodSignature) (pjp.getSignature())).getMethod();
        //获取注解信息
        LoginActiviti annotation = method.getAnnotation(LoginActiviti.class);
        //获取固定登陆人
        String loginName = annotation.loginName();
        String[] addNewGroup = annotation.addNewGroup();
        String[] addNewRole = annotation.addNewRole();
        List<String> list = new ArrayList<>();
        for (String s : addNewGroup) {
            list.add("GROUP_"+s);
        }
        for (String s : addNewRole) {
            list.add("ROLE_"+s);
        }
        boolean excludeOldRole = annotation.excludeOldRole();

        String username = sysUser.getUsername();
        if (loginName !=null&& StrUtil.isNotBlank(loginName)){
            username = loginName;
        }
        mySecurityUtil.logInAs(username,list,excludeOldRole);
        try {
            Object proceed = pjp.proceed();
            return proceed;
        }catch (Throwable e){
            log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", pjp.getSignature().getDeclaringTypeName(),
                    pjp.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL", e.getMessage(), e);
            throw e;
        }
    }
}
