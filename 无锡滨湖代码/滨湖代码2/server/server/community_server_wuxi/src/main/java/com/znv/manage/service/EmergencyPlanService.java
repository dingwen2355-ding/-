package com.znv.manage.service;

import com.znv.manage.common.bean.EmergencyPlan;
import com.znv.manage.common.bean.Plan;
import com.znv.manage.common.bean.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EmergencyPlanService {
    Result getEmergencyInfo(String eventId);

    Result updateEmergencyInfo(EmergencyPlan emergencyPlan);


    Result insertEmergencyInfo(EmergencyPlan emergencyPlan);

    Result deleteEmergencyInfo(String emergencyPlanIds);

    Result getEmergencyInfoNew(String eventId, String keyWords, String emIds,String precinctId,String typeName,String typeId);

    Result queryEmergencyInfo(String eventId);

    Result updateEmergencyPlanCfg(String eventId,
                               String planId,
                               String planLevel,
                               String planStatus,
                               Date startTime,
                               Date endTime,
                               String operator,
                                  String startPerson);

    List<Plan> queryPlanList(String name,String precinctId,String typeName,String typeId);

    Result deletePlanByIds(String ids);

    Result addPlan(MultipartFile file,String planName,String planTypeId,String precinctId);

    Result updatePlan(MultipartFile file,String planName,String planTypeId,String planId,String precinctId);

    List<Map<String,String>> queryPlanTypeList();
}
