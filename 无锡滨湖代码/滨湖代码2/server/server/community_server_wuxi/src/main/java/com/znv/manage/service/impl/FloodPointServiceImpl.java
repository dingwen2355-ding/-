package com.znv.manage.service.impl;

import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.dao.FloodPointDao;
import com.znv.manage.service.FloodPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 防汛防台点位数据
 *
 * @author ：0049003788
 * @date ：2023/9/11 9:25
 */
@Service
@Slf4j
public class FloodPointServiceImpl implements FloodPointService {
    @Resource
    FloodPointDao floodPointDao;

    @Override
    public List<Map<String, Object>> queryPoint(String floodType) {
        return floodPointDao.queryPoint(floodType);
    }

    @Override
    public List<Map<String, Object>> queryNearDevice(String gpsx, String gpsy, Float distance) {
        double lon;
        double lat;
        try {
            lon = Double.parseDouble(gpsx);
            lat = Double.parseDouble(gpsy);
        } catch (NumberFormatException e) {
            throw new BusinessException("数据类型错误");
        }
        return floodPointDao.queryDeviceByGpsAndDistance(lon, lat, distance);
    }
}
