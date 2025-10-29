package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.HospitalSyncJZGH;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.HospitalJZGHDao;
import com.znv.manage.service.HospitalJZGHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

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
public class HospitalJZGHServiceImpl implements HospitalJZGHService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(HospitalJZGHServiceImpl.class);

    private static String url = "http://10.214.111.238:9090/service/api/wjw/registerrecord";
    private static String authorization = "BasiceXd0Zzp5d3Rn";
    private static RestTemplate restTemplate = new RestTemplate();
    private static String tokenUrl = "http://10.214.111.238:9090/oauth/token?client_id=zkywlw&client_secret=zkywlw";
    private static String urlPrefix = "?client_id=zkywlw&access_token=";

    @Autowired
    HospitalJZGHDao hospitalJZGHDao;

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncJZGHData() {
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
                params.put("startdate", "");
                String newUrl = url + urlPrefix + getAccessToken();
                StringBuilder stringBuilderUrl = new StringBuilder();
                if (params != null && params.size() > 0) {
                    for (String key : params.keySet()) {
                        stringBuilderUrl.append("&");
                        stringBuilderUrl.append(key);
                        stringBuilderUrl.append("=");
                        stringBuilderUrl.append(params.get(key));
                    }
                    newUrl = newUrl + stringBuilderUrl.toString();
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization",authorization);
                ResponseEntity<String> res = restTemplate.exchange(newUrl, HttpMethod.GET, new HttpEntity<String>(headers),String.class);
                String result = res.getBody();
                List<HospitalSyncJZGH> list = JSON.parseArray(String.valueOf(result), HospitalSyncJZGH.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertSBXXData(list);
                }else {
                    logger.info("insertJZGHData list.size为0");
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


    public int insertSBXXData(List<HospitalSyncJZGH> list) {
        int i = 0;
        List<HospitalSyncJZGH> resultList = new ArrayList();
        for(HospitalSyncJZGH fireAwarenessSyncJZGH : list){
            if(!queryExist(fireAwarenessSyncJZGH)){
                resultList.add(fireAwarenessSyncJZGH);
            }
        }
        if (resultList.size() != 0) {
            i += this.hospitalJZGHDao.insertJZGHData(resultList);
        }
        return i;
    }

    public boolean queryExist(HospitalSyncJZGH fireAwarenessSyncJZGH) {
        boolean flag = false;
        int count = hospitalJZGHDao.queryExist(fireAwarenessSyncJZGH);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    private static String getAccessToken(){
        String accessToken = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",authorization);
            ResponseEntity<String> result = restTemplate.exchange(tokenUrl, HttpMethod.POST, new HttpEntity<String>(headers),String.class);
            JSONObject jsonObject = JSONObject.parseObject(result.getBody());
            accessToken = jsonObject.getString("access_token");
        }catch (Exception e){
            logger.error("accessToken 获取异常："+e.getMessage());
        }
        return accessToken;
    }


}
