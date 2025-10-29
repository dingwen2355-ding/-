package com.znv.manage.dao;

import com.znv.manage.common.bean.EmergencyPlan;
import com.znv.manage.common.bean.Plan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface EmergencyPlanMapper {
    List<Map<String, Object>> getEmergencyInfo(@Param("eventId") String eventId);

    int updateEmergencyById(EmergencyPlan emergencyPlan);

    void insertEmergencyInfo(EmergencyPlan emergencyPlan);

    void deleteEmergencyInfo(@Param("emergencyPlanIds") String emergencyPlanIds);

    List<Map<String, Object>> getEmergencyInfoNew(@Param("eventId") String eventId);

    List<Map<String, Object>> queryEmergencyInfoNew(@Param("eventId") String eventId,
                                                    @Param("keyWords") String keyWords,
                                                    @Param("emIds") String emIds,
                                                    @Param("precinctId") String precinctId,
                                                    @Param("typeName") String typeName,
                                                    @Param("typeId") String typeId);

    Map<String, Object> queryEmergencyInfo(@Param("eventId") String eventId);

    int updateEmergencyPlanCfg(@Param("eventId") String eventId,
                               @Param("planId") String planId,
                               @Param("planLevel") String planLevel,
                               @Param("planStatus") String planStatus,
                               @Param("startTime") Date startTime,
                               @Param("endTime") Date endTime,
                               @Param("operator") String operator,
                               @Param("startPerson") String startPerson);

    List<Plan> queryPlanList(@Param("name") String name,
                             @Param("precinctId") String precinctId,
                             @Param("planId") String planId,
                             @Param("typeName") String typeName,
                             @Param("typeId") String typeId);

    int deletePlanByIds(@Param("ids") String ids);

    int savePlan(Plan plan);

    List<Map<String, String>> queryPlanTypeList();

    List<Map<String, String>> querySimilarPlan(@Param("typeName") String typeName,
                                               @Param("planName") String planName);
}
