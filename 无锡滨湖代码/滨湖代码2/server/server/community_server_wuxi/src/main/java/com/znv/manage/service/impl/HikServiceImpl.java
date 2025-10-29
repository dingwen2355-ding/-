package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.HikUtils;
import com.znv.manage.service.HikService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */

@Service
@Slf4j
public class HikServiceImpl implements HikService {

    private RestTemplate restTemplate = new RestTemplate();

//    static {
//        ArtemisConfig.host = "2.14.29.9:443"; // 代理API网关nginx服务器ip端口
//        ArtemisConfig.appKey = "24482199";  // 秘钥appkey
//        ArtemisConfig.appSecret = "P3y5XBaQMXAMQANohqZv";// 秘钥appSecret
//    }

    @Override
    public int updateRtsp(String apiUrl) {
        try{
            String pushContent = getCameraJson().toJSONString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> formEntity = new HttpEntity<>(pushContent, headers);

            JSONObject jsonObj = restTemplate.postForObject(apiUrl, formEntity, JSONObject.class);
            if ("200".equals(jsonObj.getString("code"))) {
                log.info("向VAE推送rtsp链接成功..." + apiUrl);
            } else {
                log.info("向VAE推送rtsp链接失败..." + apiUrl);
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return 0;
    }

    /**
     * {\"code\":\"0\",\"msg\":\"success\",
     * \"data\":{\"total\":4,\"pageSize\":10,\"pageNo\":1,
     * \"list\":[{\"cameraIndexCode\":\"32010411101312941022\",\"name\":\"中山东路中山南路路口枪\",\"unitIndexCode\":\"32010400002160094501\",\"gbIndexCode\":\"320
     * @return
     */
    @Override
    public JSONObject getCameraJson(){
        String string = HikUtils.getCameras(1,5000);
        JSONObject jsonObject = JSONObject.parseObject(string);
        if ("success".equals(jsonObject.getString("msg"))){
            JSONArray cameraList = jsonObject.getJSONObject("data").getJSONArray("list");

            JSONObject cameraListObj = new JSONObject();
            JSONArray cameraListArray = new JSONArray();
            cameraListObj.put("cameraList", cameraListArray);

            for (int i = 0;i<cameraList.size();i++){
                JSONObject tempObj = cameraList.getJSONObject(i);
                JSONObject cameraObj = new JSONObject();

                cameraObj.put("cameraId", tempObj.getString("cameraIndexCode"));
                cameraObj.put("cameraName", tempObj.getString("name"));
                String rtspUrl = HikUtils.getRtsp(tempObj.getString("cameraIndexCode"));
                cameraObj.put("url", rtspUrl);

                cameraListArray.add(cameraObj);
            }
            HikUtils.jsonObject = cameraListObj;
            return cameraListObj;
        }else{
            HikUtils.jsonObject = new JSONObject();
            return null;
        }
    }

    @Override
    public Result getCamera(Integer pageNo,Integer pageSize) {
        String string = HikUtils.getCameras(pageNo,pageSize);
        Result result = new Result();
        result.setData(string);
        return result;
    }

    @Override
    public Result getRtsp(String indexCode) {

        String string = HikUtils.getRtsp(indexCode);
        Result result = new Result();
        result.setData(string);
        return result;
    }

    @Override
    public Result getHls(String indexCode) {

        String string = HikUtils.getHls(indexCode);
        Result result = new Result();
        result.setData(string);
        return result;
    }
}
