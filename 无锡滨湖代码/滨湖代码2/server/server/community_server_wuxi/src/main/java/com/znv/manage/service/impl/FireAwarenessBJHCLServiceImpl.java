package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.FireAwarenessSyncBJHCL;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.FireAwarenessBJHCLDao;
import com.znv.manage.service.FireAwarenessBJHCLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class FireAwarenessBJHCLServiceImpl implements FireAwarenessBJHCLService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(FireAwarenessBJHCLServiceImpl.class);

    @Autowired
    FireAwarenessBJHCLDao fireAwarenessBJHCLDao;

    @Value("${url}")
    private String IP;

    private static String url = "/service/api/xfdd/t_realwork_deviceinfo";

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncBJHCLData() {
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
                List<FireAwarenessSyncBJHCL> list = JSON.parseArray(String.valueOf(object), FireAwarenessSyncBJHCL.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if(pageNumber == 1){
                        fireAwarenessBJHCLDao.deleteBJHCLData();
                    }
                    i += insertSSBJHCLData(list);
                }else {
                    logger.info("insertBJHCLData list.size为0");
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


    public int insertSSBJHCLData(List<FireAwarenessSyncBJHCL> list) {
        int i = 0;
        List<FireAwarenessSyncBJHCL> resultList = new ArrayList();
        for(FireAwarenessSyncBJHCL fireAwarenessSyncBJHCL : list){
            /*if(!queryExist(fireAwarenessSyncBJHCL)){*/
                resultList.add(fireAwarenessSyncBJHCL);
           /* }*/
        }
        if (resultList.size() != 0) {
            i += this.fireAwarenessBJHCLDao.insertBJHCLData(resultList);
        }
        return i;
    }

    /*public boolean queryExist(FireAwarenessSyncBJHCL fireAwarenessSyncBJHCL) {
        boolean flag = false;
        int count = fireAwarenessBJHCLDao.queryExist(fireAwarenessSyncBJHCL);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }*/

}
