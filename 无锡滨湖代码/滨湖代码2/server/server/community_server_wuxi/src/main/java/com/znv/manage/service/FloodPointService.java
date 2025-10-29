package com.znv.manage.service;

import java.util.List;
import java.util.Map;

/**
 * @author 0049003788
 */
public interface FloodPointService {
    /**
     * 点位数据查询撒点
     *
     * @param floodType 类型
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryPoint(String floodType);

    /**
     * 点位附近摄像头查询
     *
     * @param gpsx     经度
     * @param gpsy     纬度
     * @param distance 距离(km)
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryNearDevice(String gpsx, String gpsy, Float distance);
}
