package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.AIsxt;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.SpAIgjxx;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.SpAIgjxxDao;
import com.znv.manage.service.SpAIgjxxService;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: SpAIgjxxServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/2 16:06
 **/
@Slf4j
@Service
public class SpAIgjxxServiceImpl implements SpAIgjxxService {

    private static Logger logger = LoggerFactory.getLogger(SpAIgjxxServiceImpl.class);

    private static String url = "http://10.214.111.238:9090/service/api/zhb/t_camera_alarm";
    private static String authorization = "BasiceXd0Zzp5d3Rn";
    private static RestTemplate restTemplate = new RestTemplate();
    private static String tokenUrl = "http://10.214.111.238:9090/oauth/token?client_id=zzdld&client_secret=zzdld";
    private static String urlPrefix = "?client_id=zzdld&access_token=";

    @Autowired
    private SpAIgjxxDao spAIgjxxDao;

    @Override
    public Result getSpAIgjxxStatistics(Date beginTime, Date endTime) {
        Result result = new Result();

        try {
            List list = spAIgjxxDao.SpAIgjxxTodayList(beginTime,endTime);
            Integer count = spAIgjxxDao.countSpAIgjxxToday(beginTime,endTime);
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("count",count);
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getSpAIyjStatistics(Date beginTime, Date endTime) {
        Result result = new Result();

        try {
            List list = spAIgjxxDao.SpAIyjTodayList(beginTime,endTime);
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
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
    public Result SpAIgjxxTop5Today(Date beginTime, Date endTime) {
        Result result = new Result();

        try {
            List list = spAIgjxxDao.SpAIgjxxTop5Today(beginTime,endTime);
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
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

    @Override
    public int getSpAIgjxxData() {
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                HashMap<String, String> params = new HashMap<>();
                params.put("pageSize", Integer.toString(pageSize));
                params.put("pageNumber", Integer.toString(pageNumber));
//                params.put("start_time", " ");
                params.put("start_time", DateUtil.getStringDateShort());
                String newUrl = url + urlPrefix + getAccessToken();

                StringBuilder stringBuilderUrl = new StringBuilder();
                if (params != null && params.size() > 0) {
                    for (String key : params.keySet()) {
                        stringBuilderUrl.append("&");
                        stringBuilderUrl.append(key);
                        stringBuilderUrl.append("=");
                        stringBuilderUrl.append(params.get(key));
                    }
                    newUrl = newUrl + stringBuilderUrl;
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization",authorization);
                ResponseEntity<String> res = restTemplate.exchange(newUrl, HttpMethod.GET, new HttpEntity<String>(headers),String.class);
                String result = res.getBody();
//                log.info("视频AI告警信息接口返回：" + result);
                //返回结果为unicode编码，需要特殊处理
                List<SpAIgjxx> list = JSON.parseArray(String.valueOf(result), SpAIgjxx.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertSpAIgjxxList(list);
                }
                if (list.size() < pageSize) {
                    flag = false;
                }
            }while (flag);
        } catch (Exception e) {
            log.error("getData result error ： " + e.getMessage());
        }
        return i;
    }

    @Override
    public int getAIsxtData() {

        int i = 0;
        try {
            HashMap<String, String> params = new HashMap<>();
            String url = "http://10.214.111.238:9090/service/api/zhb/basicinformation";
            Object result = AccessUtil.get(url, params);
//            log.info("AI摄像头信息接口返回："+result);
            //返回结果为unicode编码，需要特殊处理
            List<AIsxt> list = JSON.parseArray(String.valueOf(result), AIsxt.class);
            //向数据库插入数据
            if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                i += insertAIsxtList(list);
            }
        } catch (Exception e) {
            log.error("getData result error ： " + e.getMessage());
        }
        return i;
    }

    public int insertSpAIgjxxList(List<SpAIgjxx> insertSpAIgjxxList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertSpAIgjxxList) || (insertSpAIgjxxList.size() == 0)){
                log.info("insertSpAIgjxx list.size为0");
            }
            else {
//                //获取数据库中已有数据的alarmId
//                List<HashMap<String, Object>> hashMaps = spAIgjxxDao.querySpAIgjxxAlarmIdList();
//                //根据alarmId去重
//                for (HashMap map:hashMaps) {
//                    String alarmId = (String) map.get("alarmId");
//                    for (int j = 0; j < insertSpAIgjxxList.size(); j++) {
//                        if (alarmId.equals(insertSpAIgjxxList.get(j).getAlarmId())){
//                            insertSpAIgjxxList.remove(j);
//                        }
//                    }
//                }
                if (insertSpAIgjxxList.size() != 0) {
                    i += spAIgjxxDao.insertSpAIgjxxList(insertSpAIgjxxList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public int insertAIsxtList(List<AIsxt> insertAIsxtList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertAIsxtList) || (insertAIsxtList.size() == 0)){
                log.info("insertSpAIgjxx list.size为0");
            }
            else {
//                //获取数据库中已有数据的Id
//                List<HashMap<String, Object>> hashMaps = spAIgjxxDao.queryAIsxtIdList();
//                //根据id去重
//                for (HashMap map:hashMaps) {
//                    Integer id = (Integer) map.get("id");
//                    for (int j = 0; j < insertAIsxtList.size(); j++) {
//                        if (id.equals(insertAIsxtList.get(j).getId())){
//                            insertAIsxtList.remove(j);
//                        }
//                    }
//                }
                if (insertAIsxtList.size() != 0) {
                    i += spAIgjxxDao.insertAIsxtList(insertAIsxtList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }
}
