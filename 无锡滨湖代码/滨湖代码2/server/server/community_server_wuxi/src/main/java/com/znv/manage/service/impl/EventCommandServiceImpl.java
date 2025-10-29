package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.JsonUtils;
import com.znv.manage.dao.EventCommandDao;
import com.znv.manage.service.EventCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventCommandServiceImpl implements EventCommandService {
    @Autowired
    private EventCommandDao eventCommandMapper;

    @Autowired
    RestTemplate restTemplate;

    private static final String NEAR_EVENT_URL = "http://10.1.50.120/CoordReverse/mapTransform/hitLayerByXY";
    private static final String EVENT_LIST_URL = "http://10.1.50.23:9527/wechatApp/api/wechat/flow/findInfoList";
    public static final String TOKEN_URL = "userOrg/api/org/login";

    @Value("${event_url3}")
    String eventUrl;

    /**
     * 指挥
     *
     * @return
     */
    @Override
    public Map<String, List<Map<String, String>>> communication(String eventId) {
        List<Map<String, String>> communication = eventCommandMapper.communication(eventId);
        Map<String, List<Map<String, String>>> maps = CommonUtils.ListToMap(communication, "type");
        return maps;
    }

    /**
     * 指挥
     *
     * @return
     */
    @Override
    public Result communicationNew(String eventId, String isMax) {
        List<Map<String, String>> communication = eventCommandMapper.communicationNew(eventId, isMax);
//        for (Map<String, String> map : communication) {
//            String phone = map.get("phone");
//            if(!StringUtils.isEmpty(phone)){
//                map.put("phone", CommonUtils.desensitization(AESUtil.decrypt(phone, "dhf0049001262")));
//            }
//        }
        Map<String, LinkedHashMap<String, Map<String, Object>>> maps = new HashMap<>();
        for (Map<String, String> c : communication) {
            String type = c.get("type");
            String unitId;
            String unit;
            if ("1".equals(type)) {
                unitId = c.get("commanderId");
                unit = c.get("commander");
            } else {
                unitId = c.get("unitId");
                unit = c.get("unit");
            }
            if (maps.containsKey(type)) {
                //获取
                LinkedHashMap<String, Map<String, Object>> map = maps.get(type);
                if (map.containsKey(unitId)) {
                    Map<String, Object> map2 = map.get(unitId);
                    List<Map<String, String>> o = (List<Map<String, String>>) map2.get("data");
                    o.add(c);
                } else {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("name", unit);
                    List<Map<String, String>> list = new ArrayList<>();
                    list.add(c);
                    map2.put("data", list);
                    map.put(unitId, map2);
                }
            } else {
                LinkedHashMap<String, Map<String, Object>> map = new LinkedHashMap<>();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", unit);
                List<Map<String, String>> list = new ArrayList<>();
                list.add(c);
                map2.put("data", list);
                map.put(unitId, map2);
                maps.put(type, map);
            }
        }
        JSONObject jsonObject = new JSONObject(true);
        for (String s : maps.keySet()) {
            LinkedHashMap<String, Map<String, Object>> stringMapLinkedHashMap = maps.get(s);
            JSONObject jsonObject1 = new JSONObject(true);
            for (String s1 : stringMapLinkedHashMap.keySet()) {
                jsonObject1.put(s1, stringMapLinkedHashMap.get(s1));
            }
            jsonObject.put(s, jsonObject1);
        }
        return new Result(jsonObject);
    }

    @Override
    public Object communicationOrderByDepartment(String eventId, String isMax, String precinctId) {
        List<Map<String, String>> communication;
        String planId = eventCommandMapper.queryEventPlanId(eventId);
        communication = eventCommandMapper.communicationNewByPrecinctId(precinctId, isMax, planId, "2");


//        if(!StringUtils.isEmpty(precinctId)){
//            //区级平台
//            communication = eventCommandMapper.communicationNewByPrecinctId(precinctId,isMax);
//        }else {
//            //市级平台
//
//            String eventType = eventCommandMapper.queryEventType(eventId);
//            communication = eventCommandMapper.communicationNewByEventType(eventType,isMax);
//        }


        Map<String, List<LinkedHashMap<String, Map<String, Object>>>> maps = new HashMap<>();
        for (Map<String, String> c : communication) {
            String type = c.get("type");
            String unitId;
            String unit;
            String dutyLevel = c.get("dutyLevel");
            if ("1".equals(type)) {
                unitId = c.get("commanderId");
                unit = c.get("commander");
            } else {
                unitId = c.get("unitId");
                unit = c.get("unit");
            }
            if (maps.containsKey(type)) {
                //获取
                LinkedHashMap<String, Map<String, Object>> map = maps.get(type).get(0);
                if (map.containsKey(unitId)) {
                    Map<String, Object> map2 = map.get(unitId);
                    List<Map<String, String>> o = (List<Map<String, String>>) map2.get("data");
                    o.add(c);
                } else {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("name", unit);
                    map2.put("dutyLevel", dutyLevel);
                    List<Map<String, String>> list = new ArrayList<>();
                    list.add(c);
                    map2.put("data", list);
                    map.put(unitId, map2);
                }
            } else {
                List<LinkedHashMap<String, Map<String, Object>>> list1 = new ArrayList<>();
                LinkedHashMap<String, Map<String, Object>> map = new LinkedHashMap<>();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", unit);
                map2.put("dutyLevel", dutyLevel);
                List<Map<String, String>> list = new ArrayList<>();
                list.add(c);
                map2.put("data", list);
                map.put(unitId, map2);
                list1.add(map);
                maps.put(type, list1);
            }
        }
        for (String s : maps.keySet()) {
            if (!"1".equals(s)) {
                List<LinkedHashMap<String, Map<String, Object>>> linkedHashMaps = maps.get(s);
                LinkedHashMap<String, Map<String, Object>> linkedHashMap = linkedHashMaps.get(0);
                for (String s1 : linkedHashMap.keySet()) {
                    Map<String, Object> stringObjectMap = linkedHashMap.get(s1);
                    List<Map<String, String>> o = (List<Map<String, String>>) stringObjectMap.get("data");
                    Map<String, String> map = o.get(0);
                    if (!StringUtils.isEmpty(map.get("isDefault"))) {
                        continue;
                    } else {
                        Map<String, String> map1 = new HashMap<>();
                        map1.putAll(map);
                        map1.put("personId", "");
                        map1.put("leader", "");
                        map1.put("phone", "");
                        map1.put("telephone", "");
                        map1.put("fox", "");
                        map1.put("duty", "");
                        map1.put("account", "");
                        o.add(0, map1);
                        continue;
                    }
                }
            }
        }
        return maps;
    }

    @Override
    public Object communicationOrderByDepartmentByPrecinctId(String isMax, String precinctId, String planId, String linkType) {
        List<Map<String, String>> communication = eventCommandMapper.communicationNewByPrecinctId(precinctId, isMax, planId, linkType);
        Map<String, List<LinkedHashMap<String, Map<String, Object>>>> maps = new HashMap<>();
        for (Map<String, String> c : communication) {
            String type = c.get("type");
            String unitId;
            String unit;
            String dutyLevel = c.get("dutyLevel");
            if ("1".equals(type)) {
                unitId = c.get("commanderId");
                unit = c.get("commander");
            } else {
                unitId = c.get("unitId");
                unit = c.get("unit");
            }
            if (maps.containsKey(type)) {
                //获取
                LinkedHashMap<String, Map<String, Object>> map = maps.get(type).get(0);
                if (map.containsKey(unitId)) {
                    Map<String, Object> map2 = map.get(unitId);
                    List<Map<String, String>> o = (List<Map<String, String>>) map2.get("data");
                    o.add(c);
                } else {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("name", unit);
                    map2.put("dutyLevel", dutyLevel);
                    List<Map<String, String>> list = new ArrayList<>();
                    list.add(c);
                    map2.put("data", list);
                    map.put(unitId, map2);
                }
            } else {
                List<LinkedHashMap<String, Map<String, Object>>> list1 = new ArrayList<>();
                LinkedHashMap<String, Map<String, Object>> map = new LinkedHashMap<>();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", unit);
                map2.put("dutyLevel", dutyLevel);
                List<Map<String, String>> list = new ArrayList<>();
                list.add(c);
                map2.put("data", list);
                map.put(unitId, map2);
                list1.add(map);
                maps.put(type, list1);
            }
        }
        for (String s : maps.keySet()) {
            if (!"1".equals(s)) {
                List<LinkedHashMap<String, Map<String, Object>>> linkedHashMaps = maps.get(s);
                LinkedHashMap<String, Map<String, Object>> linkedHashMap = linkedHashMaps.get(0);
                for (String s1 : linkedHashMap.keySet()) {
                    Map<String, Object> stringObjectMap = linkedHashMap.get(s1);
                    List<Map<String, String>> o = (List<Map<String, String>>) stringObjectMap.get("data");
                    Map<String, String> map = o.get(0);
                    if (!StringUtils.isEmpty(map.get("isDefault"))) {
                        continue;
                    } else {
                        Map<String, String> map1 = new HashMap<>();
                        map1.putAll(map);
                        map1.put("personId", "");
                        map1.put("leader", "");
                        map1.put("phone", "");
                        map1.put("telephone", "");
                        map1.put("fox", "");
                        map1.put("duty", "");
                        map1.put("account", "");
                        o.add(0, map1);
                        continue;
                    }
                }
            }
        }
        return maps;
    }

    @Override
    public Object communicationOrderByDepartmentByEventType(String isMax, String eventType) {
        List<Map<String, String>> communication = eventCommandMapper.communicationNewByEventType(eventType, isMax);
        Map<String, List<LinkedHashMap<String, Map<String, Object>>>> maps = new HashMap<>();
        for (Map<String, String> c : communication) {
            String type = c.get("type");
            String unitId;
            String unit;
            String dutyLevel = c.get("dutyLevel");
            if ("1".equals(type)) {
                unitId = c.get("commanderId");
                unit = c.get("commander");
            } else {
                unitId = c.get("unitId");
                unit = c.get("unit");
            }
            if (maps.containsKey(type)) {
                //获取
                LinkedHashMap<String, Map<String, Object>> map = maps.get(type).get(0);
                if (map.containsKey(unitId)) {
                    Map<String, Object> map2 = map.get(unitId);
                    List<Map<String, String>> o = (List<Map<String, String>>) map2.get("data");
                    o.add(c);
                } else {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("name", unit);
                    map2.put("dutyLevel", dutyLevel);
                    List<Map<String, String>> list = new ArrayList<>();
                    list.add(c);
                    map2.put("data", list);
                    map.put(unitId, map2);
                }
            } else {
                List<LinkedHashMap<String, Map<String, Object>>> list1 = new ArrayList<>();
                LinkedHashMap<String, Map<String, Object>> map = new LinkedHashMap<>();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", unit);
                map2.put("dutyLevel", dutyLevel);
                List<Map<String, String>> list = new ArrayList<>();
                list.add(c);
                map2.put("data", list);
                map.put(unitId, map2);
                list1.add(map);
                maps.put(type, list1);
            }
        }
        for (String s : maps.keySet()) {
            if (!"1".equals(s)) {
                List<LinkedHashMap<String, Map<String, Object>>> linkedHashMaps = maps.get(s);
                LinkedHashMap<String, Map<String, Object>> linkedHashMap = linkedHashMaps.get(0);
                for (String s1 : linkedHashMap.keySet()) {
                    Map<String, Object> stringObjectMap = linkedHashMap.get(s1);
                    List<Map<String, String>> o = (List<Map<String, String>>) stringObjectMap.get("data");
                    Map<String, String> map = o.get(0);
                    if (!StringUtils.isEmpty(map.get("isDefault"))) {
                        continue;
                    } else {
                        Map<String, String> map1 = new HashMap<>();
                        map1.putAll(map);
                        map1.put("personId", "");
                        map1.put("leader", "");
                        map1.put("phone", "");
                        map1.put("telephone", "");
                        map1.put("fox", "");
                        map1.put("duty", "");
                        map1.put("account", "");
                        o.add(0, map1);
                        continue;
                    }
                }
            }
        }
        return maps;
    }

    /**
     * 查询部门成员
     *
     * @param unitId
     * @return
     */
    @Override
    public List<Map<String, String>> unitMember(String unitId) {
        List<Map<String, String>> communication = eventCommandMapper.unitMember(unitId);
//        for (Map<String, String> map : communication) {
//            String phone = map.get("phone");
//            if(!StringUtils.isEmpty(phone)){
//                map.put("phone", CommonUtils.desensitization(AESUtil.decrypt(phone, "dhf0049001262")));
//            }
//            String telephone = map.get("telephone");
//            if(!StringUtils.isEmpty(telephone)){
//                map.put("telephone", CommonUtils.desensitization(AESUtil.decrypt(telephone, "dhf0049001262")));
//            }
//        }
        return communication;
    }

    @Override
    public List<Map<String, String>> areaMember(String areaCode) {
        return eventCommandMapper.areaMember(areaCode);
    }

    @Override
    public void eventLeader(String eventId, String data) {
        List<Map<String, String>> maps = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(data);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Map<String, String> map = new HashMap<>();
            map.put("eventId", eventId);
            map.put("type", "1");
            map.put("unit", JsonUtils.getJSONString(json, "unit"));
            map.put("leader", JsonUtils.getJSONString(json, "leader"));
            maps.add(map);
        }
        //插入数据
        eventCommandMapper.eventLeader(maps);
    }

    @Override
    public Map<String, List<Map<String, String>>> queryEventCommunicationConfig(String eventId) {
        List<Map<String, String>> maps = eventCommandMapper.queryEventCommunicationConfig(eventId);
        //数据处理
        Map<String, List<Map<String, String>>> newMap = new HashMap<>();
        maps.forEach(map -> {
            String type = map.get("type");
            if (newMap.containsKey(type)) {
                List<Map<String, String>> list = newMap.get(type);
                map.remove("type");
                list.add(map);
            } else {
                List<Map<String, String>> list = new ArrayList<>();
                map.remove("type");
                list.add(map);
                newMap.put(type, list);
            }
        });
        return newMap;
    }

    @Override
    public void updateEventCommunication(JSONObject json) {
        String eventId = JsonUtils.getJSONString(json, "eventId");
        //删除事件关联
        JSONObject data = JsonUtils.getJSONObject(json, "data");
        eventCommandMapper.deleteEventCommunication(eventId);
        //插入新数据
        Set<String> dataKey = data.keySet();
        List<Map<String, String>> maps = new ArrayList<>();
        for (String s : dataKey) {
            if (s.equals("1")) {
                JSONObject zb = JsonUtils.getJSONObject(data, "1");
                Set<String> strings = zb.keySet();
                for (String key : strings) {
                    Map<String, String> map = new HashMap<>();
                    map.put("eventId", eventId);
                    map.put("type", "1");
                    map.put("unit_id", key);
                    map.put("leader", zb.getString(key));
                    map.put("dutyLevel", "0");
                    map.put("sort", "");
                    maps.add(map);
                }
            } else {
                JSONArray jsonArray = JsonUtils.getJSONArray(data, s);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject unit = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("eventId", eventId);
                    map.put("type", s);
                    map.put("unit_id", JsonUtils.getJSONString(unit, "unitId"));
                    map.put("leader", JsonUtils.getJSONString(unit, "leader"));
                    map.put("dutyLevel", JsonUtils.getJSONString(unit, "dutyLevel"));
                    map.put("sort", i + "");
                    maps.add(map);
                }
            }
        }
        //保存数据
        eventCommandMapper.saveEventCommunication(maps);
    }

    @Override
    public void updateEventCommunicationByPrecinctId(JSONObject json) {
        String precinctId = JsonUtils.getJSONString(json, "precinctId");
        if (org.springframework.util.StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        String planId = JsonUtils.getJSONString(json, "planId");
        String linkType = JsonUtils.getJSONString(json, "linkType");
        //删除事件关联
        JSONObject data = JsonUtils.getJSONObject(json, "data");
        eventCommandMapper.deleteEventCommunicationByPrecinctId(precinctId, planId);
        //插入新数据
        Set<String> dataKey = data.keySet();
        List<Map<String, String>> maps = new ArrayList<>();
        for (String s : dataKey) {
            if ("1".equals(s)) {
                JSONObject zb = JsonUtils.getJSONObject(data, "1");
                Set<String> strings = zb.keySet();
                for (String key : strings) {
                    Map<String, String> map = new HashMap<>();
                    map.put("precinctId", precinctId);
                    map.put("planId", planId);
                    map.put("linkType", linkType);
                    map.put("type", "1");
                    map.put("unit_id", key);
                    map.put("leader", zb.getString(key));
                    map.put("dutyLevel", "0");
                    map.put("sort", "");
                    maps.add(map);
                }
            } else {
                JSONArray jsonArray = JsonUtils.getJSONArray(data, s);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject unit = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("precinctId", precinctId);
                    map.put("planId", planId);
                    map.put("linkType", linkType);
                    map.put("type", s);
                    map.put("unit_id", JsonUtils.getJSONString(unit, "unitId"));
                    map.put("leader", JsonUtils.getJSONString(unit, "leader"));
                    map.put("dutyLevel", JsonUtils.getJSONString(unit, "dutyLevel"));
                    map.put("sort", i + "");
                    maps.add(map);
                }
            }
        }
        //保存数据
        eventCommandMapper.saveEventCommunicationByPrecinctId(maps);
    }

    @Override
    public void updateEventCommunicationByEventType(JSONObject json) {
        String eventType = JsonUtils.getJSONString(json, "eventType");
        //删除事件关联
        JSONObject data = JsonUtils.getJSONObject(json, "data");
        eventCommandMapper.deleteEventCommunicationByEventType(eventType);
        //插入新数据
        Set<String> dataKey = data.keySet();
        List<Map<String, String>> maps = new ArrayList<>();
        for (String s : dataKey) {
            if (s.equals("1")) {
                JSONObject zb = JsonUtils.getJSONObject(data, "1");
                Set<String> strings = zb.keySet();
                for (String key : strings) {
                    Map<String, String> map = new HashMap<>();
                    map.put("eventType", eventType);
                    map.put("type", "1");
                    map.put("unit_id", key);
                    map.put("leader", zb.getString(key));
                    map.put("dutyLevel", "0");
                    map.put("sort", "");
                    maps.add(map);
                }
            } else {
                JSONArray jsonArray = JsonUtils.getJSONArray(data, s);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject unit = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("eventType", eventType);
                    map.put("type", s);
                    map.put("unit_id", JsonUtils.getJSONString(unit, "unitId"));
                    map.put("leader", JsonUtils.getJSONString(unit, "leader"));
                    map.put("dutyLevel", JsonUtils.getJSONString(unit, "dutyLevel"));
                    map.put("sort", i + "");
                    maps.add(map);
                }
            }
        }
        //保存数据
        eventCommandMapper.saveEventCommunicationByEventType(maps);
    }

    @Override
    public List<Map<String, Object>> queryUnitSystemByEventId(String eventId, String systemType) {
        return eventCommandMapper.queryUnitSystemByEventId(eventId, systemType);
    }

    @Override
    public Map<String, Object> queryEventCommunicationOn(String eventId) {
        return eventCommandMapper.queryEventCommunicationOn(eventId);
    }

    @Override
    public Map<String, List<Map<String, String>>> queryUnitSystem(String systemType, String unit, String systemName) {
        List<Map<String, String>> list = eventCommandMapper.queryUnitSystem(systemType, unit, systemName);
        Map<String, List<Map<String, String>>> system_type = CommonUtils.ListToMap(list, "system_type");
        return system_type;
    }

    @Override
    public void updateLeader(String eventId, String type, String leader) {
        eventCommandMapper.updateLeader(eventId, type, leader);
    }

    @Override
    public void updateLeader2(JSONObject json) {
        String eventId = json.getString("eventId");
        JSONArray list = json.getJSONArray("list");
        List<Map<String, String>> maps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<>();
            JSONObject comm = list.getJSONObject(i);
            map.put("eventId", eventId);
            map.put("type", "1");
            map.put("unit_id", comm.getString("commanderId"));
            map.put("leader", comm.getString("commander"));
            maps.add(map);
        }
        if (maps.size() > 0) {
            eventCommandMapper.saveEventCommunication(maps);
        }
    }

    @Override
    public void updateLeaderByEventType(JSONObject json) {
        String eventType = json.getString("eventType");
        JSONArray list = json.getJSONArray("list");
        List<Map<String, String>> maps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<>();
            JSONObject comm = list.getJSONObject(i);
            map.put("eventType", eventType);
            map.put("type", "1");
            map.put("unit_id", comm.getString("commanderId"));
            map.put("leader", comm.getString("commander"));
            maps.add(map);
        }
        if (maps.size() > 0) {
            eventCommandMapper.saveEventCommunicationByEventType(maps);
        }
    }

    @Override
    public void updateLeaderByPrecinctId(JSONObject json) {
        String precinctId = json.getString("precinctId");
        if (org.springframework.util.StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        String planId = json.getString("planId");
        String linkType = json.getString("linkType");
        JSONArray list = json.getJSONArray("list");
        List<Map<String, String>> maps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<>();
            JSONObject comm = list.getJSONObject(i);
            map.put("precinctId", precinctId);
            map.put("planId", planId);
            map.put("linkType", linkType);
            map.put("type", "1");
            map.put("unit_id", comm.getString("commanderId"));
            map.put("leader", comm.getString("commander"));
            maps.add(map);
        }
        if (maps.size() > 0) {
            eventCommandMapper.saveEventCommunicationByPrecinctId(maps);
        }
    }

    //    @Override
