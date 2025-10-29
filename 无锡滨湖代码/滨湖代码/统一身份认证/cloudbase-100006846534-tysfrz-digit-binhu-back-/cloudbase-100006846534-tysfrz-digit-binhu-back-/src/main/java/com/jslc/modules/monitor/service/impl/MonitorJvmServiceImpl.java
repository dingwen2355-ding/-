package com.jslc.modules.monitor.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.monitor.entity.MonitorJvm;
import com.jslc.modules.monitor.mapper.MonitorJvmMapper;
import com.jslc.modules.monitor.service.IMonitorJvmService;
import com.jslc.modules.monitor.vo.ApiVO;
import com.jslc.modules.monitor.vo.JvmVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * jvm监控 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2023-10-08
 */
@Service
public class MonitorJvmServiceImpl extends ServiceImpl<MonitorJvmMapper, MonitorJvm> implements IMonitorJvmService {
    @Value("${serverName:}")
    String serverName;

    @Override
    public void collectJvmInfo() {
        if (StringUtils.isNotBlank(serverName)) {
            Runtime runtime = Runtime.getRuntime();
            long jvmTotalMemoryByte = runtime.totalMemory();
            long freeMemoryByte = runtime.freeMemory();
            MonitorJvm monitorJvm = getMonitorJvmByServerName(serverName);
            if (monitorJvm == null) {
                monitorJvm = new MonitorJvm();
            }
            monitorJvm.setServerName(serverName);
            //jvm总内存
            monitorJvm.setTotalMemory(formatByte(jvmTotalMemoryByte));
            //空闲空间
            monitorJvm.setFreeMemory(formatByte(freeMemoryByte));
            //已使用内存
            monitorJvm.setUsedMemory(monitorJvm.getTotalMemory().subtract(monitorJvm.getFreeMemory()));

            monitorJvm.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(monitorJvm);
        }
    }

    @Override
    public List<JvmVO> getJvmInfo() {
        List<MonitorJvm> monitorJvms = this.list();
        List<JvmVO> jvmVOS = new ArrayList<>();
        for (MonitorJvm monitorJvm : monitorJvms) {
            JvmVO jvmVO = new JvmVO();
            BeanUtils.copyProperties(monitorJvm, jvmVO);
            jvmVOS.add(jvmVO);
        }
        return jvmVOS;
    }

    private MonitorJvm getMonitorJvmByServerName(String serverName) {
        return this.getOne(Wrappers.lambdaQuery(MonitorJvm.class).eq(MonitorJvm::getServerName, serverName));
    }

    /*@Override
    public List<ApiStatisticsVO> getStatisticsApi() {
        List<ApiStatisticsVO> apiStatisticsVOlist = Lists.newArrayList();
        List<String> apiNames = new ArrayList<>();
        List<ApiVO> hourApiVOS = this.baseMapper.getHourStatisticsApi(apiNames);
        Map<String, List<ApiVO>> hourApiVOMap = hourApiVOS.stream().collect(Collectors.groupingBy(ApiVO::getApi));
        List<ApiVO> dayApiVOS = this.baseMapper.getDayStatisticsApi(apiNames);
        Map<String, ApiVO> dayApiVOMap = dayApiVOS.stream().collect(Collectors.toMap(ApiVO::getApi, o -> o));
        List<ApiStatisticsVO> apiStatisticsVOList = new ArrayList<>();
        long startTime = System.currentTimeMillis() - (24 * 3600 * 1000);
        Map<String, Integer> timeMap = new LinkedHashMap<>();
        for (int i = 0; i < 24; i++) {
            Date date = new Date(startTime + i * 3600 * 1000);
            timeMap.put(DateUtil.format(date, "yyyy-MM-dd HH"), 0);
        }
        for (String apiName : apiNames) {
            ApiStatisticsVO apiStatisticsVO = new ApiStatisticsVO();
            apiStatisticsVO.setApiName(apiName);
            ApiVO dayApi = dayApiVOMap.get(apiName);
            if (dayApi == null) {
                dayApi = new ApiVO();
                dayApi.setApiAmount(0);
                dayApi.setApi(apiName);
            }
            dayApi.setTime(DateUtil.format(new Date(),"yyyy-MM-dd"));
            apiStatisticsVO.setDayCallApiAmount(dayApi);
            List<ApiVO> hourApis = hourApiVOMap.get(apiName);

            Map<String, ApiVO> hourApiMap = new HashMap<>();
            if (hourApis != null) {
                hourApiMap = hourApis.stream().collect(Collectors.toMap(ApiVO::getTime, o -> o));
            }

            List<ApiVO> hourApiList = new LinkedList();
            for (Map.Entry<String, Integer> entry : timeMap.entrySet()) {
                String time = entry.getKey();
                ApiVO hourApi = hourApiMap.get(time);
                if (hourApi == null) {
                    hourApi = new ApiVO();
                    hourApi.setApi(apiName);
                    hourApi.setApiAmount(0);
                    hourApi.setTime(time);
                }
                hourApiList.add(hourApi);
            }
            apiStatisticsVO.setHourCallApiAmount(hourApiList);
            apiStatisticsVOList.add(apiStatisticsVO);
        }
        return apiStatisticsVOList;
    }*/

    /**
     * 单位转换
     */
    private static BigDecimal formatByte(long byteNumber) {
        //换算单位
       /* double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);*/
        double gbNumber = byteNumber / (1024 * 1024 * 1024);
        return BigDecimal.valueOf(byteNumber).divide(BigDecimal.valueOf(1073741824), 3, BigDecimal.ROUND_HALF_UP);
        //return new DecimalFormat("#.##").format(gbNumber);
    }


    @Override
    public Map getStatisticsApi() {
        Map map = new HashMap();
        List<ApiVO> hourApiVOS = this.baseMapper.getHourStatisticsApi(null);
        Map<String, Integer> hourApiAmountMap = hourApiVOS.stream().collect(Collectors.toMap(ApiVO::getTime, o -> o.getApiAmount()));
        ApiVO dayApiVOS = this.baseMapper.getDayStatisticsApi(null);
        long startTime = System.currentTimeMillis() - (23 * 3600 * 1000);
        Map<String, Integer> timeMap = new LinkedHashMap<>();
        List<String> hours = new LinkedList<>();
        List<Integer> amounts = new LinkedList<>();
        for (int i = 0; i < 24; i++) {
            Date date = new Date(startTime + i * 3600 * 1000);
            String time = DateUtil.format(date, "yyyy-MM-dd HH");
            Integer amount = hourApiAmountMap.get(time);
            if (amount == null) {
                amount = 0;
            }
            hours.add(time + ":00");
            amounts.add(amount);
        }
        if (dayApiVOS == null) {
            map.put("dayApiAmount", 0);
        } else {
            map.put("dayApiAmount", dayApiVOS.getApiAmount());
        }

        map.put("hours", hours);
        map.put("hourApiAmount", amounts);
        long apiAbnormaAmount = this.getApiAbnormaAmount();
        map.put("apiAbnormaAmount", apiAbnormaAmount);
        return map;
    }

    @Override
    public long getApiAbnormaAmount() {
        return baseMapper.getApiAbnormaAmount();
    }
}
