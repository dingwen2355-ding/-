package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncBJXX;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FireAwarenessBJXXDao;
import com.znv.manage.service.FireAwarenessBJXXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: 服务层
 * @date 2018/5/18 14:57
 */
@Service
public class FireAwarenessBJXXServiceImpl implements FireAwarenessBJXXService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessBJXXServiceImpl.class);

    @Autowired
    FireAwarenessBJXXDao fireAwarenessBJXXDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/t_realtime_alarm_bydate";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncBJXXData() {
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                Map<String, String> params = new HashMap<>();
                params.put("pageSize", Integer.toString(pageSize));
                params.put("pageNumber", Integer.toString(pageNumber));
                params.put("startdate", DateUtil.getStringDateShort());
                Object object = AccessUtil.get(IP + url, params);
                List<FireAwarenessSyncBJXX> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncBJXX.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if(pageNumber == 1){
                        fireAwarenessBJXXDao.deleteBJXXData();
                    }
                    i += insertBJXXData(list);
                }else {
                    logger.info("insertBJXXData list.size为0");
                }
                if (list.size() < pageSize) {
                    flag = false;
                }
            } while (flag);
        } catch (Exception e) {
            logger.error("getData res error ： " + e.getMessage());
        }
        return i;
    }


    public int insertBJXXData(List<FireAwarenessSyncBJXX> list) {
        int i = 0;
        List<FireAwarenessSyncBJXX> resultList = new ArrayList();
        for(FireAwarenessSyncBJXX fireAwarenessSyncBJXX : list){
            /*if(!queryExist(fireAwarenessSyncBJXX)){*/
                resultList.add(fireAwarenessSyncBJXX);
           /* }*/
        }
        if (resultList.size() != 0) {
            i += this.fireAwarenessBJXXDao.insertBJXXData(resultList);
        }
        return i;
    }

    /*public boolean queryExist(FireAwarenessSyncBJXX fireAwarenessSyncBJXX) {
        boolean flag = false;
        int count = fireAwarenessBJXXDao.queryExist(fireAwarenessSyncBJXX);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }*/


}
