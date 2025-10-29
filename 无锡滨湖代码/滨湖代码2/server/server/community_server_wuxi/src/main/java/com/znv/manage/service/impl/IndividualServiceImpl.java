package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Individual;
import com.znv.manage.common.bean.IndividualConfig;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.JsonUtils;
import com.znv.manage.common.utils.MapUtil;
import com.znv.manage.common.utils.SurveyMap;
import com.znv.manage.dao.IndividualDao;
import com.znv.manage.service.IndividualService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class IndividualServiceImpl implements IndividualService {

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private IndividualDao individual;
    @Autowired
    private SurveyMap surveyMap;
    @Autowired
    private IndividualConfig individualConfig;
    @Value("${individual_gps_url:}")
    private String indivdualNewUrl;

    @Value("${danbin.gps.url:http://119.3.16.101:8082/idslds/UeLocationInfo/querylatestloction}")
    private String danbinGpsUrl;
    @Value("${danbin.gps.proxy.ip:10.7.53.116}")
    private String danbinGpsProxyIp;
    @Value("${danbin.gps.proxy.port:18082}")
    private String danbinGpsProxyPort;

    @Override
    public String token() {
        return restTemplate.getForObject(individualConfig.getHlwToken(),String.class);
    }

    @Override
    public String HLWtoken() {
        //查询验证码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject json = new JSONObject();
        json.put("loginName",individualConfig.getLogin_name());
        json.put("password",individualConfig.getLogin_password());
        HttpEntity entity = new HttpEntity<>(json.toString(),headers);
        JSONObject jsonObject = restTemplate.postForObject(individualConfig.getTokenUrl(), entity, JSONObject.class);
        String status = jsonObject.getString("status");
        if("200".equals(status)){
            JSONObject data = jsonObject.getJSONObject("data");
            String token = data.getString("token");
            System.err.println(token);
            return token;
        }
        return null;
    }

    @Override
    public JSONObject memberList() {
        //查询验证码
        HttpEntity entity = new HttpEntity<>(null,getHeader(MediaType.APPLICATION_FORM_URLENCODED));
        HttpEntity< JSONObject> response = restTemplate.exchange(individualConfig.getMemberUrl(),HttpMethod.GET,entity,JSONObject.class);
        JSONObject jsonObject = response.getBody();
        judgeToken(jsonObject);
        log.info("单兵{}",jsonObject);
        //插入数据
        String status = jsonObject.getString("status");
        if("200".equals(status)){
            JSONObject data = JsonUtils.getJSONObject(jsonObject, "data");
            JSONObject page = JsonUtils.getJSONObject(data, "page");
            JSONArray resultList = JsonUtils.getJSONArray(page, "resultList");
            List<Individual> list = JSONObject.parseArray(resultList.toJSONString(), Individual.class);
            individual.saveIndividual(list);
        }
        return jsonObject;
    }

    @Override
    public String  verificationCode(){
        HttpEntity< JSONObject> response = restTemplate.exchange(individualConfig.getVerificationUrl(),HttpMethod.GET,null,JSONObject.class);
        JSONObject body = response.getBody();
        String image = body.getString("data");
        return "data:image/jpg;base64,"+image;
    }

    @Override
    public JSONObject gps() {
        try{
            JSONObject jsonObject = restTemplate.getForObject(individualConfig.getHlwGps(), JSONObject.class);
            judgeToken(jsonObject);
            JSONArray data = JsonUtils.getJSONArray(jsonObject, "data");
            List<Map<String, String>> maps = new ArrayList<>();
            for(int i=0;i<data.size();i++){
                Map<String, String> map = new HashMap<>();
                JSONObject json = data.getJSONObject(i);
                String timestamp = JsonUtils.getJSONString(json, "timestamp");
                String createTime = DateUtil.timeStamp2Date(timestamp+"000", DateUtil.formatYMDHms);
                map.put("submitTime",createTime);
                map.put("id", JsonUtils.getJSONString(json, "id"));
                String gpsx = JsonUtils.getJSONString(json, "longitude");
                String gpsy = JsonUtils.getJSONString(json, "latitude");
                Map<String, Double> gps84 = MapUtil.gcj_To_Gps84(Double.parseDouble(gpsx), Double.parseDouble(gpsy));
                Double lon = gps84.get("lon");
                Double lat = gps84.get("lat");
                map.put("gpsx",lon+"");
                map.put("gpsy",lat+"");
                Map<String, String> map1 = surveyMap.SurveyGpsBy84(lon, lat);
                map.put("survey_gpsx",map1.get("gpsx"));
                map.put("survey_gpsy",map1.get("gpsy"));
                maps.add(map);
            }
            //插入数据
            if(maps.size()>0){
                individual.saveIndividualGps(maps);
            }
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject gpsZL() {
        List<String> list = individual.queryIndividualUid("2");
        JSONArray array = new JSONArray();
        for (String s : list) {
            JSONObject json = new JSONObject();
            json.put("mdn",s);
            json.put("systemId","1");
            array.add(json);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(array,headers);
        JSONObject jsonObject = restTemplate.postForObject(individualConfig.getHlwZLgps(),entity, JSONObject.class);
        log.info("智领{}",jsonObject);
        JSONArray data = jsonObject.getJSONArray("data");
        List<Map<String,String>> objects = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            Map<String, String> map = new HashMap<>();
            JSONObject object = data.getJSONObject(i);
            map.put("id",object.getString("mdn"));
            map.put("submitTime", DateUtil.timeStamp2Date(object.getString("time")+"000","yyyy-MM-dd HH:mm:ss"));
            Double gpsx=Double.parseDouble(object.getString("longitude"))/100000;
            Double gpsy=Double.parseDouble(object.getString("latitude"))/100000;
            Map<String, Double> gps84 = MapUtil.gcj_To_Gps84(gpsx,gpsy);
            Double lon = gps84.get("lon");
            Double lat = gps84.get("lat");
            map.put("gpsx",lon+"");
            map.put("gpsy",lat+"");
            Map<String, String> map1 = surveyMap.SurveyGpsBy84(lon, lat);
            map.put("survey_gpsx",map1.get("gpsx"));
            map.put("survey_gpsy",map1.get("gpsy"));
            objects.add(map);
        }
        if(objects.size()>0){
            individual.saveIndividualGps(objects);
        }
        return null;
    }

    @Override
    public JSONObject HLWgps() {
        Map<String, Object> param = new HashMap<>();
        List<String> list = individual.queryIndividualUid("1");
        param.put("uids",list);
        HttpEntity entity = new HttpEntity<>(param,getHeader(MediaType.APPLICATION_JSON));
        JSONObject jsonObject = restTemplate.postForObject(individualConfig.getGpsUrl(), entity, JSONObject.class);
        System.err.println(jsonObject);
        return jsonObject;
    }

    @Override
    public JSONObject createGroup(String name,List<String> deviceIds) {
        try{
            JSONObject json = new JSONObject();
            json.put("name",name);
            json.put("uids",deviceIds);
            HttpEntity entity = new HttpEntity<>(json,getHeader(MediaType.APPLICATION_JSON));
            JSONObject jsonObject = restTemplate.postForObject(individualConfig.getHlwCreateGroup(), entity, JSONObject.class);
            log.info(jsonObject.toJSONString());
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject HLWcreateGroup(String name, List<String> uids) {
        try{
            JSONObject json = new JSONObject();
            json.put("name",name);
            json.put("uids",uids);
            HttpEntity entity = new HttpEntity<>(json,getHeader(MediaType.APPLICATION_JSON));
            JSONObject jsonObject = restTemplate.postForObject(individualConfig.getCreateTemporaryGroupUrl(), entity, JSONObject.class);
            judgeToken(jsonObject);
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject dissolveGroup(String gid) {
        HttpEntity entity = new HttpEntity<>(null,getHeader(MediaType.APPLICATION_FORM_URLENCODED));
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(individualConfig.getHlwDissolveGroup() + "?gid=" + gid, HttpMethod.GET, entity, JSONObject.class);
        JSONObject jsonObject = exchange.getBody();
        judgeToken(jsonObject);
        return jsonObject;
    }

    @Override
    public JSONObject HLWdissolveGroup(String gid) {
        HttpEntity entity = new HttpEntity<>(null,getHeader(MediaType.APPLICATION_FORM_URLENCODED));
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(individualConfig.getDissolveTemporaryGroupUrl() + "?gid=" + gid, HttpMethod.GET, entity, JSONObject.class);
        JSONObject jsonObject = exchange.getBody();
        judgeToken(jsonObject);
        return jsonObject;
    }

    @Override
    public JSONObject checkGroup(String gid) {
        HttpEntity entity = new HttpEntity<>(null,getHeader(MediaType.APPLICATION_FORM_URLENCODED));
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(individualConfig.getCheckTemporaryGroupUrl() + "?gid=" + gid, HttpMethod.GET, entity, JSONObject.class);
        JSONObject jsonObject = exchange.getBody();
        judgeToken(jsonObject);
        return jsonObject;
    }

    public JSONObject groupList(){
        try{
            HttpEntity entity = new HttpEntity<>(null,getHeader(MediaType.APPLICATION_FORM_URLENCODED));
            ResponseEntity<JSONObject> exchange = restTemplate.exchange(individualConfig.getGroupUrl(), HttpMethod.GET, entity, JSONObject.class);
            JSONObject jsonObject = exchange.getBody();
            JSONArray data = JsonUtils.getJSONArray(jsonObject, "data");
            for(int i=0;i<data.size();i++){

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private HttpHeaders getHeader(MediaType mediaType){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.set("pc-access-token",token());
        return headers;
    }

    private void judgeToken(JSONObject json){
        String message = json.getString("message");
        if("未登录".equals(message)){
            token();
        }
    }

    private static void getTree(String s,String parentId){
        JSONObject jsonObject = JSONObject.parseObject(s);
        if("200".equals(jsonObject.getString("status"))){
            JSONArray datas = JsonUtils.getJSONArray(jsonObject, "data");
            for(int i=0;i<datas.size();i++){
                JSONObject data = datas.getJSONObject(i);
                String id = data.getString("id");
                String name = data.getString("name");
                Map<String, String> map = new HashMap<>();
                map.put("id",id);
                map.put("name",name);
                map.put("parentId",parentId);
                if(data.containsKey("children")){
//                    getTree();
                }
            }
        }
    }

    public JSONObject saveInfo(JSONArray message){
        log.info("新单兵{}",message);
        JSONObject param = new JSONObject();
        param.put("uelist",message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(param,headers);
        String json = restTemplate.postForObject(indivdualNewUrl, entity, String.class);
        JSONObject object = JSONObject.parseObject(json);
        return object;
//        JSONArray data = object.getJSONArray("data");
//        System.err.println(json);
//        List<Map<String, String>> list = new ArrayList<>();
        //保存数据
//        for(int i=0;i<data.size();i++){
//            Map<String, String> map = new HashMap<>();
//            JSONObject deviceGps = data.getJSONObject(i);
//            map.put("id",deviceGps.getString("mdn"));
//            map.put("submitTime",deviceGps.getString("time"));
//            map.put("gpsy",deviceGps.getString("latitude"));
//            map.put("gpsx",deviceGps.getString("longitude"));
//            list.add(map);
//        }
//        individual.saveIndividualGps(list);
    }

    @Override
    public JSONObject saveStatus(JSONArray array) {
        HttpEntity entity = new HttpEntity<>(array,getHeader(MediaType.APPLICATION_JSON));
        String json = restTemplate.postForObject("http://shdb-gis.intcity.com.cn:50001/gis/api/pttUser/isOnline", entity, String.class);
        JSONObject object = JSONObject.parseObject(json);
        return object;
    }

    @Override
    public void updateGps() {
        String url = danbinGpsUrl;
        try {
            RestTemplate restTemplate1 = new RestTemplate();
            List<Map<String,Object>> list = individual.queryIndividualIds();
            JSONObject msg = new JSONObject();
            msg.put("uelist",list);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setProxy(
                    new Proxy(
                            Proxy.Type.HTTP,
                            new InetSocketAddress(danbinGpsProxyIp, Integer.parseInt(danbinGpsProxyPort))  //设置代理服务
                    )
            );
            restTemplate1.setRequestFactory(requestFactory);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            String result = restTemplate1.postForObject(url, request, String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray data = jsonObject.getJSONArray("data");
            if(!CollectionUtils.isEmpty(data)){
                for(int i=0;i<data.size();i++){
                    data.getJSONObject(i).put("gpsx",Double.parseDouble(data.getJSONObject(i).getString("longitude"))/100000);
                    data.getJSONObject(i).put("gpsy",Double.parseDouble(data.getJSONObject(i).getString("latitude"))/100000);
                }
            }
            individual.updateIndividualGps(data);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        JSONArray objects = new JSONArray();
        objects.add("a");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid",objects);
        System.err.println(jsonObject);
    }
}
