package com.znv.manage.dao;

import com.znv.manage.bean.DutyModule;
import com.znv.manage.bean.DutyPerson;
import com.znv.manage.bean.DutyTable;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DutyManageDao {

    List<Map<String, Object>> queryDutyPerson(
            @Param("name") String name,
            @Param("iname") String iname,
            @Param("phone") String phone,
            @Param("precinctId") String precinctId
    );

    int insertDutyPerson(
            @Param("dutyPerson") DutyPerson dutyPerson);

    int deleteDutyPerson(@Param("ids") String ids);

    int updateDutyPerson(@Param("dutyPerson") DutyPerson dutyPerson);

    @MapKey("name")
    Map<String, Object> queryDutyPersonFroMap(@Param("precinctId") String precinctId);

    int insertDutyPersonByExcel(@Param("list") List<DutyPerson> addList);

    List<Map<String, Object>> queryDutyModule(
            @Param("dutyType") String dutyType,
            @Param("moduleName") String moduleName,
            @Param("precinctId") String precinctId);

    int insertDutyModule(@Param("dutyModule") DutyModule dutyModule);

    int deleteDutyModule(@Param("ids") String ids);

    int updateDutyModule(@Param("dutyModule") DutyModule dutyModule);

    List<Map<String, Object>> queryDutyRole(@Param("name") String name,@Param("precinctId") String precinctId);

    int insertDutyRole(@Param("roleName") String roleName,@Param("precinctId") String precinctId);

    int deleteDutyRole(@Param("ids") String ids);

    int updateDutyRole(
            @Param("id") String id,
            @Param("roleName") String roleName);

    List<Map<String, Object>> queryDutyInfo(
            @Param("name") String name,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("dutyType") String dutyType,
            @Param("moduleId") String moduleId,
            @Param("precinctId") String precinctId
    );

    int deleteDutyInfo(
            @Param("dutyDate") String dutyDate,
            @Param("dutyType") String dutyType,
            @Param("moduleId") String moduleId,@Param("precinctId") String precinctId);

    int insertDutyInfo(@Param("list") List<DutyTable> addList);

    @MapKey("name")
    Map<String, String> queryPersonFroMap(@Param("precinctId") String precinctId);

    @MapKey("moduleName")
    Map<String, String> queryModuleFroMap(@Param("dutyType") String dutyType,@Param("precinctId") String precinctId);

    @MapKey("roleName")
    Map<String, String> queryRoleFroMap(@Param("precinctId") String precinctId);

    List<Map<String, Object>> queryDutyPersonByDate(
            @Param("dutyDate") String dutyDate,
            @Param("dutyType") String dutyType,
            @Param("moduleId") String moduleId,
            @Param("precinctId") String precinctId);

    List<Map<String, Object>> queryDutyModuleByTime(
            @Param("id") long id,
            @Param("moduleTypes") String moduleTypes,
            @Param("dutyType") String dutyType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("isEndToday") String isEndToday,
            @Param("precinctId") String precinctId
    );

    List<Map<String, Object>> queryData(
            @Param("precinctId") String precinctId,
            @Param("personId") String personId,
            @Param("dutyDate") String dutyDate);

    String queryModulName(@Param("dutyModule") String dutyModule);

    List<Map<String, Object>> queryDutyInfoById(
            @Param("precinctId")String precinctId,
            @Param("dutyDate")String dutyDate,
            @Param("dutyModule")String dutyModule,
            @Param("dutyPerson")String dutyPerson
    );
}

