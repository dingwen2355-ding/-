package cn.wxgis.jc.system.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.constant.ServiceNameConstants;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.system.feign.factory.RemoteLogFallbackFactory;
import cn.wxgis.jc.system.po.SysLogininfor;
import cn.wxgis.jc.system.po.SysOperLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 日志服务
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.OPERLOG_SAVE)
    JsonResult<Boolean> saveLog(@RequestBody SysOperLog sysOperLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws Exception;

    /**
     * 保存访问记录
     *
     * @param sysLogininfor 访问实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.LOGININFOR_SAVE)
    JsonResult<Boolean> saveLogininfor(@RequestBody SysLogininfor sysLogininfor, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
