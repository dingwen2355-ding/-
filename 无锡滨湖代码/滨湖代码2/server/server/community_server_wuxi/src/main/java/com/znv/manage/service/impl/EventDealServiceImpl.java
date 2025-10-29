package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.*;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.EventDealMapper;
import com.znv.manage.dao.XyxcUserDao;
import com.znv.manage.service.EventDealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventDealServiceImpl implements EventDealService {

    @Autowired
    EventDealMapper eventDealMapper;

    @Autowired
    private SyncEventServiceImpl syncEventService;

    @Autowired
    private XyxcUserDao xyxcUserDao;

    @Override
    public Result getEventDealFlowInfo(String eventId, String status) {

        Result result = new Result();
        try {
            Map<String, Object> map = eventDealMapper.getEventInfo(eventId).get(0);
            Map<String, Object> temp = new HashMap<>();
            String eventAbstract = updateEventDescInfo(map);
            log.info("eventAbstract = " + eventAbstract);
            temp.put("eventAbstract", eventAbstract);

            List<Map<String, Object>> eventDealFlow = eventDealMapper.getEventDealFlowInfo(eventId, status);
            log.info("eventDealFlow = " + eventDealFlow.toString());

            temp.put("eventDealFlow", eventDealFlow);
            temp.put("emergencyInfo", eventDealMapper.getEventEmergencyInfo(eventId));
            result.setData(temp);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
            log.info(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Map<String, Object> getEventNewDealFlowInfo(String eventId) {
        return eventDealMapper.getEventNewDealFlowInfo(eventId);
    }

    @Override
    public Result getEventInfo(String eventId) {
        Result result = new Result();
        try {
            result.setData(eventDealMapper.getEventInfo(eventId));
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result insertEventDealFlowInfo(HistoryDealContent historyDealContent) {

        Result result = new Result();
        if (StringUtils.isEmpty(historyDealContent.getEventId())) {
            result.setResutCode(ResultCodeEnum.NOTACCEPTABLE.getCode(), ResultCodeEnum.NOTACCEPTABLE.getName());
            result.setData("event is null");
        } else {
            try {
                if (StringUtils.isBlank(historyDealContent.getRecordTime())) {
                    historyDealContent.setRecordTime(null);
                }
                eventDealMapper.insertEventDealInfo(historyDealContent);
                result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
            } catch (Exception e) {
                log.error(e.toString());
                result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
                result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
                result.setData(e.toString());
            }
        }
        return result;
    }

    @Override
    public Result updateEventDealFlowInfo(HistoryDealContent historyDealContent) {
        Result result = new Result();

        if (StringUtils.isEmpty(historyDealContent.getEventId())) {
            result.setResutCode(ResultCodeEnum.NOTACCEPTABLE.getCode(), ResultCodeEnum.NOTACCEPTABLE.getName());
            result.setData("event is null");
        } else {
            try {
                eventDealMapper.updateEventDealFLowInfo(historyDealContent);
                result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
            } catch (Exception e) {
                log.error(e.toString());
                result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
                result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
                result.setData(e.toString());
            }
        }
        return result;
    }

    @Override
    public Result deleteEventDealFlowInfo(String ids) {
        try {
            eventDealMapper.deleteFlowByEventId(ids);
            return new Result();
        } catch (Exception e) {
            return new Result(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
    }

    @Override
    public Result batchUpdateDeafFlow(String eventId, JSONArray list) {
        List<HistoryDealContent> eventFlow = list.toJavaList(HistoryDealContent.class);
        for (HistoryDealContent his : eventFlow) {
            if (StringUtils.isBlank(his.getRecordTime())) {
                his.setRecordTime(null);
            }
        }
        if (!StringUtils.isEmpty(eventId) && !"".equals(eventId)) {
            eventDealMapper.deleteFlowByEventId(eventId);
            if (eventFlow.size() > 0) {
                eventDealMapper.batchUpdateDealFlow(eventFlow);
            }
        }
        return null;
    }

    private String updateEventDescInfo(Map<String, Object> map) {
        String eventBeginInfo = "";
        try {

            String desc = map.get("description").toString();
            String date = map.get("acceptTime").toString();

            if (!org.springframework.util.StringUtils.isEmpty(date)) {
                String descBeginContent = desc.contains("，") ? desc.substring(0, desc.indexOf('，')) : desc;
                if (descBeginContent.contains("日") && descBeginContent.contains("月")) {
                    log.info("eventDesc 已包含日期，无需再添加");
                    return desc;
                } else {
                    String happenDate = DateUtil.getFormatDateStr(map.get("acceptTime").toString(), "yyyy-MM-dd")
                            .replaceFirst("-", "年")
                            .replace("-", "月") + "日";

                    eventBeginInfo = happenDate + "，" + desc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("eventDesc添加日期出错");
            log.error(e.toString());
        }
        log.info("eventDesc" + eventBeginInfo);
        return eventBeginInfo;
    }


    @Override
    public Result getVideoListByEvent(String gpsX, String gpsY) {
        //获取事件经纬度
        Result result = new Result();
        List<SyncDevice> list = new ArrayList<>();
        try {
            //获取所有设备数据
            List<SyncDevice> deviceInfoList = eventDealMapper.getDeviceList();
            if (!CollectionUtils.isEmpty(deviceInfoList) || (deviceInfoList.size() != 0)) {
                for (SyncDevice syncDevice : deviceInfoList) {
                    double distance = CommonUtils.getDistance(gpsX, gpsY,
                            String.valueOf(syncDevice.getLongitude()),
                            String.valueOf(syncDevice.getLatitude()));
                    syncDevice.setDistance(distance);
                }
                list = deviceInfoList.stream()
                        .sorted(Comparator.comparing(SyncDevice::getDistance))
                        .limit(3).collect(Collectors.toList());

            }
            result.setData(list);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Set<Map<String,Object>> queryOperators(String eventId) {
        Set<Map<String,Object>> users = new HashSet<>();
        String ids = "";
        JSONArray dealList= syncEventService.getDealContentForWeb2(eventId).getJSONArray("rows");
        if(!CollectionUtils.isEmpty(dealList)){
            List<String> idList = new ArrayList<>();
            for(int i=0;i<dealList.size();i++){
                JSONObject jsonObject = dealList.getJSONObject(i);
                String content = jsonObject.getString("operationContent");
                if(!StringUtils.isEmpty(content) && !content.contains("处置完成") && content.contains("/")){
                    String str2 = content.substring(content.indexOf("/"));
                    if(!StringUtils.isEmpty(str2) && str2.contains("【") && str2.contains("】")){
                        String name1 = str2.substring(str2.indexOf("【") + 1, str2.indexOf("】"));
                        String dept1 = str2.substring(1,str2.indexOf("【"));
                        users.addAll(xyxcUserDao.queryUserInfo2(null,name1,
                                null,dept1));
                    }
                    if(str2.contains("向")){
                        String str3 = content.substring(content.indexOf("向"));
                        String name2 = str3.substring(str3.indexOf("【") + 1, str3.indexOf("】"));
                        String dept2 = str3.substring(str3.indexOf("/")+1,str3.indexOf("【"));
                        users.addAll(xyxcUserDao.queryUserInfo2(null,name2,
                                null,dept2));
                    }
                }
                if(null != jsonObject.getJSONObject("projectTaskInfo")){
                    String to_member_name = jsonObject.getJSONObject("projectTaskInfo").getString("to_member_name");
                    if(!StringUtils.isEmpty(to_member_name)){
                        idList.add(to_member_name);
                    }
                }
            }
            if(!CollectionUtils.isEmpty(idList)){
                ids = String.join(",",idList);
            }
        }
        users.addAll(xyxcUserDao.queryUserInfo2(null,null,ids,null));
        return users;
    }

}
