package com.znv.manage.service;

import com.znv.manage.bean.DutyModule;
import com.znv.manage.bean.DutyPerson;
import com.znv.manage.bean.DutyTable;
import com.znv.manage.bean.DutyTablePro;

import java.util.List;
import java.util.Map;

public interface DutyManageService {

    List<Map<String, Object>> queryDutyPerson(String name, String iname, String phone,String precinctId);

    int insertDutyPerson(DutyPerson dutyPerson);

    int deleteDutyPerson(String ids);

    int updateDutyPerson(DutyPerson dutyPerson);

    Map<String, Object> queryDutyPersonFroMap(String precinctId);

    int insertDutyPersonByExcel(List<DutyPerson> addList);

    List<Map<String, Object>> queryDutyModule(String dutyType, String moduleName,String precinctId);

    int insertDutyModule(DutyModule dutyModule);

    int deleteDutyModule(String ids);

    int updateDutyModule(DutyModule dutyModule);

    List<Map<String, Object>> queryDutyRole(String name,String precinctId);

    int insertDutyRole(String roleName,String precinctId);

    int deleteDutyRole(String ids);

    int updateDutyRole(String id, String roleName);

    List<Map<String, Object>> queryDutyInfo(
            String name,
            String startDate,
            String endDate,
            String dutyType,
            String moduleId,
            String precinctId
    );

    int updateDutyInfo(DutyTablePro dutyTablePro);

    Map<String, String> queryPersonFroMap(String precinctId);

    Map<String, String> queryModuleFroMap(String dutyType,String precinctId);

    Map<String, String> queryRoleFroMap(String precinctId);

    int insertDutyInfo(List<DutyTable> addList);

    List<Map<String, Object>> queryDutyPersonByDate(String dutyDate, String dutyType, String moduleId, String precinctId);

    List<Map<String, Object>> queryDutyModuleByTime(long id,String moduleTypes,String dutyType,String startTime, String endTime, String isEndToday,String precinctId);

    List<Map<String, Object>> queryData(String precinctId, String personId, String dutyDate);

    String queryModulName(String dutyModule);

    List<Map<String, Object>> queryDutyInfoById(String precinctId, String dutyDate, String dutyModule, String dutyPerson);
}
