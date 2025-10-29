package com.znv.manage.service.impl;

import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.EventInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.dao.FusionVideoDao;
import com.znv.manage.service.FusionVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author:
 * @Date:
 * @Description:
 */

@Service
@Slf4j
public class FusionVideoServiceImpl implements FusionVideoService {

    @Autowired
    private FusionVideoDao fusionVideoDao;

    @Override
    public Result updateVideoListByEventId(String eventId) {
        //获取事件经纬度
        EventInfo eventInfo = fusionVideoDao.getData(eventId);
        Result result = new Result();
        if (eventInfo != null) {
            if (eventInfo.getGpsx() != null && eventInfo.getGpsy() != null) {
                //获取所有设备数据
                List<DeviceInfo> deviceInfoList = fusionVideoDao.getList2(String.valueOf(eventInfo.getGpsx()),String.valueOf(eventInfo.getGpsy()));
                if (!CollectionUtils.isEmpty(deviceInfoList) || (deviceInfoList.size() != 0)) {
                    StringBuffer sb = new StringBuffer();
                    deviceInfoList.forEach(data -> {
                        sb.append(data.getDeviceId()).append(",");
                    });
                    eventInfo.setDeviceIds(sb.toString());
                    int data = fusionVideoDao.updateEventInfo(eventInfo);
                    if(data > 0){
                        result.setCode(ResultCodeEnum.NORMAL.getCode());
                        result.setMessage(ResultCodeEnum.NORMAL.getName());
                    }else{
                        result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
                        result.setMessage("更新数据失败");
                    }
                }
            } else {
                result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
                result.setMessage("该事件无经纬度数据");
            }
        } else {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage("无该事件数据");
        }
        return result;
    }

    @Override
    public Result updateVideoListByEventId2() {
        //获取事件经纬度
        List<EventInfo> eventInfoList = fusionVideoDao.getData2();
        Result result = new Result();
        if(!CollectionUtils.isEmpty(eventInfoList)){
            for(EventInfo eventInfo:eventInfoList){
                List<DeviceInfo> deviceInfoList = fusionVideoDao.getList2(String.valueOf(eventInfo.getGpsx()),String.valueOf(eventInfo.getGpsy()));
                        if (!CollectionUtils.isEmpty(deviceInfoList) || (deviceInfoList.size() != 0)) {
                            StringBuffer sb = new StringBuffer();
                            deviceInfoList.forEach(data -> {
                                sb.append(data.getDeviceId()).append(",");
                            });
                            eventInfo.setDeviceIds(sb.toString());
                        }
            }
            int data = fusionVideoDao.updateEventDeviceId(eventInfoList);
            if(data > 0){
                result.setCode(ResultCodeEnum.NORMAL.getCode());
                result.setMessage(ResultCodeEnum.NORMAL.getName());
            }else{
                result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
                result.setMessage("更新数据失败");
            }
        }
        return result;
    }


    @Override
    public Result getVideoListByEventId(String eventId) {
        EventInfo eventInfo = fusionVideoDao.getData(eventId);
        Result result = new Result();
        if (eventInfo != null) {
            if (!StringUtils.isEmpty(eventInfo.getDeviceIds())) {
                List<String> list = Arrays.asList(eventInfo.getDeviceIds().split(","));
                List<DeviceInfo> deviceInfoList = new ArrayList<>();
                for (String deviceId : list) {
                    DeviceInfo deviceInfo = fusionVideoDao.getDeviceById(deviceId,eventInfo.getAreaCode());
                    deviceInfoList.add(deviceInfo);
                }
                result.setData(deviceInfoList);
                result.setCode(ResultCodeEnum.NORMAL.getCode());
            }else {
                result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
                result.setMessage("该事件无设备数据");
            }
        } else {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage("无该事件数据");
        }
        return result;
    }

    @Override
    public void updateFusionMax(String eventId, String type, String deviceIds) {
        fusionVideoDao.updateFusionMax(eventId,type,deviceIds);
    }

    @Override
    public List<Map<String, Object>> queryCameraInfoByType(String deviceKind, String name, String deviceId,String precinctId,String devtype,String eventId,Integer start,Integer pageLen) {
        String gpsx = "";
        String gpsy = "";
        if(!StringUtils.isEmpty(eventId)){
            EventInfo eventInfo = fusionVideoDao.getData(eventId);
            gpsx = String.valueOf(eventInfo.getGpsx());
            gpsy = String.valueOf(eventInfo.getGpsy());
        }
        return fusionVideoDao.queryCameraInfoByType(deviceKind,name,deviceId,precinctId,devtype,gpsx,gpsy,start,pageLen);
    }

    @Override
    public Map<String, Object> fusionMax(String eventId) {
        List<Map<String, Object>> list = fusionVideoDao.fusionMax(eventId);
        if(list.size()>0){
            String type = String.valueOf(list.get(0).get("type"));
            Map<String, Object> map = new HashMap<>();
            map.put("type",type);
            map.put("deviceInfos",list);
            return map;
        }else{
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> queryDeviceKindList() {
        return fusionVideoDao.queryDeviceKindList();
    }

    @Override
    public List<Map<String, Object>> queryFixedCameraList(String precinctName) {
        return fusionVideoDao.queryFixedCameraList(precinctName);
    }

    @Override
    public Result updateEventDeviceIds(String eventId, String precinctId, String deviceIds) {
        Result result = new Result();
        try {

            fusionVideoDao.updateEventDeviceIds(eventId,deviceIds);

        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }
}
