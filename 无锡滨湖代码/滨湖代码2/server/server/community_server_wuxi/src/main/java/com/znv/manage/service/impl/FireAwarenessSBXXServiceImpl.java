package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncSBXX;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.FireAwarenessSBXXDao;
import com.znv.manage.service.FireAwarenessSBXXService;
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
public class FireAwarenessSBXXServiceImpl implements FireAwarenessSBXXService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessSBXXServiceImpl.class);

    @Autowired
    FireAwarenessSBXXDao fireAwarenessSBXXDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/t_getalliot_device";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncSBXXData() {
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
                Object object = AccessUtil.get(IP + url, params);
                List<FireAwarenessSyncSBXX> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncSBXX.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if(pageNumber == 1){
                        fireAwarenessSBXXDao.truncateSBXXData();
                    }
                    i += insertSBXXData(list);
                }else {
                    logger.info("insertSBXXData list.size为0");
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


    public int insertSBXXData(List<FireAwarenessSyncSBXX> list) {
        int i = 0;
        List<FireAwarenessSyncSBXX> resultList = new ArrayList();
        for(FireAwarenessSyncSBXX fireAwarenessSyncSBXX : list){
                resultList.add(fireAwarenessSyncSBXX);
        }
        if (resultList.size() != 0) {
            try {
                i += this.fireAwarenessSBXXDao.insertSBXXData(resultList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    /*public boolean queryExist(FireAwarenessSyncSBXX fireAwarenessSyncSBXX) {
        boolean flag = false;
        int count = fireAwarenessSBXXDao.queryExist(fireAwarenessSyncSBXX);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }*/

}
