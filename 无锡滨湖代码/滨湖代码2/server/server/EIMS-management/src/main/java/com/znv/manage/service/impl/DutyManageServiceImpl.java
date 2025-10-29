package com.znv.manage.service.impl;

import com.znv.manage.bean.*;
import com.znv.manage.dao.DutyManageDao;
import com.znv.manage.service.DutyManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：ygr
 * @date ：Created in 2020-5-19
 */
@Service
@Slf4j
public class DutyManageServiceImpl implements DutyManageService {

    @Autowired
    private DutyManageDao dutyManageDao;

    @Override
    public List<Map<String, Object>> queryDutyPerson(String name,String iname,String phone,String precinctId) {
        return dutyManageDao.queryDutyPerson(name, iname,phone,precinctId);
    }

    @Override
    public int insertDutyPerson(DutyPerson dutyPerson) {
        return dutyManageDao.insertDutyPerson(dutyPerson);
    }

    @Override
    public int deleteDutyPerson(String ids) {
        return dutyManageDao.deleteDutyPerson(ids);
    }

    @Override
    public int updateDutyPerson(DutyPerson dutyPerson) {
        return dutyManageDao.updateDutyPerson(dutyPerson);
    }

    @Override
    public Map<String, Object> queryDutyPersonFroMap(String precinctId) {
        return dutyManageDao.queryDutyPersonFroMap(precinctId);
    }

    @Override
    public int insertDutyPersonByExcel(List<DutyPerson> addList) {
        return dutyManageDao.insertDutyPersonByExcel(addList);
    }

    @Override
    public List<Map<String, Object>> queryDutyModule(String dutyType, String moduleName,String precinctId) {
        return dutyManageDao.queryDutyModule(dutyType,moduleName,precinctId);
    }

    @Override
    public int insertDutyModule(DutyModule dutyModule) {
        return dutyManageDao.insertDutyModule(dutyModule);
    }

    @Override
    public int deleteDutyModule(String ids) {
        return dutyManageDao.deleteDutyModule(ids);
    }

    @Override
    public int updateDutyModule(DutyModule dutyModule) {
        return dutyManageDao.updateDutyModule(dutyModule);
    }

    @Override
    public List<Map<String, Object>> queryDutyRole(String name,String precinctId) {
        return dutyManageDao.queryDutyRole(name,precinctId);
    }

    @Override
    public int insertDutyRole(String roleName,String precinctId) {
        return dutyManageDao.insertDutyRole(roleName,precinctId);
    }

    @Override
    public int deleteDutyRole(String ids) {
        return dutyManageDao.deleteDutyRole(ids);
    }

    @Override
    public int updateDutyRole(String id, String roleName) {
        return dutyManageDao.updateDutyRole(id,roleName);
    }

    @Override
    public List<Map<String, Object>> queryDutyInfo(
            String name, String startDate, String endDate,String dutyType,String moduleId,String precinctId) {
        return dutyManageDao.queryDutyInfo(name,startDate,endDate,dutyType,moduleId,precinctId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDutyInfo(DutyTablePro dutyTablePro) {
        try{
            List<PersonRole> list = dutyTablePro.getPersonList();
            List<DutyTable> addList = new ArrayList<>();
            int k = dutyManageDao.deleteDutyInfo(
                    dutyTablePro.getDutyDate(),
                    dutyTablePro.getDutyType(),
                    dutyTablePro.getDutyModule(),
                    dutyTablePro.getPrecinctId()
            );
            if (list.size()>0){
                for (int i = 0; i< list.size(); i++){
                    PersonRole personRole = list.get(i);
                    DutyTable dutyTable = new DutyTable();
                    dutyTable.setDutyDate(dutyTablePro.getDutyDate());
                    dutyTable.setDutyType(dutyTablePro.getDutyType());
                    dutyTable.setDutyModule(dutyTablePro.getDutyModule());
                    dutyTable.setDutyPerson(personRole.getPersonId());
                    dutyTable.setDutyRole(personRole.getRoleId());
                    dutyTable.setPrecinctId(dutyTablePro.getPrecinctId());
                    addList.add(dutyTable);
                }
                if (addList.size()>0){
                    int j = dutyManageDao.insertDutyInfo(addList);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
        return 0;
    }

    @Override
    public Map<String, String> queryPersonFroMap(String precinctId) {
        return dutyManageDao.queryPersonFroMap(precinctId);
    }

    @Override
    public Map<String, String> queryModuleFroMap(String dutyType,String precinctId) {
        return dutyManageDao.queryModuleFroMap(dutyType,precinctId);
    }

    @Override
    public Map<String, String> queryRoleFroMap(String precinctId) {
        return dutyManageDao.queryRoleFroMap(precinctId);
    }

    @Override
    public int insertDutyInfo(List<DutyTable> addList) {
        return dutyManageDao.insertDutyInfo(addList);
    }

    @Override
    public List<Map<String, Object>> queryDutyPersonByDate(String dutyDate, String dutyType, String moduleId, String precinctId) {
        return dutyManageDao.queryDutyPersonByDate(dutyDate,dutyType,moduleId,precinctId);
    }

    @Override
    public List<Map<String, Object>> queryDutyModuleByTime(long id,String moduleTypes,String dutyType,String startTime, String endTime, String isEndToday,String precinctId) {
        return dutyManageDao.queryDutyModuleByTime(id,moduleTypes,dutyType,startTime,endTime,isEndToday, precinctId);
    }

    @Override
    public List<Map<String, Object>> queryData(String precinctId, String personId, String dutyDate) {
        return dutyManageDao.queryData(precinctId,personId,dutyDate);
    }

    @Override
    public String queryModulName(String dutyModule) {
        return dutyManageDao.queryModulName(dutyModule);
    }

    @Override
    public List<Map<String, Object>> queryDutyInfoById(String precinctId, String dutyDate, String dutyModule, String dutyPerson) {
        return dutyManageDao.queryDutyInfoById(precinctId,dutyDate,dutyModule,dutyPerson);
    }

}
