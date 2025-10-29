package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncJCSJ;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FireAwarenessJCSJDao;
import com.znv.manage.service.FireAwarenessJCSJService;
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
public class FireAwarenessJCSJServiceImpl implements FireAwarenessJCSJService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessJCSJServiceImpl.class);

    @Autowired
    FireAwarenessJCSJDao fireAwarenessJCSJDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/t_real_log";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncJCSJData() {
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
                List<FireAwarenessSyncJCSJ> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncJCSJ.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if(pageNumber == 1){
                        fireAwarenessJCSJDao.deleteJCSJData();
                    }
                    i += insertJCSJData(list);
                }else {
                    logger.info("insertJCSJData list.size为0");
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


    public int insertJCSJData(List<FireAwarenessSyncJCSJ> list) {
        int i = 0;
        List<FireAwarenessSyncJCSJ> resultList = new ArrayList();
        for(FireAwarenessSyncJCSJ fireAwarenessSyncJCSJ : list){
            /*if(!queryExist(fireAwarenessSyncJCSJ)){*/
                resultList.add(fireAwarenessSyncJCSJ);
            /*}*/
        }
        if (resultList.size() != 0) {
            i += this.fireAwarenessJCSJDao.insertJCSJData(resultList);
        }
        return i;
    }

    /*public boolean queryExist(FireAwarenessSyncJCSJ fireAwarenessSyncJCSJ) {
        boolean flag = false;
        int count = fireAwarenessJCSJDao.queryExist(fireAwarenessSyncJCSJ);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }*/


}
