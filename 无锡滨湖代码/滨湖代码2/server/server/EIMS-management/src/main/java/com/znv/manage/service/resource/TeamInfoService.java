package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.RescueTeamInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface TeamInfoService {

    Result insertTeam(RescueTeamInfo rescueTeamInfo);

    Result updateTeam(RescueTeamInfo rescueTeamInfo);

    Result deleteTeam(int id);

    Result batchDeleteTeam(String ids);

    Result queryRescueTeamInfoById(int teamId);

    Result queryRescueTeamInfo(String pointName, String helpType, String helpTypeName, String industry, String area,
                               String teamName, String teamType, String apType, String storageLevel, String specialities, String userId, String userLevel, String planId, String eventId, String isAll, Integer pageStart, Integer pageLen);

    List<Map<String, Object>> queryTeamInfoList(String pointName, String helpType, String helpTypeName, String industry, String gpsx, String gpsy, String area,
                                                String teamName, String teamType, String apType, String storageLevel, String eventId, String specialities);
//                                               String teamName,String teamType,String apType,String storageLevel,String specialities,Integer pageStart,Integer pageLen);

    Result queryTeamInfoCount();

    List<Map<String, Object>> shelterInfo(String gpsx, String gpsy, String area, String name, String shelterCategory);

    List<Map<String, Object>> queryTeamInfoStuff(String id, String companyId, String model, String brand, String stuffName, String stuffType, String helpType);

    Result shelterInfoCount();

    Map<String, Object> emergencyPower(String type);

    Map<String, Map<String, List<Map<String, Object>>>> company();

    List<Map> emergencyPowerDictList();


    List<Map> carTeam(String area, String type);

    Result carTeamCount();

    List<Map> emergencyFloodStuff(String area, String name);

    Result emergencyFloodStuffCount();

    Map floodPerpare();

    Result teamInfoMajorsDict();

    Result teamInfoFullPartTimeDict();

    Result commandSelectTeam(String commandId, Integer page, Integer pageSize);

    Result teamExcel(MultipartFile file);

    Result exportTeamExcel(String ids, String pointName, String helpType, String helpTypeName, String industry, String area,
                           String teamName, String teamType, String apType, String storageLevel, String specialities, String userId, String userLevel, Integer pageStart, Integer pageLen);

    Result queryRescueTeamInfoByIdNew(int teamId);

}
