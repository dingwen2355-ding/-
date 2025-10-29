package cn.wxgis.jc.log.service;

import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.system.feign.RemoteLogService;
import cn.wxgis.jc.system.po.SysOperLog;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 */
@AllArgsConstructor
@Service
public class AsyncLogService
{
    private final RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog) throws Exception
    {
        remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
    }
}
