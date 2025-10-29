package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ShzldldgdBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.ShzldldgdDao;
import com.znv.manage.service.ShzldldgdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class ShzldldgdServiceImpl implements ShzldldgdService {

    @Autowired
    ShzldldgdDao shzldldgdDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/zzdld/ajxx";

    @Override
    public int getData() {
        int i = 0;
        int pageNumber = 0;
        boolean flag = true;
        do {
            pageNumber = pageNumber + 1;
            Map<String, String> map = new HashMap<>();
            map.put("starttime", DateUtil.getStringDateShort());
            map.put("pageSize", "1000");
            map.put("pageNumber", String.valueOf(pageNumber));
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/zzdld/ajxx object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            if (dataArr.size() > 0) {
                i += insertShzldldgdList(dataArr);
            } else {
                flag = false;
            }
        } while (flag);
        return i;
    }

    @Override
    public Result getShzldldgd(String beginTime, String endTime, Integer limit, String EVENTSUBJECTNAME, String precinctname) {
        Result result = new Result();
        List<Map<String, Object>> list = shzldldgdDao.queryShzldldgdList(beginTime, endTime, limit, EVENTSUBJECTNAME, precinctname);
        Integer count = shzldldgdDao.queryShzldldgdCount(beginTime, endTime, limit, EVENTSUBJECTNAME, precinctname);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        result.setData(map);
        return result;
    }

    @Override
    public Result getShzldldgdType(String precinctname) {
        Result result = new Result();
        List<Map<String, Object>> list = shzldldgdDao.queryShzldldgdType(precinctname);
        result.setData(list);
        return result;
    }

    @Override
    public Result getShzldldgdStatusCount(String beginTime, String endTime, String precinctname) {
        Result result = new Result();
        int all = shzldldgdDao.queryShzldldgdStatusAll(beginTime, endTime, precinctname);
        int unDeal = shzldldgdDao.queryShzldldgdStatusUnDeal(beginTime, endTime, precinctname);
        int deal = shzldldgdDao.queryShzldldgdStatusDeal(beginTime, endTime, precinctname);
        Map<String, Object> map = new HashMap<>();
        map.put("all", all);
        map.put("unDeal", unDeal);
        map.put("deal", deal);
        result.setData(map);
        return result;
    }

    public int insertShzldldgdList(List<JSONObject> list) {
        int i = 0;
        List<ShzldldgdBean> insertShzldldgd = new ArrayList();
        List<ShzldldgdBean> updateShzldldgd = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertShzldldgdList list.size为0");
            } else {
                for (JSONObject jsonObject : list) {
                    ShzldldgdBean shzldldgdBean = new ShzldldgdBean();
                    shzldldgdBean.setEVENTPOSDESC(jsonObject.getString("EVENT_POS_DESC"));
                    shzldldgdBean.setEVENTOCCURTIME(jsonObject.getString("EVENT_OCCUR_TIME"));
                    shzldldgdBean.setEVENTATTACHLIST(jsonObject.getString("EVENT_ATTACH_LIST"));
                    shzldldgdBean.setCLOSEDTIME(jsonObject.getString("CLOSED_TIME"));
                    shzldldgdBean.setEVENTCODE(jsonObject.getString("EVENT_CODE"));
                    shzldldgdBean.setEVENTTYPENAME(jsonObject.getString("EVENT_TYPE_NAME"));
                    shzldldgdBean.setEXTENDED(jsonObject.getString("EXTENDED"));
                    shzldldgdBean.setEVENTNAME(jsonObject.getString("EVENT_NAME"));
                    shzldldgdBean.setEVENTDESC(jsonObject.getString("EVENT_DESC"));
                    shzldldgdBean.setRPTCHANNELNAME(jsonObject.getString("RPT_CHANNEL_NAME"));
                    shzldldgdBean.setEVENTDISPDEMANDDEADLINE(jsonObject.getString("EVENTDISP_DEMAND_DEADLINE"));
                    shzldldgdBean.setEVENTLEVELNAME(jsonObject.getString("EVENT_LEVEL_NAME"));
                    shzldldgdBean.setEVENTGRIDNAME(jsonObject.getString("EVENT_GRID_NAME"));
                    shzldldgdBean.setEVENTSTATUSNAME(jsonObject.getString("EVENT_STATUS_NAME"));
                    shzldldgdBean.setRPTTYPENAME(jsonObject.getString("RPT_TYPE_NAME"));
                    shzldldgdBean.setEVENTDAMGLEVELNAME(jsonObject.getString("EVENT_DAMG_LEVEL_NAME"));
                    shzldldgdBean.setEVENTSUBJECTNAME(jsonObject.getString("EVENT_SUBJECT_NAME"));
                    shzldldgdBean.setEVENTID(jsonObject.getString("EVENT_ID"));
                    shzldldgdBean.setEVENTPOSX(jsonObject.getString("EVENT_POS_X"));
                    shzldldgdBean.setEVENTPOSY(jsonObject.getString("EVENT_POS_Y"));
                    shzldldgdBean.setEVENTGRIDID(jsonObject.getString("EVENT_GRID_ID"));
                    shzldldgdBean.setEVENTREPDEPARTNAME(jsonObject.getString("EVENT_REP_DEPART_NAME"));
                    shzldldgdBean.setEVENTATTACHID(jsonObject.getString("EVENT_ATTACH_ID"));
                    shzldldgdBean.setRPTTIME(jsonObject.getString("RPT_TIME"));
                    insertShzldldgd.add(shzldldgdBean);
//                    if (updateFlag(shzldldgdBean.getEVENTID())) {
//                        log.info("insertShzldldgdList eventId 已存在:" + jsonObject.toJSONString());
//
//                    } else {
//                        log.info("insertShzldldgdList eventId 不存在:" + jsonObject.toJSONString());
//                        insertShzldldgd.add(shzldldgdBean);
//                    }
                }
            }
            if (insertShzldldgd.size() != 0) {
                i += shzldldgdDao.insertShzldldgdList(insertShzldldgd);
            }
//            if (updateShzldldgd.size() != 0) {
//                try {
//                    i += shzldldgdDao.updateShzldldgdList(updateShzldldgd);
//                } catch (Exception e) {
//                    log.error("updateShzldldgdList error " + updateShzldldgd.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

//    public boolean updateFlag(String eventId) {
//        boolean flag = false;
//        int count = shzldldgdDao.queryShzldldgdListByEventId(eventId);
//        if (count > 0) {
//            flag = true;
//        }
//        return flag;
//    }

}
