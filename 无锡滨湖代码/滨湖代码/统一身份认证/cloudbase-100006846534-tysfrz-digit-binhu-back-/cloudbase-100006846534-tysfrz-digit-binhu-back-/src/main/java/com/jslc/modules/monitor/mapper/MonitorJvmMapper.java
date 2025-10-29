package com.jslc.modules.monitor.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jslc.modules.monitor.entity.MonitorJvm;
import com.jslc.modules.monitor.vo.ApiVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * jvm监控 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2023-10-08
 */
public interface MonitorJvmMapper extends BaseMapper<MonitorJvm> {
    List<ApiVO> getHourStatisticsApi(@Param("apiNames") List<String> apiNames);

    ApiVO getDayStatisticsApi(@Param("apiNames") List<String> apiNames);

    long getApiAbnormaAmount();

}
