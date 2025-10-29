package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.*;
import com.znv.manage.dao.EventManageMapper;
import com.znv.manage.dao.PublicOpinionDao;
import com.znv.manage.service.PublicOpinionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class PublicOpinionServiceImpl implements PublicOpinionService {

    @Autowired
    PublicOpinionDao publicOpinionDao;

    @Autowired
    private EventManageMapper eventInfoDao;

    @Value("${transmit.opinion.url:https://yuqing.shdata.com:8443/api/TopNegXiangYangEvents/80970B0C4D9742C0B01A263EEDCEE140}")
    String transmitPublicOpinionUrl;

    @Value("${opinion.login.url:http://yqmsapi.istarshine.com/v4/user/login}")
    String opinionLoginUrl;
    @Value("${opinion.searchAll.url:http://yqmsapi.istarshine.com/v4/search/all}")
    String searchAllUrl;
    @Value("${opinion.mediaCount.url:http://yqmsapi.istarshine.com/v4/search/allMediaCount}")
    String mediaCountUrl;
    @Value("${opinion.allStatisticalAnalysis.url:http://yqmsapi.istarshine.com/v4/search/allStatisticalAnalysis}")
    String allStatisticalAnalysisUrl;
    @Value("${opinion.loginName:17708010053}")
    String loginName;
    @Value("${opinion.password:cdwxb@666}")
    String password;

    private static final Map<String,String> MEDIA_TYPE_MAP = new HashMap<>();
    static {
        MEDIA_TYPE_MAP.put("1","网媒");
        MEDIA_TYPE_MAP.put("2","微博");
        MEDIA_TYPE_MAP.put("3","微信");
        MEDIA_TYPE_MAP.put("4","论坛");
        MEDIA_TYPE_MAP.put("5","贴吧");
        MEDIA_TYPE_MAP.put("6","报刊");
        MEDIA_TYPE_MAP.put("7","短视频");
        MEDIA_TYPE_MAP.put("8","网络视频");
        MEDIA_TYPE_MAP.put("9","电视视频");
        MEDIA_TYPE_MAP.put("10","APP");
        MEDIA_TYPE_MAP.put("11","其他");


    }

    private static Map<String,Object> TIME_TOKEN = new HashMap<>();

    @Override
    public String setToken() {
        RestTemplate restTemplate = new RestTemplate();
        String token = "";
        try {
            JSONObject msg = new JSONObject();
            msg.put("loginName", loginName);
            msg.put("password", password);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(opinionLoginUrl, request, JSONObject.class);
            log.info("舆情登陆结果："+jsonObject);
            if("200".equals(jsonObject.getString("code"))){
                String session = jsonObject.getJSONObject("data").getString("session");
                String userId = jsonObject.getJSONObject("data").getString("userId");
                token = session + "_" + userId;
                token = Base64.getEncoder().encodeToString(token.getBytes());
                TIME_TOKEN.put("time",new Date());
                TIME_TOKEN.put("token",token);
            }
        }catch (Exception e){
            log.error("舆情登陆异常："+e.getMessage());
        }
        return token;
    }

    @Override
    public Result searchAll(String eventId,String keywords,String limitNum) {
        Result result = new Result();
        if(StringUtils.isEmpty(keywords)){
//            List<Map<String, Object>> list = eventInfoDao.queryEventInfo(eventId,null,null,null,null,null,null,null,null,null);
//            Map<String,Object> event = list.get(0);
//            String eventTitle = String.valueOf(event.get("eventTitle"));
//            keywords = eventTitle;
            return result;
        }
        RestTemplate restTemplate = new RestTemplate();
        try {
            String jsonStr = "{\n" +
                    "    \"timeRange\":\"2\",\n" +
                    "    \"attitude\":[],\n" +
                    "    \"keywords\":\"\",\n" +
                    "    \"typeList\":[\"title\"],\n" +
                    "    \"secondKeywords\":\"\",\n" +
                    "    \"secondTypeList\":[\"title\"],\n" +
                    "    \"searchMode\":\"2\",\n" +
                    "    \"mediaType\":[],\n" +
                    "    \"weiboType\":[1],\n" +
                    "    \"weiboState\":\"\",\n" +
                    "    \"infoSource\":\"\",\n" +
                    "    \"sourceRange\":\"\",\n" +
                    "    \"isOcr\":\"\",\n" +
                    "    \"isRepeat\":\"1\",\n" +
                    "    \"orderBy\":1,\n" +
                    "    \"limitNum\":10,\n" +
                    "    \"sites\":[],\n" +
                    "    \"customCondition\":[],\n" +
                    "    \"offset\":0\n" +
                    "}";
            JSONObject msg = JSONObject.parseObject(jsonStr);
            msg.put("keywords",keywords);
            msg.put("secondKeywords",keywords);
            msg.put("beginTime",DateUtil.getPreTime2(-24 * 60));
            msg.put("endTime",new Date().getTime());
            msg.put("limitNum",Integer.parseInt(limitNum));
            log.error("舆情入参："+msg);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("token",getToken());
            log.error("舆情token："+getToken());
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(searchAllUrl, request, JSONObject.class);
            log.error("舆情jsonObject："+getToken());
            if("200".equals(jsonObject.getString("code"))){
                result.setData(jsonObject.getJSONObject("data"));
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result opinionStatistics(String eventId,String keywords) {
        Result result = new Result();
        try{
//            List<Map<String, Object>> list = eventInfoDao.queryEventInfo(eventId,null,null,null,null,null,null,null,null,null);
//            if(!CollectionUtils.isEmpty(list)){
                RestTemplate restTemplate = new RestTemplate();
//                Map<String,Object> event = list.get(0);
//                Date happenTime = DateUtil.strToDateLong(String.valueOf(event.get("happenTime")));
                if(StringUtils.isEmpty(keywords)){
//                    String eventTitle = String.valueOf(event.get("eventTitle"));
//                    keywords = eventTitle;
                    return result;
                }
                String jsonStr = "{\n" +
                        "    \"timeRange\":\"2\",\n" +
                        "    \"interval\":\"hour\",\n" +
                        "    \"attitude\":[],\n" +
                        "    \"keywords\":\"\",\n" +
                        "    \"typeList\":[\"title\"],\n" +
                        "    \"secondKeywords\":\"\",\n" +
                        "    \"secondTypeList\":[\"title\"],\n" +
                        "    \"searchMode\":\"2\",\n" +
                        "    \"mediaType\":[],\n" +
                        "    \"weiboType\":[1],\n" +
                        "    \"weiboState\":\"\",\n" +
                        "    \"infoSource\":\"\",\n" +
                        "    \"sourceRange\":\"\",\n" +
                        "    \"isOcr\":\"\",\n" +
                        "    \"isRepeat\":\"0\",\n" +
                        "    \"orderBy\":2,\n" +
                        "    \"limitNum\":100,\n" +
                        "    \"sites\":[],\n" +
                        "    \"customCondition\":[],\n" +
                        "    \"offset\":0\n" +
                        "\n" +
                        "}";
                JSONObject msg = JSONObject.parseObject(jsonStr);
//                msg.put("beginTime",happenTime.getTime());
//                msg.put("endTime",new Date().getTime());
                msg.put("keywords",keywords);
                msg.put("secondKeywords",keywords);
                msg.put("beginTime",DateUtil.getPreTime2(-24 * 60));
            msg.put("endTime",new Date().getTime());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                headers.add("token",getToken());
                HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
                JSONObject jsonObject = restTemplate.postForObject(allStatisticalAnalysisUrl, request, JSONObject.class);
                if("200".equals(jsonObject.getString("code"))){
                    JSONArray data = jsonObject.getJSONArray("data");
                    result.setData(data);
                }
//            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result mediaTypeStatistics(String eventId,String keywords) {
        Result result = new Result();
        try{
            RestTemplate restTemplate = new RestTemplate();
            if(StringUtils.isEmpty(keywords)){
//                List<Map<String, Object>> list = eventInfoDao.queryEventInfo(eventId,null,null,null,null,null,null,null,null,null);
//                Map<String,Object> event = list.get(0);
//                String eventTitle = String.valueOf(event.get("eventTitle"));
//                keywords = eventTitle;
                return result;
            }
            String jsonStr = "{\n" +
                    "    \"timeRange\":\"2\",\n" +
                    "    \"attitude\":[],\n" +
                    "    \"keywords\":\"\",\n" +
                    "    \"typeList\":[\"title\"],\n" +
                    "    \"secondKeywords\":\"\",\n" +
                    "    \"secondTypeList\":[\"title\"],\n" +
                    "    \"searchMode\":\"2\",\n" +
                    "    \"mediaType\":[],\n" +
                    "    \"weiboType\":[1],\n" +
                    "    \"weiboState\":\"\",\n" +
                    "    \"infoSource\":\"\",\n" +
                    "    \"sourceRange\":\"\",\n" +
                    "    \"isOcr\":\"\",\n" +
                    "    \"isRepeat\":\"1\",\n" +
                    "    \"orderBy\":1,\n" +
                    "    \"limitNum\":1,\n" +
                    "    \"sites\":[],\n" +
                    "    \"customCondition\":[],\n" +
                    "    \"offset\":0\n" +
                    "\n" +
                    "}";
            JSONObject msg = JSONObject.parseObject(jsonStr);
            msg.put("keywords",keywords);
            msg.put("secondKeywords",keywords);
            msg.put("beginTime",DateUtil.getPreTime2(-24 * 60));
            msg.put("endTime",new Date().getTime());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("token",getToken());
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(mediaCountUrl, request, JSONObject.class);
            if("200".equals(jsonObject.getString("code"))){
                JSONArray mediaCount = jsonObject.getJSONObject("data").getJSONArray("mediaCount");
                if(!CollectionUtils.isEmpty(mediaCount) && mediaCount.size() == 12){
                    JSONArray resultArray = new JSONArray();
                    for(int i=1;i<mediaCount.size();i++){
                        JSONObject temp = new JSONObject();
                        temp.put("mediaType",MEDIA_TYPE_MAP.get(i+""));
                        temp.put("count",mediaCount.get(i));
                        resultArray.add(temp);
                    }
                    result.setData(resultArray);
                }
            }

        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result attitudeStatistics(String eventId, String keywords) {
        Result result = new Result();
        try {
            if(StringUtils.isEmpty(keywords)){
//                List<Map<String, Object>> list = eventInfoDao.queryEventInfo(eventId,null,null,null,null,null,null,null,null,null);
//                Map<String,Object> event = list.get(0);
//                String eventTitle = String.valueOf(event.get("eventTitle"));
//                keywords = eventTitle;
                return result;
            }
            JSONArray jsonArray = new JSONArray();
            for(int i=1;i<=3;i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type",i+"");
                jsonObject.put("count",getOpinionTotalByAttitude(keywords,i+""));
                jsonArray.add(jsonObject);
            }
            result.setData(jsonArray);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }
    private String getOpinionTotalByAttitude(String keywords,String attitude) {
        String count = 0 + "";
        try{
            RestTemplate restTemplate = new RestTemplate();
            String jsonStr = "{\n" +
                    "    \"timeRange\":\"2\",\n" +
                    "    \"attitude\":[],\n" +
                    "    \"keywords\":\"\",\n" +
                    "    \"typeList\":[\"title\"],\n" +
                    "    \"secondKeywords\":\"\",\n" +
                    "    \"secondTypeList\":[\"title\"],\n" +
                    "    \"searchMode\":\"2\",\n" +
                    "    \"mediaType\":[],\n" +
                    "    \"weiboType\":[1],\n" +
                    "    \"weiboState\":\"\",\n" +
                    "    \"infoSource\":\"\",\n" +
                    "    \"sourceRange\":\"\",\n" +
                    "    \"isOcr\":\"\",\n" +
                    "    \"isRepeat\":\"1\",\n" +
                    "    \"orderBy\":1,\n" +
                    "    \"limitNum\":10,\n" +
                    "    \"sites\":[],\n" +
                    "    \"customCondition\":[],\n" +
                    "    \"offset\":0\n" +
                    "}";
            JSONObject msg = JSONObject.parseObject(jsonStr);
            msg.put("keywords",keywords);
            msg.put("secondKeywords",keywords);
            msg.put("beginTime",DateUtil.getPreTime2(-24 * 60));
            msg.put("endTime",new Date().getTime());
            msg.getJSONArray("attitude").add(attitude);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("token",getToken());
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(searchAllUrl, request, JSONObject.class);
            if("200".equals(jsonObject.getString("code"))){
                count = jsonObject.getJSONObject("data").getString("total");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return count;
    }

    private String getOpinionTotal(String beginTime, String endTime,String keywords) {
        String count = 0 + "";
        try{
            RestTemplate restTemplate = new RestTemplate();
            String jsonStr = "{\n" +
                    "    \"beginTime\":\"\",\n" +
                    "    \"endTime\":\"\",\n" +
                    "    \"attitude\":[],\n" +
                    "    \"keywords\":\"\",\n" +
                    "    \"typeList\":[\"title\"],\n" +
                    "    \"secondKeywords\":\"\",\n" +
                    "    \"secondTypeList\":[\"title\"],\n" +
                    "    \"searchMode\":\"2\",\n" +
                    "    \"mediaType\":[],\n" +
                    "    \"weiboType\":[1],\n" +
                    "    \"weiboState\":\"\",\n" +
                    "    \"infoSource\":\"\",\n" +
                    "    \"sourceRange\":\"\",\n" +
                    "    \"isOcr\":\"\",\n" +
                    "    \"isRepeat\":\"1\",\n" +
                    "    \"orderBy\":1,\n" +
                    "    \"limitNum\":1,\n" +
                    "    \"sites\":[],\n" +
                    "    \"customCondition\":[],\n" +
                    "    \"offset\":0\n" +
                    "}";
            JSONObject msg = JSONObject.parseObject(jsonStr);
            msg.put("beginTime",beginTime);
            msg.put("endTime",endTime);
            msg.put("keywords",keywords);
            msg.put("secondKeywords",keywords);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("token",getToken());
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(searchAllUrl, request, JSONObject.class);
            if("200".equals(jsonObject.getString("code"))){
                count = jsonObject.getJSONObject("data").getString("total");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return count;
    }

    public String getToken(){
        String token = "";
        try {
            log.error("获取token 111："+TIME_TOKEN);
            if(!CollectionUtils.isEmpty(TIME_TOKEN)){
                Date date = new Date();
                Date token_time = (Date)TIME_TOKEN.get("time");
                long time = (date.getTime() / 1000) - 23 * 60 * 60;
                date.setTime(time * 1000);
                if(date.compareTo(token_time) > 0){
                    log.error("获取token 222："+date+"---"+token_time);
                    token = setToken();
                }else {
                    log.error("获取token 333：");
                    token = String.valueOf(TIME_TOKEN.get("token"));
                }
            }else {
                log.error("获取token 444：");
                token = setToken();
            }
        }catch (Exception e){
            log.error("舆情获取token异常："+e.getMessage());
        }
        return token;
    }

//    private static final String foreignMedia = "BBC,bbc,twitter,Twitter,TWITTER,CNN,FOX";
//    private static final List<String> foreignMediaList=new ArrayList<String>(){
//        {
//            this.add("BBC");
//            this.add("TWITTER");
//            this.add("CNN");
//            this.add("FOX");
//        }
//    };
//
//    private static final List<String> selfMediaList=new ArrayList<String>(){
//        {
//            this.add("WEIBO.COM");
//            this.add("WECHAT");
//        }
//    };
//
//
//    @Override
//    public Result getRealPublicOpinionDataByEventId(String eventId) {
//
//        Result result = new Result();
//        try {
//            Map<String, Object> resulMap = new HashMap<>();
//
//            List<Map<String, Object>> publicOpinionList = publicOpinionDao.getReceivedPublicOpinionData(eventId, null);
//
//            List<Map<String, Object>> publicOpinionReportList = publicOpinionDao.getReceivedPublicOpinionReport(eventId);
//
//            List<Map<String, Object>> socialReportList = publicOpinionDao.getSocialReportList(eventId, "1");
//
//            List<String> list = new ArrayList<>();
//            int readCount = 0;
//            int pubCount = 0;
//            int totalCount = 0;
//
//            Long selfMediaCount = 0L;
//            Long chinaWebCount = 0L;
//            Long foreignWebCount = 0L;
//
//            if (publicOpinionList.size() > 0) {
//                for (Map<String, Object> map : publicOpinionList) {
//                    readCount += Integer.parseInt(map.get("readCount").toString());
//                    pubCount += Integer.parseInt(map.get("pubCount").toString());
//                    totalCount += getTotalNum(Arrays.asList((map.get("countPerHour").toString().replace("[", "").replace("]", "")).split(",")));
//                    list = getTotalCountPerHour(list, Arrays.asList((map.get("countPerHour").toString().replace("[", "").replace("]", "")).split(",")));
//                }
//            }
//            for (Map<String, Object> map : publicOpinionList) {
//                chinaWebCount = chinaWebCount + (Long) map.get("chinaWebCount");
//                selfMediaCount = selfMediaCount + (Long) map.get("selfMediaCount");
//                foreignWebCount = foreignWebCount + (Long) map.get("foreignWebCount");
//            }
//
//
//            resulMap.put("chinaWebCount", chinaWebCount);
//            resulMap.put("selfMediaCount", selfMediaCount);
//            resulMap.put("foreignWebCount", foreignWebCount);
//
//            resulMap.put("readCount", readCount);
//            resulMap.put("pubCount", pubCount);
//            resulMap.put("totalCount", totalCount);
//
//            resulMap.put("list", list);
//            resulMap.put("webReportList", publicOpinionReportList);
//            resulMap.put("socialReportList", socialReportList);
//
//            result.setData(resulMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setCode(1);
//            result.setMessage(e.getMessage());
//            log.error(e.toString());
//        }
//        return result;
//    }
//
//    private static int getTotalNum(List<String> list) {
//
//        Integer totalNum = 0;
//        for (String s : list) {
//            totalNum += Integer.parseInt(s);
//        }
//        return totalNum;
//    }
//
//    private List<String> getTotalCountPerHour(List<String> list1, List<String> list2) {
//
//        List<String> list = new ArrayList<>();
//
//        if (list1.isEmpty() || list2.isEmpty() || list1.size() != list2.size()) {
//            list = list1.isEmpty() ? list2 : list1;
//        } else {
//            for (int i = 0; i < list1.size(); i++) {
//                for (int j = 0; j < list2.size(); j++) {
//                    if (i == j) {
//                        list.add(String.valueOf(Integer.parseInt(list1.get(i)) + Integer.parseInt(list2.get(j))));
//                    }
//                }
//            }
//        }
//
//        return list;
//    }
//
//
//    @Override
//    public Result getPublicOpinionByKeyWord(String keyWords, String eventId) {
//
//        Result result = new Result();
//        Map<String, Object> res = new HashMap<>();
//        List<Map<String, Object>> keyWordList = new ArrayList<>();
//        List<Map<String, Object>> boundList = new ArrayList<>();
//        try {
//            if (!StringUtils.isEmpty(eventId) && !"".equals(eventId)) {
//                boundList = publicOpinionDao.getReceivedPublicOpinionData(eventId, null);
//            }
//
//            if (!StringUtils.isEmpty(keyWords) && !"".equals(keyWords)) {
//                keyWordList = publicOpinionDao.getPublicOpinionDataByKeyWord(keyWords, eventId);
//                keyWordList.forEach(map -> {
//                    if (!ObjectUtils.isEmpty(map.get("eventId")) && (map.get("eventId").toString().equals(eventId) || map.get("eventId").toString().contains(eventId))) {
//                        // 判断该舆情是否与该事件绑定
//                        map.put("isBound", "0");
//                    } else {
//                        map.put("isBound", "1");
//                    }
//                });
//            }
//
//
//            res.put("keyWordList", keyWordList);
//            res.put("boundList", boundList);
//            result.setData(res);
//        } catch (Exception e) {
//            log.error(e.toString());
//            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
//            result.setData(e.toString());
//        }
//
//        return result;
//    }
//
//    @Override
//    public Result boundPublicOPinionWithEvent(String eventId, String publicOpinionIds, String type) {
//
//        Result result = new Result();
//        try {
//
//            switch (type) {
//                // 事件绑定
//                case "1":
//                    List<Map<String, Object>> list = publicOpinionDao.getReceivedPublicOpinionData(null, publicOpinionIds);
//                    list.forEach(map -> {
//                        if (ObjectUtils.isEmpty(map.get("eventId"))) {
//                            publicOpinionDao.updatePublicOpinionRelation(eventId, map.get("publicOpinionId").toString());
//                        } else {
//                            if (!eventId.equals(map.get("eventId").toString()) && !map.get("eventId").toString().contains(eventId)) {
//                                String eventIds = map.get("eventId").toString() + "," + eventId;
//                                publicOpinionDao.updatePublicOpinionRelation(eventIds, map.get("publicOpinionId").toString());
//                            }
//                        }
//                    });
//                    result.setResutCode(ResultCodeEnum.CREATEORUPDATESUCCESS.getCode(), ResultCodeEnum.CREATEORUPDATESUCCESS.getName());
//                    break;
//                // 事件解除绑定
//                case "2":
//
//                    List<Map<String, Object>> boundList = publicOpinionDao.getReceivedPublicOpinionData(eventId, publicOpinionIds);
//                    boundList.forEach(map -> {
//                        if (map.get("eventId").toString().equals(eventId)) {
//                            map.replace("eventId", null);
//                            publicOpinionDao.updatePublicOpinionRelation(null, map.get("publicOpinionId").toString());
//                        } else if (map.get("eventId").toString().contains("," + eventId)) {
//                            map.replace("eventId", map.get("eventId").toString().replace("," + eventId, ""));
//                            publicOpinionDao.updatePublicOpinionRelation(map.get("eventId").toString(), map.get("publicOpinionId").toString());
//                        }
//
//                        result.setResutCode(ResultCodeEnum.CREATEORUPDATESUCCESS.getCode(), ResultCodeEnum.CREATEORUPDATESUCCESS.getName());
//                    });
//                    break;
//                default:
//                    result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
//                    result.setMessage("type is not allowed");
//                    break;
//            }
//        } catch (Exception e) {
//            log.error(e.toString());
//            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
//        }
//        return result;
//    }
//
//    public void saveOpinion(){
//        RestTemplate restTemplate = new RestTemplate();
//        JSONObject opinion = restTemplate.getForObject(transmitPublicOpinionUrl, JSONObject.class);
//        JSONArray topEvents = JsonUtils.getJSONArray(opinion, "TopEvents");
//        List<Map<String, String>> opinioInfos = new ArrayList<>();
//        List<Map<String, String>> opinionsReport = new ArrayList<>();
//        System.err.println();
//        if(topEvents!=null&&topEvents.size()>0){
//            List<Map<String, String>> list = publicOpinionDao.queryPublicOpinionInfoAll();
//            Map<String, List<Map<String, String>>> opinionS = ListUtils.ListToMap(list, "opinionId");
//            //遍历舆情信息
//            for(int i=0;i<topEvents.size();i++){
//                int foreignWebCount=0;
//                int chinaWebCount=0;
//                int selfMediaCount=0;
//                JSONObject event = topEvents.getJSONObject(i);
//                String id = event.getString("id");
//                Map<String, String> map = new HashMap<>();
//                map.put("id",id);
//                map.put("update_time", DateUtil.timeStamp2Date(String.valueOf(event.get("update_time")), DateUtil.formatYMDHms));
//                map.put("pub_time",DateUtil.timeStamp2Date(String.valueOf(event.get("pub_time")), DateUtil.formatYMDHms));
//                map.put("title",event.getString("title"));
//                map.put("body", CommonUtils.convertEmoji(event.getString("body")));
//                map.put("source",event.getString("source"));
//                map.put("url",event.getString("url"));
//                map.put("pub_count",event.getString("pub_count"));
//                map.put("read_count",event.getString("read_count"));
//                map.put("negativity",event.getString("negativity"));
//                map.put("rank",event.getString("rank"));
//                map.put("count_per_hour",event.getString("count_per_hour"));
//                JSONArray reports = event.getJSONArray("source_list");
//                if(opinionS.containsKey(id)){
//
//                    Map<String, String> opinionOld = opinionS.get(id).get(0);
//                    int total = Integer.parseInt(opinionOld.get("total"));
//                    long pubTime = DateUtil.getTimeStamp(opinionOld.get("pubTime"));
//                    if(reports.size()==total){
//                        continue;
//                    }else{
//                        //判断时间
//                        foreignWebCount=Integer.parseInt(opinionOld.get("foreignWebCount"));
//                        chinaWebCount=Integer.parseInt(opinionOld.get("chinaWebCount"));
//                        selfMediaCount=Integer.parseInt(opinionOld.get("selfMediaCount"));
//                        for(int j=0;j<reports.size();j++){
//                            JSONObject source = reports.getJSONObject(j);
//                            long time = Long.parseLong(source.getString("pub_time"));
//                            if(pubTime<time){
//                                HashMap<String, String> report = new HashMap<>();
//                                String source_name = JsonUtils.getJSONString(source, "source_name");
//                                report.put("publicOpinionId",id);
//                                report.put("source_name",source_name);
//                                report.put("pub_time",DateUtil.timeStamp2Date(JsonUtils.getJSONString(source,"pub_time"),DateUtil.formatYMDHms));
//                                report.put("url",JsonUtils.getJSONString(source,"url"));
//                                report.put("title",CommonUtils.convertEmoji(JsonUtils.getJSONString(source,"title")));
//                                if(foreignMediaList.contains(source_name.toUpperCase())){
//                                    foreignWebCount++;
//                                }else if(selfMediaList.contains(source_name.toUpperCase())){
//                                    selfMediaCount++;
//                                }else{
//                                    chinaWebCount++;
//                                }
//                                opinionsReport.add(report);
//                            }else{
//                                break;
//                            }
//                        }
//                    }
//                }else{
//                    //全插入
//                    for(int j=0;j<reports.size();j++){
//                        JSONObject source = reports.getJSONObject(j);
//                        HashMap<String, String> report = new HashMap<>();
//                        report.put("publicOpinionId",id);
//                        String source_name = JsonUtils.getJSONString(source, "source_name");
//                        report.put("source_name",source_name);
//                        if(foreignMediaList.contains(source_name.toUpperCase())){
//                            foreignWebCount++;
//                        }else if(selfMediaList.contains(source_name.toUpperCase())){
//                            selfMediaCount++;
//                        }else{
//                            chinaWebCount++;
//                        }
//                        report.put("pub_time",DateUtil.timeStamp2Date(JsonUtils.getJSONString(source,"pub_time"),DateUtil.formatYMDHms));
//                        report.put("url",JsonUtils.getJSONString(source,"url"));
//                        report.put("title",CommonUtils.convertEmoji(JsonUtils.getJSONString(source,"title")));
//                        opinionsReport.add(report);
//                    }
//                }
//                map.put("foreignWebCount",foreignWebCount+"");
//                map.put("chinaWebCount",chinaWebCount+"");
//                map.put("selfMediaCount",selfMediaCount+"");
//                opinioInfos.add(map);
//            }
//        }
//        //插入数据库
//        if(opinioInfos.size()>0){
//            publicOpinionDao.insertPublicOpinionInfos(opinioInfos);
//        }
//        if(opinionsReport.size()>0){
//            publicOpinionDao.insertPublicOpinionReportInfos(opinionsReport);
//        }
//    }
//
//    @Override
//    public void updateEventOpinionLevel() {
//
//        List<Map<String, Object>> eventOpinionLevelList = new ArrayList<>();
//        List<Map<String, Object>> eventList = publicOpinionDao.getEventListWithoutOpinionLevel();
//
//        if (eventList.size() > 0){
//            for (Map<String, Object> map : eventList){
//                List<Map<String, Object>> tempList = publicOpinionDao.queryTopNOpinions(map.get("eventId").toString(), null, null, null);
//                Map<String, Object> tempMap = new HashMap<>();
//                tempMap.put("opinionLevel", "3");
//                tempMap.put("eventId", map.get("eventId"));
//                if (tempList.size() == 0){
//                    eventOpinionLevelList.add(tempMap);
//                } else {
//                    for (Map<String, Object> opinionMap : tempList){
//                        if (!ObjectUtils.isEmpty(opinionMap.get("pubTime"))){
//                            String beginTime = opinionMap.get("pubTime").toString() + " 00:00:00";
//                            String endTime = opinionMap.get("pubTime").toString() + " 23:59:59";
//                            List<Map<String, Object>> opinionList = publicOpinionDao.queryTopNOpinions(null, "10", beginTime, endTime);
//                            for (Map<String, Object> opinion: opinionList){
//                                if (!ObjectUtils.isEmpty(opinion.get("eventId")) && opinion.get("eventId").toString().equals(map.get("eventId"))){
//                                    tempMap.replace("opinionLevel", "1");
//                                }
//                            }
//                        }
//                    }
//                    if (tempMap.get("opinionLevel").toString().equals("3")){
//                        tempMap.replace("opinionLevel", "2");
//                    }
//                    eventOpinionLevelList.add(tempMap);
//                }
//            }
//        }
//        log.info(eventOpinionLevelList.toString());
//        if (eventOpinionLevelList.size() >0){
//            eventInfoDao.updateEventOpinionLevel(eventOpinionLevelList);
//        }
//    }
//
//    @Override
//    public void updateOpinionReportName() {
//
//        try{
//            List<Map<String, Object>> reportWithoutNameList = publicOpinionDao.queryPublicOpinionReportWithoutName();
//            // 查出名称没有格式化的数据
//            List<Map<String, Object>> reportWithNameList = new ArrayList<>();
//            List<Map<String, Object>> urlDontMatchList = new ArrayList<>();
//            String noNameReportIds = "";
//            if(reportWithoutNameList.size()>0){
//                for (Map<String, Object> tempMap : reportWithoutNameList){
//                    if (!ObjectUtils.isEmpty(tempMap.get("mediaName"))){
//                        reportWithNameList.add(tempMap);
//                    } else {
//                        noNameReportIds = noNameReportIds + ',' + tempMap.get("id").toString();
//                    }
//                }
//                log.info("reportWithNameList = " + reportWithNameList);
//            }
//            // 查出第一次格式化名字失败的数据
//            if (!org.springframework.util.StringUtils.isEmpty(noNameReportIds)){
//                urlDontMatchList = publicOpinionDao.queryPublicOpinionWithOtherUrl(noNameReportIds);
//                if (urlDontMatchList.size()> 0){
//                    for (Map<String, Object> map : urlDontMatchList){
//                        if (!org.springframework.util.StringUtils.isEmpty(map.get("mediaName"))){
//                            reportWithNameList.add(map);
//                        } else {
//                            map.replace("mediaName", "网页消息");
//                            reportWithNameList.add(map);
//                        }
//                    }
//                }
//                log.info("reportWithNameList = " + reportWithNameList);
////
////                if (reportWithNameList.size() >0){
////                    for (int i = 0; i< 8; i++){
////                        List<Map<String, Object>> list = reportWithNameList.subList(10000 * i, 10000*(i+1) < reportWithNameList.size()? 10000*(i+1) : reportWithNameList.size());
////                    }
////                }
//            }
//
//            if (reportWithNameList.size() > 0){
//                // 更新名字
//                publicOpinionDao.updateReportDomainName(reportWithNameList);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            log.error(e.toString());
//        }
//    }
//
//    @Override
//    public void savePublicOpinion() {
//        log.info("start savePublicOpinion...");
//        //获取未匹配事件的舆情列表
//        List<Map<String,Object>> opinions = publicOpinionDao.getPublicOpinionList();
//        if(CollectionUtils.isEmpty(opinions)){
//            return;
//        }
//        //获取事件标题
//        List<Map<String, String>> eventInfoTitle = publicOpinionDao.getEventInfoTitle();
//        List<Map<String, Object>> list = new ArrayList<>();
//
//        for(Map<String,Object> opinion:opinions) {
//            double similar=0;
//            String eventId="";
//            //获取舆情时间
//            String publicOpinionId = String.valueOf(opinion.get("publicOpinionId"));
//            String opinionTitle = String.valueOf(opinion.get("opinionTitle"));
//            String pubTime = String.valueOf(opinion.get("pubTime"));
//            if(StringUtils.isBlank(pubTime)){
//                continue;
//            }
//            long updateTimeStamp = DateUtil.getTimeStamp(pubTime,DateUtil.formatYMD);
//            for (Map<String, String> map : eventInfoTitle) {
//                String happen_time = map.get("happen_time");
//                String eventId1 = map.get("event_id");
//                String eventTitle = map.get("event_title");
//                if(StringUtils.isBlank(happen_time)){
//                    continue;
//                }
//                long happenTimeStamp = DateUtil.getTimeStamp(happen_time,DateUtil.formatYMDHms);
//                //搜索范围为事件上报时间前24小时及事件发生后48小时
//                if((updateTimeStamp>happenTimeStamp)?(updateTimeStamp-happenTimeStamp)<86400000 * 2:(happenTimeStamp-updateTimeStamp)<86400000){
//                    double similarScore = CosUtil.sim(eventTitle,opinionTitle);
//                    if(similarScore>similar){
//                        similar=similarScore;
//                        eventId=eventId1;
//                    }
//                }
//            }
//            if(StringUtils.isNotBlank(eventId)&&similar>0.25){
//                //保存数据
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("eventId",eventId);
//                map.put("publicOpinionId",publicOpinionId);
//                list.add(map);
//            }
//        }
//        if(list.size()>0){
//            //正式库
//            publicOpinionDao.updatePublicOpinionEventId(list);
//        }
//        log.info("end savePublicOpinion...");
//    }

}
