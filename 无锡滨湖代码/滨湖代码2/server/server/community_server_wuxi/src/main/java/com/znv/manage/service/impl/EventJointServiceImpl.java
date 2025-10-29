package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.EventBill;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.EventRequestUtil;
import com.znv.manage.dao.EventJointMapper;
import com.znv.manage.dao.EventManageMapper;
import com.znv.manage.service.EventJointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventJointServiceImpl implements EventJointService {

    //TODO:文档里是/wechatApp/api/user/getToken 但是没用
    public static final String TOKEN_URL = "userOrg/api/org/login";
    public static final String DRAW_BILL_URL = "wechatApp/api/wechat/flow/customCreateNewFlow";
    public static final String DEAL_MESSAGE_URL = "wechatApp/api/flow/multispanOrderDealPage";
    public static final String EVENT_LIST_URL = "wechatApp/api/wechat/flow/findFlowInfo";
    public static final String DISTRICT_LEVEL = "区市县";

    @Autowired
    EventJointMapper eventJointMapper;

    @Autowired
    EventManageMapper eventManageMapper;

    @Autowired
    RestTemplate restTemplate;

    @Value("${event_url3}")
    String eventUrl;

    @Override
    public Result getDepartmentAll() {
        Result result = new Result();
        try {
            JSONArray resultJson = new JSONArray();
            for (Map<String, Object> secondaryMap : eventJointMapper.queryAllSecondary()) {
                if (!DISTRICT_LEVEL.equals(secondaryMap.get("name").toString())) {
                    secondaryMap.put("children", eventJointMapper.queryDepartment(null, null, secondaryMap.get("name").toString(), null));
                } else {
                    secondaryMap.put("children", districtToTree(eventJointMapper.queryDepartmentLow()));
                }
                resultJson.add(secondaryMap);
            }
            result.setData(resultJson);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    public List<Map<String, Object>> districtToTree(List<Map<String, Object>> list) {
        List<Map<String, Object>> childList = new ArrayList<>();
        for (Map<String, Object> district : eventJointMapper.queryAllCounty()) {
            List<Map<String, Object>> tempList = list.stream().filter(map -> district.get("id").toString()
                    .equals(map.get("countyId").toString())).collect(Collectors.toList());
            if(!tempList.isEmpty()){
                district.put("children", tempList);
            }
            childList.add(district);
        }
        return childList;
    }

    @Override
    public Result drawBill(JSONObject param) {
        Result result = new Result();
        try {
            List<String> tags = new ArrayList<>();
            tags.add("其他");
            param.put("$event_tags$",tags);
            JSONObject body = EventRequestUtil.postRequest(restTemplate, eventUrl + DRAW_BILL_URL, param, eventUrl+TOKEN_URL);
            result.setData(body);

            if (body != null && "200".equals(body.getString("code"))) {
                //传参入库
                EventBill eventBill = new EventBill();
                eventBill.setBillJson(param.toString());
                eventBill.setIncomingFlowNo(param.getString("incomingflowno"));
                eventBill.setQuestionTitle(param.getString("questiontitle"));
                eventBill.setDepartments(param.getString("departments"));
                eventBill.setEventId(body.getString("data"));
                eventJointMapper.saveBill(eventBill);
                param.remove("departments");
            }
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryTitle(String eventId, String startDate, String endDate, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventJointMapper.queryTitle(eventId, startDate, endDate)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result dealMessage(JSONObject param) {
        Result result = new Result();
        try {
            result.setData(EventRequestUtil.postRequest(restTemplate, eventUrl + DEAL_MESSAGE_URL, param, eventUrl + TOKEN_URL));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result eventList(JSONObject param) {
        Result result = new Result();
        try {
            result.setData(EventRequestUtil.postRequest(restTemplate, eventUrl + EVENT_LIST_URL, param, eventUrl + TOKEN_URL));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryEventList(String beginTime,String endTime,Integer pageNum,Integer pageSize) {
        Result result = new Result();
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> list = eventManageMapper.queryEventInfoList(beginTime,endTime);
        result.setData(new PageInfo<>(list));
        return result;
    }

    @Override
    public Result queryEventStatisticsMap(String beginTime, String endTime) {
        Result result = new Result();
        List<Map<String,Object>> list = eventManageMapper.queryEventStatisticsMap(beginTime,endTime);
        result.setData(list);
        return result;
    }
}
