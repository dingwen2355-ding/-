package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.Fwgdb;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Szxxb;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FwgdbDao;
import com.znv.manage.dao.SzxxbDao;
import com.znv.manage.service.SzxxbService;
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

@Service
@Slf4j
public class SzxxbServiceImpl implements SzxxbService {
    private static Logger logger = LoggerFactory.getLogger(SzxxbServiceImpl.class);

    @Autowired
    private SzxxbDao szxxbDao;

    @Override
    public Result getSzxxbStatistics(Date beginTime, Date endTime) {
        Result result = new Result();

        try {
            Integer count = szxxbDao.countDeathNumToday(beginTime,endTime);
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
                String url = "http://10.214.111.238:9090/service/api/zhb/t_bz_dead_info";
                Object result = AccessUtil.get(url, params);
//                log.info("逝者信息表接口返回："+result);
                //返回结果为unicode编码，需要特殊处理
                List<Szxxb> list = JSON.parseArray(String.valueOf(result), Szxxb.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertSzxxbList(list);
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


    public int insertSzxxbList(List<Szxxb> insertSzxxbList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertSzxxbList) || (insertSzxxbList.size() == 0)){
                log.info("insertSzxxbList list.size为0");
            }
            else {
//                //获取数据库中已有数据的id_card_number
//                List<HashMap<String, String>> hashMaps = szxxbDao.querySzxxbAllIdCard();
//                //根据id_card_number去重
//                for (HashMap map:hashMaps) {
//                    String idCardNumber = (String) map.get("id_card_number");
//                    for (int j = 0; j < insertSzxxbList.size(); j++) {
//                        if (idCardNumber.equals(insertSzxxbList.get(j).getIdCardNumber())){
//                            insertSzxxbList.remove(j);
//                        }
//                    }
//                }
                if (insertSzxxbList.size() != 0) {
                    i += szxxbDao.insertSzxxbList(insertSzxxbList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }
}
