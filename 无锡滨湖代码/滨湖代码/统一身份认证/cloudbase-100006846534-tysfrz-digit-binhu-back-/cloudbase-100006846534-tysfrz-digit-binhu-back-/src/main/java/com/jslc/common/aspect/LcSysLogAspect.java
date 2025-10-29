package com.jslc.common.aspect;

import com.jslc.common.utils.HsmUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.WebUtil;
import com.jslc.common.annotation.LcSysLog;
import com.jslc.modules.system.entity.LcSysLogEntity;
import com.jslc.modules.system.service.LcSysLogService;
import com.jslc.common.utils.IPUtils;
import org.springblade.core.secure.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author zhengyongbiao
 * @title: LcSysLogAspect
 * @description: 系统日志，切面处理类
 * @date 2023/2/14 8:50
 */
@Aspect
@Component
@Slf4j
public class LcSysLogAspect {
	@Autowired
	private LcSysLogService sysLogService;
	@Autowired
	private HsmUtil hsmUtil;
	@Autowired
	private LcSysLogService lcSysLogService;

	@Pointcut("@annotation(com.jslc.common.annotation.LcSysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();

			LcSysLogEntity sysLog = new LcSysLogEntity();
			LcSysLog syslog = method.getAnnotation(LcSysLog.class);
			if (syslog != null) {
				//注解上的描述
				sysLog.setOperation(syslog.value());
			}
			//请求的方法名
			/*String className = joinPoint.getTarget().getClass().getName();
			String methodName = signature.getName();
			sysLog.setMethod(className + "." + methodName + "()");*/
			//请求的参数
			Object[] args = joinPoint.getArgs();
			String params = JsonUtil.toJson(args);
			sysLog.setParams(params);
			//获取request
			HttpServletRequest request = WebUtil.getRequest();
			//设置IP地址
			sysLog.setIp(IPUtils.getIpAddr(request));
			//租户
			sysLog.setTenantId(AuthUtil.getTenantId());
			//用户名
			sysLog.setUsername(AuthUtil.getUserName());
			sysLog.setCreateDate(new Date());
			sysLog.setOperSm(sysLogService.encryptOper(sysLog));
			//保存系统日志
			sysLogService.save(sysLog);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
