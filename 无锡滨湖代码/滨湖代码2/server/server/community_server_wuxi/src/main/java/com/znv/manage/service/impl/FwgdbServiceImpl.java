package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.Fwgdb;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FwgdbDao;
import com.znv.manage.service.FwgdbService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FwgdbServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/8 14:15
 **/
@Service
@Slf4j
public class FwgdbServiceImpl implements FwgdbService {
    private static Logger logger = LoggerFactory.getLogger(FwgdbServiceImpl.class);

    @Autowired
    private FwgdbDao fwgdbDao;

    @Override
    public int getData() {
        //设置请求参数
        int pageNumber = 0;
        int pageSize = 500;
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
                String url = "http://10.214.111.238:9090/service/api/mzj/t_ls_service_order";
                Object result = AccessUtil.get(url, params);
//                log.info("服务工单表数据接口返回："+result);
                //返回结果为unicode编码，需要特殊处理
                List<Fwgdb> list = JSON.parseArray(String.valueOf(result), Fwgdb.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertFwgdbList(list);
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

    @Override
    public Result getFwgdbStatistics(Date beginTime, Date endTime) {
        Result result = new Result();

        try {
            Integer count = fwgdbDao.countFwgdbToday(beginTime,endTime);
            Map<String,Object> map = new HashMap<>();
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

    public int insertFwgdbList(List<Fwgdb> insertFwgdbList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertFwgdbList) || (insertFwgdbList.size() == 0)){
                log.info("insertFwgdbList list.size为0");
            }
            else {
//                //获取数据库中已有数据的Id
//                List<HashMap<String, String>> hashMaps = fwgdbDao.queryFwgdbAllId();
//                //根据id去重
//                for (HashMap map:hashMaps) {
//                    String id = (String)map.get("id");
//                    for (int j = 0; j < insertFwgdbList.size(); j++) {
//                        if (id.equals(insertFwgdbList.get(j).getID())){
//                            insertFwgdbList.remove(j);
//                        }
//                    }
//                }
                if (insertFwgdbList.size() != 0) {
                    i += fwgdbDao.insertFwgdbList(insertFwgdbList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }
}
