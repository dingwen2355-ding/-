package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.PrecinctDao;
import com.znv.manage.service.WXService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


@Service
@Slf4j
public class WXServiceImpl implements WXService {

    @Autowired
    private PrecinctDao precinctDao;

    private static String ACCESS_TOKEN = "";
    private static Date ACCESS_TIME;



    @Override
    public String login() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            JSONObject jsonObject = restTemplate.getForObject("http://172.61.132.228:30080/cgi-bin/gettoken?corpid=ww8ce488fc8ced87c6&corpsecret=fdMKaqJwvoGzCDMXQ3OdqVILW-GKtEkGYyCvyaa3h80",JSONObject.class);
            if(null != jsonObject && "ok".equals(jsonObject.getString("errmsg"))){
                log.error("flag1111");
                ACCESS_TIME = new Date();
                ACCESS_TOKEN = jsonObject.getString("access_token");
                log.error("flag222"+"ACCESS_TIME:"+ACCESS_TIME+"---"+"token:"+ACCESS_TOKEN);
            }else {
                log.error("wx获取accessToken失败："+jsonObject);
            }
        }catch (Exception e){
            log.error("wx获取accessToken异常："+e.getMessage());
        }
        return ACCESS_TOKEN;
    }

    @Override
    public String getAccessToken() {
        String token = "";
        try {
            Date now = new Date();
            log.error("now:"+now+"---"+"ACCESS_TIME:"+ACCESS_TIME+"---"+"token:"+ACCESS_TOKEN);
            if(StringUtils.isEmpty(ACCESS_TOKEN) || null == ACCESS_TIME){
                token = login();
            }else {
                if(now.getTime() - ACCESS_TIME.getTime() > 1000 * 60 * 60 * 1.5){
                    token = login();
                }else {
                    token = ACCESS_TOKEN;
                }
            }
        }catch (Exception e){
            log.error("获取token异常："+e.getMessage());
        }
        return token;
    }

    @Override
    public Result getWxURl(String userId,String precinctId) {
        Result result = new Result();
        try {
            String token = getAccessToken();
            if(StringUtils.isEmpty(token)){
                result.setCode(1);
                result.setMessage("获取token失败");
            }else {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://172.61.132.228:30080/cgi-bin/get_launch_code?access_token=" + token;
                log.error("wxUrl:"+url);
                String operator_userid = precinctDao.queryWxUserId(precinctId);
                String msg = "{\n" +
                        "        \"type\": \"single_voip\",\n" +
                        "        \"operator_userid\": \"\",\n" +
                        "        \"single_voip\": {\n" +
                        "                \"userid\": \"\"\n" +
                        "        }\n" +
                        "}";
                JSONObject object = JSONObject.parseObject(msg);
                object.getJSONObject("single_voip").put("userid",userId);
                object.put("operator_userid",operator_userid);
                log.error("object:"+object);
                ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST,new HttpEntity<>(object,new HttpHeaders()),JSONObject.class);
                JSONObject jsonObject = responseEntity.getBody();
                if(null != jsonObject && "ok".equals(jsonObject.getString("errmsg"))){
                    String code = jsonObject.getString("launch_code");
                    String result_str = "wxworklocal://launch?launch_code="+code+"&type=openvoip&is_video=1";
                    result.setData(result_str);
                }else {
                    result.setCode(1);
                    result.setMessage("get_launch_code接口调用失败:"+jsonObject);
                    log.error("get_launch_code接口调用失败:"+jsonObject);
                }
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage("get_launch_code调用异常："+e.getMessage());
            log.error("get_launch_code调用异常："+e.getMessage());
        }
        return result;
    }
}
