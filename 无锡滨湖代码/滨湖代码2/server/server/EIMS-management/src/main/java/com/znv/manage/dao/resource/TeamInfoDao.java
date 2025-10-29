package com.znv.manage.dao.resource;


import com.znv.manage.common.bean.resource.RescueTeamInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TeamInfoDao {

    int insertTeamInfo(RescueTeamInfo rescueTeamInfo);

    int updateTeamInfo(RescueTeamInfo rescueTeamInfo);

    int deleteTeamInfo(int id);

    int batchDeleteTeamInfo(@Param("ids") String ids);

    List<RescueTeamInfo> queryRescueTeamInfoById(int id);

    Map<String,Object> queryRescueTeamInfoByIdNew(@Param("id") int id);

    List<Map<String, Object>> queryRescueTeamInfo(@Param("pointName") String pointName,
                                                  @Param("helpType") String helpType,
                                                  @Param("helpTypeName") List<String> helpTypeName,
                                                  @Param("industry") String industry,
                                                  @Param("area") String area,
                                                  @Param("teamName") String teamName,
                                                  @Param("teamType") String teamType,
                                                  @Param("apType") String apType,
                                                  @Param("storageLevel") String storageLevel,
                                                  @Param("specialities") String specialities,
                                                  @Param("userId") String userId,
                                                  @Param("planId") String planId,
                                                  @Param("eventId") String eventId,
                                                  @Param("start") Integer start,
                                                  @Param("pageLen") Integer pageLen);

    int queryBasicInfoCount(@Param("area") String area,
                            @Param("teamName") String teamName,
                            @Param("teamType") String teamType,
                            @Param("storageLevel") String storageLevel,
                            @Param("apType") String apType,
                            @Param("helpTypeName") List<String> helpTypeName,
                            @Param("industry") String industry,
                            @Param("specialities") String specialities);

    List<Map<String, Object>> queryTeamInfoList(@Param("pointName") String pointName,
                                                @Param("helpType") String helpType,
                                                @Param("helpTypeName") String helpTypeName,
                                                @Param("industry") String industry,
                                                @Param("area") String area,
                                                @Param("teamName") String teamName,
                                                @Param("teamType") String teamType,
                                                @Param("apType") String apType,
                                                @Param("storageLevel") String storageLevel,
                                                @Param("specialities") String specialities,
                                                @Param("eventId") String eventId);
//                                               @Param("specialities") String specialities,
//                                               @Param("start")Integer start,
//                                               @Param("pageLen")Integer pageLen);

    List<Map<String, Object>> queryTeamInfoCountXQ();

    List<Map<String, Object>> queryTeamInfoCountGLLX();

    List<Map<String, Object>> queryTeamInfoStuff(@Param("id") String id,
                                                 @Param("companyId") String companyId,
                                                 @Param("model") String model,
                                                 @Param("brand") String brand,
                                                 @Param("stuffName") String stuffName,
                                                 @Param("stuffType") String stuffType,
                                                 @Param("helpType") String helpType);

    List<Map<String, Object>> queryTeamInfoCountSSHY();

    List<Map<String, Object>> queryTeamInfoCountJYLX();

    List<Map<String, Object>> carTeamCountXQ();

    List<Map<String, Object>> carTeamCountLX();

    List<Map<String, Object>> shelterInfo(@Param("area") String area, @Param("name") String name,
                                          @Param("shelterCategory") String shelterCategory);

    List<Map<String, Object>> shelterInfoCountXQ();

    List<Map<String, Object>> emergencyPower(@Param("type") String type);

    List<Map<String, Object>> companyInfo();

    List<Map> queryEmergencyPowerDictList();

    List<Map<String, String>> queryCarTeamInfo(@Param("areaName") String areaName, @Param("carType") String carType);

    List<Map<String, String>> queryCarTeamManager(@Param("type") String type);

    List<Map<String, String>> queryFloodStuffInfo(@Param("areaName") String areaName, @Param("stuffName") String stuffName);

    List<Map<String, String>> queryFloodPerpareStuff();

    List<Map<String, Object>> teamInfoMajorsDict();

    List<Map<String, Object>> teamInfoFullPartTimeDict();

    String selectCommandTeamInfo(@Param("commandId") String commandId);

    int updateCommandTeamId(@Param("commandId") String commandId,
                            @Param("teamIds") String teamIds);

    List<Map<String, Object>> getTeamInfoByCommandId(@Param("commandId") String commandId);

    String getTeamNameByIds(@Param("ids") String ids);

    Integer getTag(@Param("name") String name, @Param("id") String id);

    String  getPrecinct(@Param("name") String name);

    Integer getBelongTag(@Param("name") String name);

    String getTeamIdByName(@Param("name") String name);

    List<Map<String, Object>> queryRescueTeamInfoExport(@Param("ids") String ids,
                                                  @Param("pointName") String pointName,
                                                  @Param("helpType") String helpType,
                                                  @Param("helpTypeName") String helpTypeName,
                                                  @Param("industry") String industry,
                                                  @Param("area") String area,
                                                  @Param("teamName") String teamName,
                                                  @Param("teamType") String teamType,
                                                  @Param("apType") String apType,
                                                  @Param("storageLevel") String storageLevel,
                                                  @Param("specialities") String specialities,
                                                  @Param("userId") String userId,
                                                  @Param("start") Integer start,
                                                  @Param("pageLen") Integer pageLen);
}
