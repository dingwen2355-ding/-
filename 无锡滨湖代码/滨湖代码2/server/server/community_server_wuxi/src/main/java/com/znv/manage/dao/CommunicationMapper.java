package com.znv.manage.dao;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Communication;
import com.znv.manage.common.bean.People;
import com.znv.manage.common.bean.Unit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CommunicationMapper {

    int deleteByPrimaryKey(String id);

    int insert(Communication record);

    int insertSelective(Communication record);

    Communication selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Communication record);

    int updateByPrimaryKey(Communication record);

    List<Map<String, String>> baseInfo(@Param("text") String text, @Param("type") String type, @Param("precinctId") String precinctId, @Param("unitId") String unitId,
                                       @Param("pageStart") Integer pageStart,
                                       @Param("pageLen") Integer pageLen);

    List<Map<String, String>> areaInfo(@Param("precinctId") String precinctId,
                                       @Param("level") String level,
                                       @Param("topId") String topId);

    List<Map<String, String>> unitInfo(@Param("text") String text, @Param("type") String type, @Param("eventId") String eventId, @Param("precinctId") String precinctId);

    List<Unit> unitInfo2(@Param("text") String text, @Param("type") String type, @Param("precinctId") String precinctId);

    List<Map<String, String>> queryCommunication();

    void saveCommunicationAesPhone(@Param("lists") List<Map<String, String>> lists);

    void saveCommunicationOrder(@Param("maps") List<Map> maps);

    String queryByUnitId(@Param("id") String unitId);

    int saveZFPeople(@Param("list") JSONArray jsonArray);

    int saveZFPeopleGps(@Param("list") JSONArray jsonArray);

    List<String> queryZFUserIds();

    List<Map<String, Object>> queryZFPeopleList(@Param("phone") String phone, @Param("precinctName") String precinctName);

    List<Unit> queryUnitList(@Param("name") String name, @Param("precinctId") String precinctId);

    int deleteUnitById(@Param("ids") String ids);

    int saveUnit(Unit unit);

    int saveUnitList(@Param("list") List<Unit> list);

    int savePrecinct(Unit unit);

    int savePrecinctList(@Param("list") List<Unit> list);

    List<People> queryPeopleList(@Param("name") String name, @Param("unitName") String unitName, @Param("precinctId") String precinctId);

    int deletePeopleById(@Param("ids") String ids);

    int savePeople(People people);

    int savePeopleList(List<People> list);

    List<Map<String, Object>> queryPrecinctList(@Param("precinctKind") String precinctKind,
                                                @Param("upPrecinctId") String upPrecinctId,
                                                @Param("precinctId") String precinctId);
}