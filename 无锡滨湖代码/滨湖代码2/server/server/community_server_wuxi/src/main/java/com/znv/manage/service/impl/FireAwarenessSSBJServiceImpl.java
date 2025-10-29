package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncSSBJ;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FireAwarenessSSBJDao;
import com.znv.manage.service.FireAwarenessSSBJService;
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
public class FireAwarenessSSBJServiceImpl implements FireAwarenessSSBJService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessSSBJServiceImpl.class);

    @Autowired
    FireAwarenessSSBJDao fireAwarenessSyncDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/t_real_time_alarm";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncSSBJData() {
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
                List<FireAwarenessSyncSSBJ> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncSSBJ.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertSSBJData(list);
                }else {
                    logger.info("insertSSBJData list.size为0");
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


    public int insertSSBJData(List<FireAwarenessSyncSSBJ> list) {
        int i = 0;
        List<FireAwarenessSyncSSBJ> resultList = new ArrayList();
        for(FireAwarenessSyncSSBJ fireAwarenessSyncSSBJ : list){
            if(!queryExist(fireAwarenessSyncSSBJ)){
                resultList.add(fireAwarenessSyncSSBJ);
            }
        }
        if (resultList.size() != 0) {
            i += this.fireAwarenessSyncDao.insertSSBJData(resultList);
        }
        return i;
    }

    public boolean queryExist(FireAwarenessSyncSSBJ fireAwarenessSyncSSBJ) {
        boolean flag = false;
        int count = fireAwarenessSyncDao.queryExist(fireAwarenessSyncSSBJ);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }


}
