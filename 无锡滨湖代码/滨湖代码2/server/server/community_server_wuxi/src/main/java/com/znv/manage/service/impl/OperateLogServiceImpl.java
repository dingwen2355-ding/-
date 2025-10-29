package com.znv.manage.service.impl;

import com.znv.manage.common.bean.OperateLog;
import com.znv.manage.common.bean.Result;
import com.znv.manage.dao.OperateLogDao;
import com.znv.manage.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogDao operateLogDao;


    @Override
    public Result saveLog(OperateLog operateLog) {
        Result result = new Result();
        try {
            operateLogDao.saveLog(operateLog);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result queryOperatelogList(String operatorName,String precinctId, Date beginTime, Date endTime, Integer pageStart, Integer pageLen) {
        Result result = new Result();
        try {
            if("510100".equals(precinctId)){
                precinctId = "";
            }
            List<OperateLog> list1 = operateLogDao.queryOperatelogList(operatorName,precinctId ,beginTime,endTime,null,null);
            Integer start = 0;
            start = pageLen * (pageStart - 1);
            List<OperateLog> list2 = operateLogDao.queryOperatelogList(operatorName,precinctId, beginTime,endTime,start,pageLen);
            Map<String,Object> map = new HashMap<>();
            map.put("count", CollectionUtils.isEmpty(list1)?0:list1.size());
            map.put("list",list2);
            result.setData(map);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

}
