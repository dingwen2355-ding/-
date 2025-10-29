package com.znv.manage.service.impl;

import com.znv.manage.common.bean.EmergencyPlan;
import com.znv.manage.common.bean.Plan;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.EmergencyPlanMapper;
import com.znv.manage.service.EmergencyPlanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EmergencyPlanServiceImpl implements EmergencyPlanService {

    @Value("${dirPath:/mnt/data/files/}")
    private String dirPath;
    @Value("${fileUrl:http://180.167.126.151:8029/files/}")
    private String fileUrl;

    @Autowired
    EmergencyPlanMapper emergencyPlanMapper;

    @Override
    public Result getEmergencyInfo(String eventId) {
        Result result = new Result();
        try {
            result.setData(emergencyPlanMapper.getEmergencyInfo(eventId));
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateEmergencyInfo(EmergencyPlan emergencyPlan) {

        Result result = new Result();

        if (StringUtils.isEmpty(emergencyPlan.getId())) {
            result.setResutCode(ResultCodeEnum.NOTACCEPTABLE.getCode(), ResultCodeEnum.NOTACCEPTABLE.getName());
            result.setData("Id is null");
        } else {
            try {
                emergencyPlanMapper.updateEmergencyById(emergencyPlan);
                result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.toString());
                result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
                result.setData(e.toString());
            }
        }
        return result;
    }

    @Override
    public Result insertEmergencyInfo(EmergencyPlan emergencyPlan) {
        Result result = new Result();

        try {
            emergencyPlanMapper.insertEmergencyInfo(emergencyPlan);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteEmergencyInfo(String emergencyPlanIds) {
        Result result = new Result();

        try {
            emergencyPlanMapper.deleteEmergencyInfo(emergencyPlanIds);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }

        return result;
    }

    @Override
    public Result getEmergencyInfoNew(String eventId, String keyWords, String emIds, String precinctId, String typeName, String typeId) {
        Result result = new Result();
        try {
//            result.setData(emergencyPlanMapper.getEmergencyInfoNew(eventId));

            result.setData(emergencyPlanMapper.queryEmergencyInfoNew(eventId, keyWords, emIds, precinctId, typeName, typeId));

            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryEmergencyInfo(String eventId) {
        Result result = new Result();
        try {
            Map<String, Object> map = emergencyPlanMapper.queryEmergencyInfo(eventId);
            List<Map<String, String>> planList = null;
            String typeName = (String) map.get("typeName");
            if (StringUtils.isNotBlank(typeName)) {
                planList = emergencyPlanMapper.querySimilarPlan(typeName, (String) map.get("planName"));
            }
            map.put("similarPlan", planList);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateEmergencyPlanCfg(String eventId, String planId, String planLevel, String planStatus, Date startTime, Date endTime, String operator, String startPerson) {
        Result result = new Result();
        try {
            emergencyPlanMapper.updateEmergencyPlanCfg(eventId, planId, planLevel, planStatus, startTime, endTime, operator, startPerson);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public List<Plan> queryPlanList(String name, String precinctId, String typeName, String typeId) {
        return emergencyPlanMapper.queryPlanList(name, precinctId, null, typeName, typeId);
    }

    @Override
    public Result deletePlanByIds(String ids) {
        Result result = new Result();
        try {
            int count = emergencyPlanMapper.deletePlanByIds(ids);
            result.setMessage("delete count:" + count);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result addPlan(MultipartFile file, String planName, String planTypeId, String precinctId) {
        Result result = new Result();
        String originalFileName = "";
        if (null != file) {
            originalFileName = file.getOriginalFilename();

            File parentDir = new File(dirPath);
            // 如果那个目录不存在先创建目录
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            String planId = UUID.randomUUID().toString();
            File newFile = new File(dirPath + originalFileName);
            String url = fileUrl + originalFileName;
            try {
                file.transferTo(newFile);
                Plan plan = new Plan();
                plan.setId(planId);
                plan.setPlanFile(url);
                plan.setPlanName(planName);
                plan.setPrecinctId(precinctId);
                plan.setTypeId(planTypeId);
                int count = emergencyPlanMapper.savePlan(plan);
                result.setMessage("upload success count:" + count);
            } catch (IOException e) {
                result.setCode(1);
                result.setMessage(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public Result updatePlan(MultipartFile file, String planName, String planTypeId, String planId, String precinctId) {
        Result result = new Result();
        try {
            List<Plan> list = emergencyPlanMapper.queryPlanList(null, null, planId, null, null);
            if (!CollectionUtils.isEmpty(list)) {
                Plan plan = list.get(0);
                plan.setPlanName(planName);
                plan.setTypeId(planTypeId);
                plan.setPrecinctId(precinctId);
                if (null != file) {
                    String originalFileName = "";
                    originalFileName = file.getOriginalFilename();
                    File parentDir = new File(dirPath);
                    // 如果那个目录不存在先创建目录
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                    File newFile = new File(dirPath + originalFileName);
                    String url = fileUrl + originalFileName;
                    file.transferTo(newFile);
                    plan.setPlanFile(url);
                }
                int count = emergencyPlanMapper.savePlan(plan);
                result.setMessage("upload success count:" + count);
            } else {
                result.setCode(500);
                result.setMessage("预案不存在");
            }
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Map<String, String>> queryPlanTypeList() {
        return emergencyPlanMapper.queryPlanTypeList();
    }
}
