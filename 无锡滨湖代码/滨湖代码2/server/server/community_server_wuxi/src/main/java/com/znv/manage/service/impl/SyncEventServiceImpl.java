package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.SyncEvent;
import com.znv.manage.common.bean.SyncEventContent;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.EventRequestUtil;
import com.znv.manage.dao.*;
import com.znv.manage.service.SyncEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author znv
 * @ClassName:
 * @Description: 服务层
 * @date 2018/5/18 14:57
 */
@Service
public class SyncEventServiceImpl implements SyncEventService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SyncEventServiceImpl.class);

    @Autowired
    SyncEventDao syncEventDao;

    @Autowired
    CommunicationMapper communicationMapper;

    @Autowired
    EventManageMapper eventManageMapper;

    @Autowired
    private FusionVideoDao fusionVideoDao;


    @Value("${event_url}")
    private String IP;

    @Value("${event_url2}")
    private String IP2;


    @Value("${event_url3}")
    private String IP3;

    @Value("${event_user}")
    private String USERNAME;

    private static String tokenUrl = "userOrg/api/org/login";

    private static String eventUrl = "wechatApp/api/wechat/flow/findFlowInfo";

    private static String contentUrl = "itsmApp/visData/getFlowLogVisNew?type=1&flowNo=";

    private static String flowUrl = "wechatApp/api/flow/orderDealPage";

    private static String detailUrl = "project/task/queryTaskByParam?flowNo=";

    private static String contentUrlOne = "RE0001";

    private static String contentUrlTwo = "RE0002";

    private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final List<String> MAJOR_EVENT_TYPES;

    static {
        MAJOR_EVENT_TYPES = new ArrayList<>();
        MAJOR_EVENT_TYPES.add("较大");
        MAJOR_EVENT_TYPES.add("重大");
        MAJOR_EVENT_TYPES.add("特别重大");
    }

    @Autowired
    private FusionVideoServiceImpl fusionVideoService;

    @Autowired
    private XyxcUserDao xyxcUserDao;

    /**
     * 同步数据
     *
     * @return
     */
    @Override
    public int syncEvent(JSONObject dataFilter,JSONObject dateRange,String modelKey) {
        int pageNumber = 0;
        int pageSize = 500;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                RestTemplate restTemplate = new RestTemplate();
                JSONObject param = new JSONObject();
                JSONObject data = new JSONObject();
                param.put("mapVo",data);
                data.put("page", pageNumber);
                data.put("size", pageSize);
                data.put("dataFilter", dataFilter);
                data.put("dateRange", dateRange);
                data.put("modelKey", modelKey);
                HttpHeaders headers = new HttpHeaders();
                headers.add("token", EventRequestUtil.getToken(restTemplate, IP3 + tokenUrl));
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity request = new HttpEntity(param, headers);
                JSONObject jsonObject = restTemplate.postForObject(IP3 + eventUrl, request, JSONObject.class);
                JSONObject object = jsonObject.getJSONObject("data");
                JSONArray array = object.getJSONArray("rows");
                logger.info("event array:"+array);
                if (!CollectionUtils.isEmpty(array) || (!array.isEmpty())) {
                    i += insertEventSync(array);
                } else {
                    logger.info("insertEventData list.size为0");
                }
                if (array.size() < pageSize) {
                    flag = false;
                }
            } while (flag);

        } catch (Exception e) {
            logger.error("getData res error ： " + e.getMessage());
        }
        return i;
    }

    @Override
    public int syncEventContentOne() {
        List<SyncEventContent> syncEventContents = this.getDealContent(contentUrlOne);
        if (syncEventContents.size() != 0) {
            logger.info("RE001数据获取" + syncEventContents);
            for (SyncEventContent syncEventContent : syncEventContents) {
                logger.info("RE001数据插入1" + syncEventContent);
                if (!queryEventContentReExist(syncEventContent)) {
                    syncEventContent.setFlowNo(contentUrlOne);
                    logger.info("RE001数据插入" + syncEventContent);
                    int count = syncEventDao.insertEventDealContentSync(syncEventContent);
                    logger.info("RE001数据插入数量" + count);
                }
            }
        }
        return syncEventContents.size();
    }

    @Override
    public int syncEventContentTwo() {
        List<SyncEventContent> syncEventContents = this.getDealContent(contentUrlTwo);
        if (syncEventContents.size() != 0) {
            logger.info("RE002数据获取" + syncEventContents);
            for (SyncEventContent syncEventContent : syncEventContents) {
                if (!queryEventContentReExist(syncEventContent)) {
                    logger.info("RE002数据插入" + syncEventContent);
                    syncEventContent.setFlowNo(contentUrlTwo);
                    int count = syncEventDao.insertEventDealContentSync(syncEventContent);
                    logger.info("RE002数据插入数量" + count);
                }
            }
        }
        return syncEventContents.size();
    }

    public List<SyncEventContent> getDealContent(String id) {
        logger.info("获取处置信息");
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        JSONObject jsonObject = restTemplate.postForObject(IP + contentUrl + id, request, JSONObject.class);
        JSONObject object = jsonObject.getJSONObject("data").getJSONObject("datas");
        JSONArray array = object.getJSONArray("rows");
        logger.info("处置信息数据列--" + array);
        List<SyncEventContent> list = JSON.parseArray(String.valueOf(array), SyncEventContent.class);
        return list;
    }

    //襄阳
    public JSONObject getDealContentForWeb(String id) {
        logger.info("获取处置信息");
        JSONObject result = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", this.handleToken());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = restTemplate.postForObject(IP + contentUrl + id, request, JSONObject.class);
        } catch (RestClientException e) {
            logger.info("获取处置信息异常" + e.toString());
            e.printStackTrace();
        }
        if (!jsonObject.isEmpty()) {
            result.put("rows", jsonObject.getJSONObject("data").getJSONObject("datas").getJSONArray("rows"));
        }
        return result;
    }

    //成都
    public JSONObject getDealContentForWeb2(String id) {
        logger.info("获取处置信息");
        JSONObject result = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        JSONObject mapVo = new JSONObject();
        mapVo.put("actionType", "");
        mapVo.put("page", 1);
        mapVo.put("size", 999);
        mapVo.put("operationContent", "");
        msg.put("mapVo", mapVo);
        msg.getJSONObject("mapVo").put("flowNo", id);
        logger.info("查询参数：" + msg);
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", this.handleToken());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = restTemplate.postForObject(IP + flowUrl, request, JSONObject.class);
        } catch (RestClientException e) {
            logger.info("获取处置信息异常" + e.toString());
            e.printStackTrace();
        }
        if (!jsonObject.isEmpty()) {
            result.put("rows", jsonObject.getJSONObject("data").getJSONArray("rows"));
        }
        return result;
    }


    @Override
    public String handleToken() {
        String token = "";
        try {
            RestTemplate restTemplate = new RestTemplate();
            JSONObject msg = new JSONObject();
//            msg.put("loginType", "sso");
            msg.put("username", USERNAME);
            msg.put("password", "MTIzNDU2");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(IP + tokenUrl, request, JSONObject.class);
            logger.info("获取token结果:" + jsonObject);
            JSONObject data = jsonObject.getJSONObject("data");
            token = data.getString("token");
        } catch (Exception e) {
            logger.error("获取事件token异常:" + e.getMessage());
        }
        return token;
    }

    @Override
    public Object queryTaskByParam(String flowNo) {
        JSONArray result = new JSONArray();
        logger.info("获取处置信息详情");
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", this.handleToken());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        try {
            JSONArray jsonArray = restTemplate.postForObject(IP2 + detailUrl + flowNo, request, JSONArray.class);
            if (!CollectionUtils.isEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String mobile = jsonObject.getString("mobile");
                    if (!StringUtils.isEmpty(mobile)) {
                        List<Map<String, Object>> list = xyxcUserDao.queryUserInfo2(mobile, null, null, null);
                        if (!CollectionUtils.isEmpty(list)) {
                            jsonObject.put("userName", list.get(0).get("name"));
                            jsonObject.put("positions", list.get(0).get("positions"));
                        }
                    }
                    result.add(jsonObject);
                }
            }
            return result;
        } catch (RestClientException e) {
            logger.info("获取处置信息详情异常" + e.toString());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public List<String> queryTaskUserIdsByFlowNo(String flowNo) {
        List<String> mobileList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", this.handleToken());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        try {
            JSONArray jsonArray = restTemplate.postForObject(IP2 + detailUrl + flowNo, request, JSONArray.class);
            if (!CollectionUtils.isEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String mobile = jsonObject.getString("mobile");
                    if (!StringUtils.isEmpty(mobile)) {
                        mobileList.add(mobile);
                    }
                }
            }

        } catch (RestClientException e) {
            logger.info("获取处置信息详情异常" + e.toString());
        }
        return mobileList;
    }

    private static String getAreaCode(JSONObject event, List<Map<String, String>> list) {
        String areaCode = "";
        for (Map<String, String> map : list) {
            if (map.get("name").equals(event.getString("city"))) {
                return map.get("areaCode");
            }
        }
        return areaCode;
    }


    public int insertEventSync(JSONArray list) {
        int i = 0;
        if (list.size() != 0) {
            i += this.eventManageMapper.insertEventSync(list);
        }
        return i;
    }

    public void dealEventTypeConf(String eventId, String eventTypeId) {
        List<Map> list = syncEventDao.queryEventTypeConf(eventTypeId);
        syncEventDao.insertEventLeffConf(eventId, list);
    }

    public boolean queryEventExist(SyncEvent syncEvent) {
        boolean flag = false;
        int count = syncEventDao.queryEventExist(syncEvent);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    public boolean queryEventContentExist(SyncEventContent syncEventContent) {
        boolean flag = false;
        int count = syncEventDao.queryEventContentExist(syncEventContent);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    public boolean queryEventContentReExist(SyncEventContent syncEventContent) {
        boolean flag = false;
        int count = syncEventDao.queryEventContentReExist(syncEventContent);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    /*public boolean queryDealExist(SyncEvent syncEvent) {
        boolean flag = false;
        int count = syncEventDao.queryDealExist(syncEvent);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }*/
}
