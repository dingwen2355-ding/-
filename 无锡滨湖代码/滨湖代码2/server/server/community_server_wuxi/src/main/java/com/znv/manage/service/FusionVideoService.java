package com.znv.manage.service;

import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.Result;

import java.util.List;
import java.util.Map;


/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */

public interface FusionVideoService {

    Result updateVideoListByEventId(String eventId);

    Result updateVideoListByEventId2();

    Result getVideoListByEventId(String eventId);

    void updateFusionMax(String eventId,String type,String deviceIds);

    List<Map<String, Object>> queryCameraInfoByType(String deviceKind, String name, String deviceId,String precinctId,String devtype,String eventId,Integer start,Integer pageLen);

    Map<String, Object> fusionMax(String eventId);

    List<Map<String,Object>> queryDeviceKindList();

    List<Map<String, Object>> queryFixedCameraList(String precinctName);

    Result updateEventDeviceIds(String eventId, String precinctId, String deviceIds);
}
