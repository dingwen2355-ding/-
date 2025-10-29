package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Znsbgjsj;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.ZnsbgjsjDao;
import com.znv.manage.service.ZnsbgjsjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ZnsbgjsjServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/3 13:53
 **/
@Slf4j
@Service
public class ZnsbgjsjServiceImpl implements ZnsbgjsjService {

    @Autowired
    private ZnsbgjsjDao znsbgjsjDao;

    @Override
    public Result getZnsbgjsjStatistics(Date beginTime, Date endTime) {
        Result result = new Result();

        try {
            List list = znsbgjsjDao.ZnsbgjsjTodayList(beginTime,endTime);
            Integer count = znsbgjsjDao.countZnsbgjsjToday(beginTime,endTime);
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("count",count);
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
    public int getData() {
        //设置请求参数
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                HashMap<String, String> params = new HashMap<>();
                params.put("startdate", DateUtil.getStringDateShort());
//                params.put("startdate", " ");
                params.put("pageSize",Integer.toString(pageSize));
                params.put("pageNumber",Integer.toString(pageNumber));
                String url = "http://10.214.111.238:9090/service/api/cjj/t_intel_ligent_data";
                Object result = AccessUtil.get(url, params);
                log.info("智慧工地-智能识别告警数据接口返回："+result);
                //返回结果为unicode编码，需要特殊处理
                List<Znsbgjsj> list = JSON.parseArray(String.valueOf(result), Znsbgjsj.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertZnsbgjsjList(list);
                }
                if (list.size()<pageSize){
                    flag = false;
                }
            }while (flag);
        } catch (Exception e) {
            log.error("getData result error ： " + e.getMessage());
        }
        return i;
    }

    public int insertZnsbgjsjList(List<Znsbgjsj> insertZnsbgjsjList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertZnsbgjsjList) || (insertZnsbgjsjList.size() == 0)){
                log.info("insertZnsbgjsjList list.size为0");
            }
            else {
//                //获取数据库中已有数据的alarmNo
//                List<HashMap<String, Object>> hashMaps = znsbgjsjDao.queryAlarmNo();
//                //根据alarmNo去重
//                for (HashMap map:hashMaps) {
//                    String alarmNo = (String) map.get("alarmNo");
//
//                    for (int j = 0; j < insertZnsbgjsjList.size(); j++) {
//                        if (alarmNo.equals(insertZnsbgjsjList.get(j).getAlarmNo())){
//                            insertZnsbgjsjList.remove(j);
//                        }
//                    }
//                }
                if (insertZnsbgjsjList.size() != 0) {
                    i += znsbgjsjDao.insertZnsbgjsjList(insertZnsbgjsjList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }
}
