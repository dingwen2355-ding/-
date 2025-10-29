package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncSBLXZD;
import com.znv.manage.common.bean.FireAwarenessSyncSBXX;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.FireAwarenessSBLXZDDao;
import com.znv.manage.service.FireAwarenessSBLXZDService;
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
public class FireAwarenessSBLXZDServiceImpl implements FireAwarenessSBLXZDService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessSBLXZDServiceImpl.class);

    @Autowired
    FireAwarenessSBLXZDDao fireAwarenessSBLXZDDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/t_device_dictionaries";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncSBLXZDData() {
        int i = 0;
        boolean flag = true;
        try {
            Map<String, String> params = new HashMap<>();
            Object object = AccessUtil.get(IP + url, params);
            List<FireAwarenessSyncSBLXZD> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncSBLXZD.class);
            if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                i += insertSBLXZDData(list);
            } else {
                logger.info("insertSBLXZDData list.size为0");
            }
        } catch (Exception e) {
            logger.error("getData res error ： " + e.getMessage());
        }
        return i;
    }


    public int insertSBLXZDData(List<FireAwarenessSyncSBLXZD> list) {
        int i = 0;
        List<FireAwarenessSyncSBLXZD> resultList = new ArrayList();
        fireAwarenessSBLXZDDao.truncateSBLXZDData();
        for (FireAwarenessSyncSBLXZD fireAwarenessSyncSBLXZD : list) {
                resultList.add(fireAwarenessSyncSBLXZD);
        }
        if (resultList.size() != 0) {
            i += this.fireAwarenessSBLXZDDao.insertSBLXZDData(resultList);
        }
        return i;
    }

    public boolean queryExist(FireAwarenessSyncSBLXZD fireAwarenessSyncSBLXZD) {
        boolean flag = false;
        int count = fireAwarenessSBLXZDDao.queryExist(fireAwarenessSyncSBLXZD);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}
