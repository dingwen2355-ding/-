package com.znv.manage.service.impl;

import com.znv.manage.common.bean.EventInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.GpsUtils;
import com.znv.manage.common.utils.SnowflakeGenerator;
import com.znv.manage.dao.EventManageMapper;
import com.znv.manage.service.EventManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class EventManageServiceImpl implements EventManageService {

    @Autowired
    private EventManageMapper eventManageMapper;

    @Autowired
    private FusionVideoServiceImpl fusionVideoService;

    @Autowired
    private PublicOpinionServiceImpl publicOpinionService;

    @Autowired
    SnowflakeGenerator snowflakeGenerator;

    private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public Result addEvent(EventInfo eventInfo) {
        Result result = new Result();
        try {
            if(!StringUtils.isEmpty(eventInfo.checkInfo())){
                result.setMessage(eventInfo.checkInfo());
                result.setCode(500);
                return result;
            }
            //生成坐标
            Map<String,String> gps = GpsUtils.getGps("襄阳",eventInfo.getAddress());
            if(!StringUtils.isEmpty(gps.get("lon")) && !StringUtils.isEmpty(gps.get("lat"))){
                eventInfo.setGpsx(Double.valueOf(gps.get("lon")));
                eventInfo.setGpsy(Double.valueOf(gps.get("lat")));
            }
            eventInfo.setEventId(String.valueOf(snowflakeGenerator.generatorId()));
            eventInfo.setPlanId(eventManageMapper.queryPlanIdsByEventType(eventInfo.getEventType()));
            int count = eventManageMapper.insertEventInfo(eventInfo);
            if (count > 0){
                threadPool.execute(()->{
                    fusionVideoService.updateVideoListByEventId(eventInfo.getEventId());
                        } );
//                threadPool.execute(()->{
//                    publicOpinionService.savePublicOpinion();
//                } );
            }

        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryEventInfo(String eventId, String type, String title, String address, String happenTime,
                                                    String endTime, String level, String area, String isFalsity,String precinctId) {
        return eventManageMapper.queryEventInfo(eventId, type, title, address, happenTime,endTime,level,area,isFalsity,precinctId);
    }

    @Override
    public List<Map<String, Object>> queryEventTypeList() {
        return eventManageMapper.queryEventTypeList();
    }

    @Override
    public Result updateEventInfoByKey(String eventId, String emergencyPlanIds) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = eventManageMapper.getEvent(eventId);
            if(CollectionUtils.isEmpty(list)){
                result.setCode(500);
                result.setMessage("事件id不存在");
                return result;
            }

            int count = eventManageMapper.updateEventInfoByKey(eventId, emergencyPlanIds);
            List<String> hisPlans = eventManageMapper.queryEventPlanCfgList(eventId);
            List<String> plans = Arrays.asList(emergencyPlanIds.split(","));
            List<String> newPlans = new ArrayList<>();
            if(!CollectionUtils.isEmpty(hisPlans)){
                for(String id:hisPlans){
                    if(!plans.contains(id)){
                        eventManageMapper.deleteEventPlanCfg(eventId,id);
                    }else {
                        newPlans.add(id);
                    }
                }
            }
            if(!CollectionUtils.isEmpty(plans)){
                List<Map<String,Object>> maps = new ArrayList<>();
                for(String plan:plans){
                    if(!newPlans.contains(plan)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("eventId",eventId);
                        map.put("planId",plan);
                        maps.add(map);
                    }
                }
                if(!CollectionUtils.isEmpty(maps)){
                    eventManageMapper.saveEmergencyPlanCfg(maps);
                }
            }
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage("update success count:"+count);
        } catch (Exception e){
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}
