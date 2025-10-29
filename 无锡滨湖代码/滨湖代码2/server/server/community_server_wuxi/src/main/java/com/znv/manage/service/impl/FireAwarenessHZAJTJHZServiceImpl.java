package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncHZAJTJHZ;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FireAwarenessHZAJTJHZDao;
import com.znv.manage.service.FireAwarenessHZAJTJHZService;
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
public class FireAwarenessHZAJTJHZServiceImpl implements FireAwarenessHZAJTJHZService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessHZAJTJHZServiceImpl.class);

    @Autowired
    FireAwarenessHZAJTJHZDao fireAwarenessHZAJTJHZDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/xls_hzajtjhz";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncHZAJTJHZData() {
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
                List<FireAwarenessSyncHZAJTJHZ> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncHZAJTJHZ.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if(pageNumber == 1){
                        fireAwarenessHZAJTJHZDao.deleteHZAJTJHZData();
                    }
                    i += insertHZAJTJHZData(list);
                }else {
                    logger.info("insertHZAJTJHZData list.size为0");
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


    public int insertHZAJTJHZData(List<FireAwarenessSyncHZAJTJHZ> list) {
        int i = 0;
        List<FireAwarenessSyncHZAJTJHZ> resultList = new ArrayList();
        for(FireAwarenessSyncHZAJTJHZ fireAwarenessSyncHZAJTJHZ : list){
            /*if(!queryExist(fireAwarenessSyncHZAJTJHZ)){*/
                resultList.add(fireAwarenessSyncHZAJTJHZ);
            /*}*/
        }
        if (resultList.size() != 0) {
            i += this.fireAwarenessHZAJTJHZDao.insertHZAJTJHZData(resultList);
        }
        return i;
    }

    /*public boolean queryExist(FireAwarenessSyncHZAJTJHZ fireAwarenessSyncHZAJTJHZ) {
        boolean flag = false;
        int count = fireAwarenessHZAJTJHZDao.queryExist(fireAwarenessSyncHZAJTJHZ);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }*/


}