//    public void defaultLeader(String unitId, String personId) {
//        eventCommandMapper.defaultLeader(unitId,personId);
//    }
    @Override
    public List<Map<String, Object>> queryUnitSystemList() {
        return eventCommandMapper.queryUnitSystemList();
    }

    @Override
    public Map<String, List<Map<String, Object>>> queryUnitSystem2() {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        map.put("1", eventCommandMapper.queryUnitSystemList());
        map.put("2", eventCommandMapper.queryOperatorDeviceList());
        return map;
    }

    @Override
    public List<Map<String, Object>> queryTopicInfoList(String topicId, String topicName) {
        return eventCommandMapper.queryTopicInfoList(topicId, topicName);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("4");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("4")) {
                list.add(i, "3");
                i++;
            }
        }
        for (String s : list) {
            System.err.println(s);
        }
    }

    @Override
    public Result getEventData(String beginTime, String endTime, String precinctId, String title, String eventType, Integer pageNum, Integer pageSize) {

        Result result = new Result();
        try {
            List<Map<String, Object>> list1 = eventCommandMapper.getEventData(beginTime, endTime, precinctId, title, eventType, null, null);
            if (null == pageNum || null == pageSize) {
                result.setData(list1);
                return result;
            }
            Integer start = 0;
            start = pageSize * (pageNum - 1);
            List<Map<String, Object>> list2 = eventCommandMapper.getEventData(beginTime, endTime, precinctId, title, eventType, start, pageSize);
            Map<String, Object> map = new HashMap<>();
            map.put("count", CollectionUtils.isEmpty(list1) ? 0 : list1.size());
            map.put("list", list2);
            result.setData(map);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            e.printStackTrace();
            log.error(e.toString());
        }

        return result;
    }

    @Override
    public Result getEventDataByType(String title, String beginTime, String endTime, String precinctId, String type, String eventId, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("count", 0);
            map.put("list", new ArrayList<>());
            result.setData(map);
            String eventType = "";
            Map<String, String> gpsMap = new HashMap<>();

            List<Map<String, Object>> list2 = new ArrayList<>();
            if ("1".equals(type)) {
                //根据事件类型查
                eventType = eventCommandMapper.queryEventType(eventId);
                if (StringUtils.isEmpty(eventType)) {
                    return result;
                }
            } else if ("2".equals(type)) {
                //查附近10km
                gpsMap = eventCommandMapper.queryGpsByEventId(eventId);
                if (StringUtils.isEmpty(gpsMap.get("gpsx")) || StringUtils.isEmpty(gpsMap.get("gpsy"))) {
                    return result;
                }
                /*JSONObject requestJson = new JSONObject();
                requestJson.put("ruleType", "工单查询");
                requestJson.put("layerName", "事件枢纽");
                requestJson.put("distance", 10000);
                JSONObject geometry = new JSONObject();
                geometry.put("x", gpsMap.get("gpsx"));
                geometry.put("y", gpsMap.get("gpsy"));
                requestJson.put("geometry", geometry);
                requestJson.put("pageNo", pageNum);
                requestJson.put("pageSize", pageSize);
                JSONObject responseJson = EventRequestUtil.postRequest(restTemplate, NEAR_EVENT_URL, requestJson, eventUrl + TOKEN_URL);
                if (!"success".equals(responseJson.getString("msg"))) {
                    throw new BusinessException("get response failed,requestParam:" + responseJson);
                }
                JSONObject data = responseJson.getJSONObject("data");
                JSONArray resultArray = data.getJSONArray("result");
                if (!resultArray.isEmpty()) {
                    //只取前20
                    for (int i = 0; i < resultArray.size() && i < 20; i++) {
                        JSONObject tempJson = resultArray.getJSONObject(i);
                        JSONObject hadressJson = tempJson.getJSONObject("hadress");
                        String flowNo = tempJson.getString("flowno");
                        Map<String, Object> tempMap = new HashMap<>();

                        //从对方事件列表接口拿到描述和类型字段
                        JSONObject extendRequestJson = new JSONObject();
                        extendRequestJson.put("page", 1);
                        extendRequestJson.put("size", 1);
                        extendRequestJson.put("field_flag", "1");
                        extendRequestJson.put("flowNo", flowNo);
                        JSONObject extendResponseJson = EventRequestUtil.postRequest(restTemplate, EVENT_LIST_URL, extendRequestJson, eventUrl + TOKEN_URL);
                        if (extendResponseJson.getInteger("code") == 200) {
                            JSONArray extendArray = extendResponseJson.getJSONObject("data").getJSONArray("rows");
                            if (extendArray != null && !extendArray.isEmpty()) {
                                tempMap.put("event_type_name", extendArray.getJSONObject(0).getString("$class_1$"));
                                tempMap.put("desc", extendArray.getJSONObject(0).getString("desc"));
                            }
                        }

                        //其他数据在对方附件查询接口已存在
                        tempMap.put("event_id", flowNo);
                        tempMap.put("area", tempJson.getString("coutrict"));
                        tempMap.put("happen_time", tempJson.getString("reportdate"));
                        tempMap.put("area_code", tempJson.getString("town_code"));
                        tempMap.put("event_title", tempJson.getString("questiontitle"));
                        if (hadressJson != null) {
                            tempMap.put("gpsx", hadressJson.getString("lng"));
                            tempMap.put("gpsy", hadressJson.getString("lat"));
                        }
                        list2.add(tempMap);
                    }
                }*/
                list2 = eventCommandMapper.queryNearbyEvent(gpsMap.get("gpsx"), gpsMap.get("gpsy"));
                //list2去重
                if (!CollectionUtils.isEmpty(list2)) {
                    list2 = list2.stream().filter(distinctByKey(temp -> temp.get("event_id")))
                            .filter(temp -> !eventId.equals(temp.get("event_id")))
                            .collect(Collectors.toList());
                }
                map.put("count", CollectionUtils.isEmpty(list2) ? 0 : list2.size());
                map.put("list", list2);
                result.setData(map);
                return result;
            }

            List<Map<String, Object>> list1 = eventCommandMapper.getEventDataByType(title, beginTime, endTime, precinctId, eventType, gpsMap.get("gpsx"), gpsMap.get("gpsy"), eventId, null, null);
            if (null == pageNum || null == pageSize) {
                result.setData(list1);
                return result;
            }
            Integer start = 0;
            start = pageSize * (pageNum - 1);
            list2 = eventCommandMapper.getEventDataByType(title, beginTime, endTime, precinctId, eventType, gpsMap.get("gpsx"), gpsMap.get("gpsy"), eventId, start, pageSize);
            map.put("count", CollectionUtils.isEmpty(list1) ? 0 : list1.size());
            map.put("list", list2);
            result.setData(map);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            e.printStackTrace();
            log.error(e.toString());
        }

        return result;
    }

    @Override
    public String queryVistitCount(Date beginTime, Date endTime) {
        return eventCommandMapper.queryVistitCount(beginTime, endTime);
    }

    @Override
    public Result queryEventResource(String eventId) {
        Result result = new Result();
        try {
            JSONObject jsonObject = new JSONObject();
            String planId = eventCommandMapper.queryEventPlanId(eventId);
            if (!StringUtils.isEmpty(planId)) {
                jsonObject.put("stuffs", eventCommandMapper.queryStuffs(planId));
                jsonObject.put("experts", eventCommandMapper.queryExperts(planId));
                jsonObject.put("teams", eventCommandMapper.queryTeams(planId));
                jsonObject.put("shelters", eventCommandMapper.queryShelters(planId));
            }
            result.setData(jsonObject);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
