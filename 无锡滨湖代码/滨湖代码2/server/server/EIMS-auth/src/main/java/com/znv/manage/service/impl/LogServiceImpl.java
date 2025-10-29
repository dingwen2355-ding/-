package com.znv.manage.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.PoiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.znv.manage.bean.user.User;
import com.znv.manage.dao.LogServiceMapper;
import com.znv.manage.dao.UserServiceMapper;
import com.znv.manage.service.LogService;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

    @Value("${excelPath}")
    private String filePath;

    @Value("${excelUrl}")
    private String fileUrl;

    @Resource
    LogServiceMapper logServiceMapper;
    
    @Resource
    UserServiceMapper userServiceMapper;

    @Override
    public List<Map<String, Object>> queryLog(String id,
                                              String operator,
                                              String operUserId,
                                              String operUserName,
                                              String ip,
                                              Date time,
                                              String startTime,
                                              String endTime,
                                              String operatedUserId,
                                              String operatedUserName,
                                              String result,
                                              String detail,
                                              String userId) {
    	List<User> list = userServiceMapper.queryUsers(userId,null,null,null, null, null,
    			null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,
                null,null,null, null);
    	String roleId = list.get(0).getRoleId();
    	String userName = list.get(0).getUserName();
        List<String> userNameList = null;
		if ("2".equals(roleId)) {
			userNameList = userServiceMapper.querySameStreetUser(userId);
		} else if ("4".equals(roleId)) {
			userNameList = userServiceMapper.queryBelongToAreaUser(userId);
			userNameList.add(userName);
		} else if ("1".equals(roleId)){
			userNameList = null;
		} else {
			userNameList = new ArrayList();
			userNameList.add(userName);
		}
        List<Map<String, Object>> maps = logServiceMapper.queryLog(id, operator, operUserId, operUserName,
                ip, time, startTime, endTime, operatedUserId, operatedUserName, result, detail, userNameList);
        log.info(maps.toString());
        return maps;
    }

    @Override
    public void insertLog(String operator,
                          String operUserId,
                          String operUserName,
                          String ip,
                          Date time,
                          String operatedUserId,
                          String operatedUserName,
                          String result,
                          String detail) {
        logServiceMapper.insertLog(operator,operUserId,operUserName,ip,time,operatedUserId,operatedUserName,result,detail);
    }

    public void multiInsertLog(
            String operator,
            String operUserId,
            String operUserName,
            String ip,
            Date time,
            List<User> users,
            String result,
            String detail
    ) {
        logServiceMapper.multiInsertLog(operator,operUserId,operUserName,ip,time,users,result,detail);
    }

    @Override
    public Result queryLogKinds() {
        Result result = new Result();
        try {
            List<Map<String, String>> list = logServiceMapper.queryLogKinds();
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
            result.setData(list);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result exportLog(String operUserName, String startTime, String endTime, String operator) {
        Result result = new Result();
        try {
            List<Map<String, Object>> maps = logServiceMapper.queryLogExport(operUserName, startTime, endTime, operator);
            int i = 1;
            for (Map<String, Object> map : maps) {
                map.put("id", i);
                i++; // 序号
            }
            StringBuilder keyStr = new StringBuilder(200);
            StringBuilder columnNameStr = new StringBuilder(200);
            String sheetName = "日志管理";
            keyStr.append("id,operUserId,operUsername,ip,operator,detail,operatedUserid,operatedUsername,result,time");
            columnNameStr.append("序号,用户ID,用户名,用户IP,操作类型,内容,操作对象ID,操作对象用户名,结果,操作时间");
            String title = ""; // 不要第一行的tile
            String[] keys = keyStr.toString().split(",");
            String[] columnNames = columnNameStr.toString().split(",");
            String[] mergeColumnNames = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String reFileName = sheetName + simpleDateFormat.format(new Date()) + ".xlsx";
            PoiUtils.createWorkBook(maps, keys, columnNames, sheetName, mergeColumnNames, title, filePath + reFileName);

            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
            result.setData(fileUrl + reFileName);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }



}
