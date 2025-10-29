package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.service.CommandDepartmentService;
import com.znv.manage.dao.CommandDepartmentDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * TCfgCommandDepartment表服务实现类
 *
 * @author 0049003788
 * @since 2022-11-28 11:18:21
 */
@Service
@Slf4j
public class CommandDepartmentServiceImpl implements CommandDepartmentService {
    @Resource
    private CommandDepartmentDao commandDepartmentDao;

    @Override
    public Result queryCommand() {
        Result result = new Result();
        try {
            List<Map<String, Object>> commandList = commandDepartmentDao.queryCommand();
            int i = 0;
            for (Map<String, Object> map : commandList) {
                map.put("id", i++);
            }
            result.setData(commandList);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryOrg(String commandName) {
        Result result = new Result();
        try {
            JSONObject commandJson = new JSONObject();
            List<Map<String, Object>> list = commandDepartmentDao.queryOrg(commandName);
            JSONArray orgArray = new JSONArray();
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> map : list) {
                    JSONObject orgJson = new JSONObject();
                    orgJson.put("id", "");
                    orgJson.put("label", map.get("label"));
                    orgJson.put("content", map.get("content"));
                    orgJson.put("dataSource", map.get("dataSource"));
                    JSONArray unitArray = new JSONArray();

                    String leadUnit = String.valueOf(map.get("leadUnit"));
                    String memberUnit = String.valueOf(map.get("memberUnit"));
                    if (StringUtils.isNotBlank(leadUnit)) {
                        if (memberUnit == null || !memberUnit.contains(leadUnit)) {
                            JSONObject unitJson = new JSONObject();
                            unitJson.put("id", "");
                            unitJson.put("name", "");
                            unitJson.put("position", map.get("leadUnit").toString());
                            unitJson.put("phone", "");
                            unitJson.put("duty", "");
                            unitArray.add(unitJson);
                        }
                    }
                    if (StringUtils.isNotBlank(memberUnit)) {
                        String[] unitList = memberUnit.split("、");
                        for (String unit : unitList) {
                            JSONObject unitJson = new JSONObject();
                            unitJson.put("id", "");
                            unitJson.put("name", "");
                            unitJson.put("position", unit);
                            unitJson.put("phone", "");
                            unitJson.put("duty", "");
                            unitArray.add(unitJson);
                        }
                    }
                    orgJson.put("list", unitArray);
                    orgArray.add(orgJson);
                }
            }
            commandJson.put("id", 0);
            commandJson.put("label", commandName);
            commandJson.put("children", orgArray);
            result.setData(commandJson);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
