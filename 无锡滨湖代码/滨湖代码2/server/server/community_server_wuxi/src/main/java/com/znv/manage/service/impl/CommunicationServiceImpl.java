package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.People;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Unit;
import com.znv.manage.common.bean.UnitTree;
import com.znv.manage.common.encrypt.AESUtil;
import com.znv.manage.common.encrypt.EncryptFactory;
import com.znv.manage.common.utils.HttpsClientRequestFactory;
import com.znv.manage.common.utils.PoiUtils;
import com.znv.manage.dao.CommunicationMapper;
import com.znv.manage.service.CommunicationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

    @Value("${appKey:6d46d5365870498b8fa51936df1ccfe2}")
    private String appKey;

    @Value("${appSecret:D0B85FBD11DC320563470AE87CE4C482266F47A2ACC36BF41629EFBFDDAFBDA744EEE761CFC09A79346738F6B307FD804DA16D7F6057F910AC7D3B8DE8784F6DB66E8D49B7EADDBF19441E95EAAA0F11}")
    private String appSecret;

    @Value("${orgListUrl:https://gray.jingliangsys.cn:88/gwy-api/user-service/org/list}")
    private String orgListUrl;

    @Value("${userListUrl:https://gray.jingliangsys.cn:88/gwy-api/user-service/user/list}")
    private String userListUrl;

    @Value("${locationByUsersUrl:https://gray.jingliangsys.cn:88/gwy-api/open-service/open/message/locationByUsers}")
    private String locationByUsersUrl;

    @Value("${tokenUrl:https://gray.jingliangsys.cn:88/gwy-api/user-service/login}")
    private String tokenUrl;

    @Value("${tokenUserName:lsywtg}")
    private String tokenUserName;

    @Value("${tokenUserId:203188297843867648}")
    private String tokenUserId;

    @Value("${tokenPassword:b371d40a56d81b27118c12a2a40504f4}")
    private String tokenPassword;

    @Autowired
    private CommunicationMapper communicationMapper;

    @Autowired
    EncryptFactory encryptFactory;

    @Value("${znvr.aes.password:znvr202003131400}")
    private String aesPassword;

    private static Map<String, String> UNIT_TYPE = new HashMap<>();

    static {
        UNIT_TYPE.put("政府单位", "1");
        UNIT_TYPE.put("企业单位", "2");
        UNIT_TYPE.put("属地单位", "3");
    }

    @Override
    public Result baseInfo(String text, String type, String eventId, String unitIdParam, String precinctId, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        List<Map<String, String>> list1 = communicationMapper.baseInfo(text, type, precinctId, unitIdParam, null, null);
        Integer start = 0;
        start = pageSize * (pageNum - 1);
        List<Map<String, String>> list2 = communicationMapper.baseInfo(text, type, precinctId, unitIdParam, start, pageSize);
        for (Map<String, String> map : list2) {
            String unitId = map.get("unitId");
            if (StringUtils.isNotBlank(unitId)) {
                if (unitId.contains(",")) {
                    List<String> data = Arrays.asList(unitId.split(","));
                    StringBuffer sb = new StringBuffer();
                    for (String id : data) {
                        sb.append(communicationMapper.queryByUnitId(id)).append(",");
                    }
                    String unit = sb.toString();
                    if (unit.endsWith(",")) {
                        unit = unit.substring(0, unit.length() - 1);
                    }
                    map.put("unit", unit);
                } else {
                    map.put("unit", communicationMapper.queryByUnitId(unitId));
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("count", CollectionUtils.isEmpty(list1) ? 0 : list1.size());
        map.put("list", list2);
        result.setData(map);
        return result;
    }

    @Override
    public List<Map<String, String>> areaInfo(String precinctId, String level, String topId) {
        List<Map<String, String>> list = new ArrayList<>();
        if ("2".equals(level)) {
            list = communicationMapper.areaInfo(null, "3", precinctId);
        } else {
            list = communicationMapper.areaInfo(precinctId, level, topId);
        }
        return list;
    }

    @Override
    public List<Map<String, String>> unitInfo(String text, String type, String eventId, String precinctId) {
        return communicationMapper.unitInfo(text, type, eventId, precinctId);
    }

    @Override
    public Result unitInfo2(String text, String type, String precinctId) {
        List<Unit> list = communicationMapper.unitInfo2(text, type, precinctId);
        UnitTree unitTree = new UnitTree(list);
        List<Unit> unitList = unitTree.builTree();
        Result result = new Result();
        result.setData(unitList);
        return result;
    }


    public void aesPhone() {
        List<Map<String, String>> list = communicationMapper.queryCommunication();
        for (Map<String, String> map : list) {
            if (StringUtils.isNotBlank(map.get("telephone"))) {
                map.put("telephone", AESUtil.encrypt(map.get("telephone"), "dhf0049001262"));
            } else {
                map.put("telephone", "");
            }
            if (StringUtils.isNotBlank(map.get("phone"))) {
                map.put("phone", AESUtil.encrypt(map.get("phone"), "dhf0049001262"));
            } else {
                map.put("phone", "");
            }
            if (StringUtils.isNotBlank(map.get("telephonehome"))) {
                map.put("telephonehome", AESUtil.encrypt(map.get("telephonehome"), "dhf0049001262"));
            } else {
                map.put("telephonehome", "");
            }
        }
        communicationMapper.saveCommunicationAesPhone(list);
    }

    @Override
    public void communicationOrder(JSONArray jsonArray) {
        List<Map> maps = JSONArray.parseArray(jsonArray.toJSONString(), Map.class);
        //存数据库
        communicationMapper.saveCommunicationOrder(maps);
    }

    @Override
    public void saveZFPeople() {
        try {
            System.setProperty("https.protocols", "TLSv1.2");
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.add("appKey", appKey);
            headers.add("appSecret", appSecret);
            headers.setContentType(APPLICATION_JSON);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orgId", "54577448238448640");
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);
            ResponseEntity<String> result = restTemplate.exchange(orgListUrl, HttpMethod.POST, entity, String.class);
            JSONObject json = JSONObject.parseObject(result.getBody());
            JSONArray resData = json.getJSONArray("resData");
            Set<Map<String, String>> orgIds = getOrgId(resData);
            if (!CollectionUtils.isEmpty(orgIds)) {
                for (Map<String, String> org : orgIds) {
                    JSONObject object = new JSONObject();
                    object.put("pageSize", 10000);
                    object.put("pageNum", 1);
                    object.put("orgId", org.get("orgId"));
                    HttpEntity<String> entity1 = new HttpEntity<String>(object.toJSONString(), headers);
                    ResponseEntity<String> result1 = restTemplate.exchange(userListUrl, HttpMethod.POST, entity1, String.class);
                    JSONObject json1 = JSONObject.parseObject(result1.getBody());
                    JSONArray people = json1.getJSONObject("resData").getJSONArray("resultList");
                    if (!CollectionUtils.isEmpty(people)) {
                        communicationMapper.saveZFPeople(people);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取执法人员信息异常：" + e.getMessage());
        }
    }

    @Override
    public void updatePeopleGps() {
        try {
            System.setProperty("https.protocols", "TLSv1.2");
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.add("appKey", appKey);
            headers.add("appSecret", appSecret);
            headers.setContentType(APPLICATION_JSON);
            JSONObject jsonObject = new JSONObject();
            List<String> userIds = communicationMapper.queryZFUserIds();
            jsonObject.put("userIds", userIds);
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);
            ResponseEntity<String> result = restTemplate.exchange(locationByUsersUrl, HttpMethod.POST, entity, String.class);
            JSONObject json = JSONObject.parseObject(result.getBody());
            JSONArray resData = json.getJSONArray("resData");
            if (!CollectionUtils.isEmpty(resData)) {
                communicationMapper.saveZFPeopleGps(resData);
            }
        } catch (Exception e) {
            log.error("获取执法人员位置信息异常：" + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> queryZFPeopleList(String phone, String precinctName) {
        return communicationMapper.queryZFPeopleList(phone, precinctName);
    }

    @Override
    public Map<String, String> queryTokenByPhone(String phone) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            List<Map<String, Object>> list = communicationMapper.queryZFPeopleList(phone, null);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> map = list.get(0);
                String userId = map.get("userId").toString();
                String pwd = String.valueOf(map.get("pwd"));
                String account = String.valueOf(map.get("account"));
                resultMap.put("userId", userId);
                resultMap.put("userName", account);
                if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(account)) {
                    return resultMap;
                }

                System.setProperty("https.protocols", "TLSv1.2");
                RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
                HttpHeaders headers = new HttpHeaders();
                headers.add("deviceType", "1");
                headers.setContentType(APPLICATION_JSON);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userName", account);
                jsonObject.put("password", pwd);
                HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);
                ResponseEntity<String> result = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);
                JSONObject json = JSONObject.parseObject(result.getBody());
                JSONObject resData = json.getJSONObject("resData");
                String token = resData.getString("token");
                resultMap.put("token", token);
            }
        } catch (Exception e) {
            log.error("获取token异常：" + e.getMessage());
        }
        return resultMap;
    }

    @Override
    public Map<String, String> queryToken() {
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap.put("userId", tokenUserId);
            resultMap.put("userName", tokenUserName);
            System.setProperty("https.protocols", "TLSv1.2");
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.add("deviceType", "1");
            headers.setContentType(APPLICATION_JSON);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", tokenUserName);
            jsonObject.put("password", tokenPassword);
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);
            ResponseEntity<String> result = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);
            JSONObject json = JSONObject.parseObject(result.getBody());
            JSONObject resData = json.getJSONObject("resData");
            String token = resData.getString("token");
            resultMap.put("token", token);
        } catch (Exception e) {
            log.error("获取token异常：" + e.getMessage());
        }
        return resultMap;
    }

    @Override
    public List<Unit> queryUnitList(String name, String precinctId) {
        return communicationMapper.queryUnitList(name, precinctId);
    }

    @Override
    public Result deleteUnitById(String ids) {
        Result result = new Result();
        try {
            int count = communicationMapper.deleteUnitById(ids);
            result.setMessage("delete count:" + count);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result saveUnit(Unit unit, String precinctId) {
        Result result = new Result();
        try {
            List<Unit> unitList = communicationMapper.queryUnitList(null, "510100".equals(precinctId) ? null : precinctId);
            if (StringUtils.isEmpty(unit.getSuperId())) {
                unit.setSuperId("-1");
            }
            if (StringUtils.isEmpty(unit.getId())) {
                unit.setId(UUID.randomUUID().toString());
                Map<String, String> problemMap = checkInfo(unit, unitList, null);
                if (!StringUtils.isEmpty(problemMap.get("problem"))) {
                    result.setMessage(problemMap.get("problem"));
                    result.setCode(500);
                    return result;
                }
            }
            int count = communicationMapper.saveUnit(unit);
            result.setMessage("save count:" + count);

            if ("3".equals(unit.getType())) {
                if ("510100".equals(unit.getAreaId())) {
                    unit.setPrecinctKind("2");
                } else {
                    unit.setPrecinctKind("3");
                }
                communicationMapper.savePrecinct(unit);
            }
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result uploadUnitExcel(MultipartFile file, String precinctId) {
        Result result = new Result();
        try {
            List<Map<String, String>> areaList = communicationMapper.areaInfo(null, null, null);
            List<Unit> unitList = communicationMapper.queryUnitList(null, "510100".equals(precinctId) ? null : precinctId);
            Map<String, List<Map<String, String>>> excelData = PoiUtils.getResult(file, 1);
            List<Map<String, String>> problemList = new ArrayList<>();
            List<Unit> precinctList = new ArrayList<>();
            for (String s : excelData.keySet()) {
                List<Map<String, String>> list = excelData.get(s);
                List<Unit> saveList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = list.get(i);
                    Unit unit = new Unit();
                    unit.setId(UUID.randomUUID().toString());
                    unit.setName(map.get("单位名称"));
                    if (StringUtils.isEmpty(map.get("上级单位名称"))) {
                        unit.setSuperId("-1");
                    }
                    unit.setSuperName(map.get("上级单位名称"));
                    unit.setType(UNIT_TYPE.get(map.get("单位类型")));
                    if ("510100".equals(precinctId)) {
                        unit.setAreaId(getPrecinctId(map.get("所属区域"), areaList));
                    } else {
                        if (!precinctId.equals(getPrecinctId(map.get("所属区域"), areaList))) {
                            Map<String, String> problem = new HashMap<>();
                            problem.put("第" + (i + 1) + "行", "所属区域与当前登陆区域不符!");
                            problemList.add(problem);
                            continue;
                        } else {
                            unit.setAreaId(getPrecinctId(map.get("所属区域"), areaList));
                        }
                    }
                    Map<String, String> problemMap = checkInfo(unit, unitList, saveList);
                    if (!StringUtils.isEmpty(problemMap.get("problem"))) {
                        Map<String, String> problem = new HashMap<>();
                        problem.put("第" + (i + 1) + "行", problemMap.get("problem"));
                        problemList.add(problem);
                        continue;
                    }
                    unit.setSuperId(problemMap.get("superId"));
                    saveList.add(unit);
                    if ("3".equals(unit.getType())) {
                        if ("510100".equals(unit.getAreaId())) {
                            unit.setPrecinctKind("2");
                        } else {
                            unit.setPrecinctKind("3");
                        }
                        precinctList.add(unit);
                    }
                }
                if (!CollectionUtils.isEmpty(saveList)) {
                    int count = communicationMapper.saveUnitList(saveList);
                    result.setMessage("成功录入条数：" + count);
                }
                if (!CollectionUtils.isEmpty(precinctList)) {
                    int count = communicationMapper.savePrecinctList(precinctList);
                    result.setMessage("成功录入区域条数：" + count);
                }
            }
            result.setData(problemList);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
            log.error(e.getMessage());
        }
        return result;
    }

    private String getPrecinctId(String precinctName, List<Map<String, String>> areaList) {
        String precinctId = "510100";
        for (Map<String, String> map : areaList) {
            if (precinctName.equals(map.get("name"))) {
                precinctId = map.get("areaCode");
                break;
            }
        }
        return precinctId;
    }

    private Map<String, String> checkInfo(Unit unit, List<Unit> list, List<Unit> saveList) {
        Map<String, String> result = new HashMap<>();
        result.put("problem", "");
        Boolean flag = true;
        for (Unit u : list) {
            if (u.getName().equals(unit.getName())) {
                result.put("problem", "系统中存在相同单位");
                return result;
            }
        }
        if (!CollectionUtils.isEmpty(saveList)) {
            for (Unit u : saveList) {
                if (u.getName().equals(unit.getName())) {
                    result.put("problem", "表中存在相同单位");
                    return result;
                }
            }
        }
        if (!"-1".equals(unit.getSuperId())) {
            for (Unit u : list) {
                if (u.getName().equals(unit.getSuperName()) || u.getId().equals(unit.getSuperId())) {
                    result.put("superId", u.getId());
                    flag = false;
                    break;
                }
            }
            if (!CollectionUtils.isEmpty(saveList)) {
                for (Unit u : saveList) {
                    if (u.getName().equals(unit.getSuperName())) {
                        result.put("superId", u.getId());
                        flag = false;
                        break;
                    }
                }
            }
        } else {
            flag = false;
            result.put("superId", "-1");
        }
        if (flag) {
            result.put("problem", "未找到该单位上级");
            return result;
        }
        return result;
    }

    @Override
    public List<People> queryPeopleList(String name, String unitName, String precinctId) {
        return communicationMapper.queryPeopleList(name, unitName, precinctId);
    }

    @Override
    public Result deletePeopleById(String ids) {
        Result result = new Result();
        try {
            int count = communicationMapper.deletePeopleById(ids);
            result.setMessage("delete count:" + count);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result savePeople(People people) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(people.getId())) {
                people.setId(people.getPhone());
            }
            if (!StringUtils.isEmpty(people.getUnitName())) {
                List<Unit> unitList = communicationMapper.queryUnitList(people.getUnitName(), null);
                people.setUnitId(unitList.get(0).getId());
            }
            int count = communicationMapper.savePeople(people);
            result.setMessage("save count:" + count);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result uploadPeopleExcel(MultipartFile file, String precinctId) {
        Result result = new Result();
        try {
            List<Unit> unitList = communicationMapper.queryUnitList(null, precinctId);
            Map<String, List<Map<String, String>>> excelData = PoiUtils.getResult(file, 1);
            List<Map<String, String>> problemList = new ArrayList<>();
            for (String s : excelData.keySet()) {
                List<Map<String, String>> list = excelData.get(s);
                List<People> saveList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = list.get(i);
                    People people = new People();
                    people.setId(map.get("电话"));
                    people.setName(map.get("姓名"));
                    String unitId = getDepartment(unitList, map.get("所属部门"));
                    people.setUnitId(unitId);
                    people.setUnitName(map.get("所属部门"));
                    people.setPhone(map.get("电话"));
                    people.setLandLine(map.get("座机"));
                    people.setDuty(map.get("职位"));
                    if (!StringUtils.isEmpty(people.checkInfo())) {
                        Map<String, String> problem = new HashMap<>();
                        problem.put("第" + (i + 1) + "行", people.checkInfo());
                        problemList.add(problem);
                        continue;
                    }
                    saveList.add(people);
                }
                if (!CollectionUtils.isEmpty(saveList)) {
                    int count = communicationMapper.savePeopleList(saveList);
                    result.setMessage("成功录入条数：" + count);
                }
            }
            result.setData(problemList);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryPrecinctList(String precinctKind, String upPrecinctId, String precinctId) {
        return communicationMapper.queryPrecinctList(precinctKind, upPrecinctId, precinctId);
    }

    private String getDepartment(List<Unit> unitList, String dept) {
        String unitId = "";
        for (Unit unit : unitList) {
            if (unit.getName().equals(dept)) {
                unitId = unit.getId();
                break;
            }
        }
        return unitId;
    }

    private Set<Map<String, String>> getOrgId(JSONArray jsonArray) {
        Set<Map<String, String>> list = new HashSet<>();
        if (CollectionUtils.isEmpty(jsonArray)) {
            return list;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.addAll(getOrgId(jsonObject.getJSONArray("children")));
            Map<String, String> map = new HashMap<>();
            map.put("orgId", jsonObject.getString("orgId"));
            map.put("orgName", jsonObject.getString("orgName"));
            list.add(map);
        }
        return list;
    }
}
