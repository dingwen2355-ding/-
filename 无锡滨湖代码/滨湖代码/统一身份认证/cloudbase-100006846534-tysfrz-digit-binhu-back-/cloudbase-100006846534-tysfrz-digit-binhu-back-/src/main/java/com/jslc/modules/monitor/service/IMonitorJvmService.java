package com.jslc.modules.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.monitor.entity.MonitorJvm;
import com.jslc.modules.monitor.vo.ApiStatisticsVO;
import com.jslc.modules.monitor.vo.ApiVO;
import com.jslc.modules.monitor.vo.JvmVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * jvm监控 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2023-10-08
 */
public interface IMonitorJvmService extends IService<MonitorJvm> {
    void collectJvmInfo();

    List<JvmVO> getJvmInfo();

//    List<ApiStatisticsVO> getStatisticsApi();
    Map getStatisticsApi();
    long getApiAbnormaAmount();
}
