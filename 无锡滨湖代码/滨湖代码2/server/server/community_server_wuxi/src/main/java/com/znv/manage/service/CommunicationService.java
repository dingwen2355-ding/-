package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.People;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Unit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CommunicationService {
    Result baseInfo(String text, String type, String eventId, String unitIdParam, String precinctId, Integer pageNum, Integer pageSize);

    List<Map<String, String>> areaInfo(String precinctId, String level, String topId);

    List<Map<String, String>> unitInfo(String text, String type, String eventId, String precinctId);

    Result unitInfo2(String text, String type, String precinctId);

    void aesPhone();

    void communicationOrder(JSONArray jsonArray);

    void saveZFPeople();

    void updatePeopleGps();

    List<Map<String, Object>> queryZFPeopleList(String phone, String precinctName);

    Map<String, String> queryTokenByPhone(String phone);

    Map<String, String> queryToken();

    List<Unit> queryUnitList(String name, String precinctId);

    Result deleteUnitById(String ids);

    Result saveUnit(Unit unit, String precinctId);

    Result uploadUnitExcel(MultipartFile file, String precinctId);

    List<People> queryPeopleList(String name, String unitName, String precinctId);

    Result deletePeopleById(String ids);

    Result savePeople(People people);

    Result uploadPeopleExcel(MultipartFile file, String precinctId);

    List<Map<String, Object>> queryPrecinctList(String precinctKind, String upPrecinctId, String precinctId);
}
