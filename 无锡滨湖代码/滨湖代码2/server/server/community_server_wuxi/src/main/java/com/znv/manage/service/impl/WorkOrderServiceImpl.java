package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.WorkOrderBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.GpsUtils;
import com.znv.manage.dao.WorkOrderDao;
import com.znv.manage.service.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    WorkOrderDao workOrderDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/12345/t_12345_cinfo";

    @Override
    public int getData() {
        int i = 0;
        int pageNumber = 0;
        boolean flag = true;
        String startDate = DateUtil.getStringDateShort();
        do {
            pageNumber = pageNumber + 1;
            Map<String, String> map = new HashMap<>();
            map.put("startdate", startDate);
            map.put("pageSize", "1000");
            map.put("pageNumber", String.valueOf(pageNumber));
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info(startDate + "getData /service/api/12345/t_12345_cinfo object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            if (dataArr.size() > 0) {
                i += insertWorkOrderList(dataArr);
            } else {
                flag = false;
            }
        } while (flag);
        return i;
    }

    @Override
    public int syncWorkOrder(String syncDate) {
        int i = 0;
        int pageNumber = 0;
        boolean flag = true;
        do {
            pageNumber = pageNumber + 1;
            Map<String, String> map = new HashMap<>();
            map.put("startdate", syncDate);
            map.put("pageSize", "1000");
            map.put("pageNumber", String.valueOf(pageNumber));
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info(syncDate + "getData /service/api/12345/t_12345_cinfo object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            if (dataArr.size() > 0) {
                i += insertWorkOrderList(dataArr);
            } else {
                flag = false;
            }
        } while (flag);
        return i;
    }


    @Override
    public Result getWorkOrder(String beginTime, String endTime, Integer limit, String precinctName) {
        Result result = new Result();
        List<Map<String, Object>> list = workOrderDao.queryWorkOrderList(beginTime, endTime, null, limit, precinctName);
        Integer count = workOrderDao.queryWorkOrderCount(beginTime, endTime, null, limit, precinctName);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        result.setData(map);
        return result;
    }

    public int insertWorkOrderList(List<JSONObject> list) {
        int i = 0;
        List<WorkOrderBean> insertWorkOrder = new ArrayList();
        List<WorkOrderBean> updateWorkOrder = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertWorkOrderList list.size为0");
            } else {
                for (JSONObject jsonObject : list) {
                    WorkOrderBean workOrderBean = new WorkOrderBean();
                    workOrderBean.setEvlrelate(jsonObject.getString("evlrelate"));
                    workOrderBean.setEvlresult(jsonObject.getString("evlresult"));
                    workOrderBean.setSerialnum(jsonObject.getString("serialnum"));
                    workOrderBean.setCstatus(jsonObject.getString("cstatus"));
                    workOrderBean.setFeedbackresult(jsonObject.getString("feedbackresult"));
                    workOrderBean.setRqstsource(jsonObject.getString("rqstsource"));
                    workOrderBean.setRowguid(jsonObject.getString("rowguid"));
                    workOrderBean.setCagouguid(jsonObject.getString("cagouguid"));
                    workOrderBean.setCagou(jsonObject.getString("cagou"));
                    workOrderBean.setRqsttime(jsonObject.getString("rqsttime"));
                    workOrderBean.setFinishtime(jsonObject.getString("finishtime"));
                    workOrderBean.setRqstnumber(jsonObject.getString("rqstnumber"));
                    workOrderBean.setAccordtypefive(jsonObject.getString("accordtypefive"));
                    workOrderBean.setCagousub(jsonObject.getString("cagousub"));
                    workOrderBean.setEvlresult1(jsonObject.getString("evlresult1"));
                    workOrderBean.setAccordtypefour(jsonObject.getString("accordtypefour"));
                    workOrderBean.setIssecret(jsonObject.getString("issecret"));
                    workOrderBean.setEnttime(jsonObject.getString("enttime"));
                    workOrderBean.setAccordtypeone(jsonObject.getString("accordtypeone"));
                    workOrderBean.setCagousubguid(jsonObject.getString("cagousubguid"));
                    workOrderBean.setAccordtypethree(jsonObject.getString("accordtypethree"));
                    workOrderBean.setRqstarea(jsonObject.getString("rqstarea"));
                    workOrderBean.setAccordtypesix(jsonObject.getString("accordtypesix"));
                    workOrderBean.setReplaytype(jsonObject.getString("replaytype"));
                    workOrderBean.setIsimpt(jsonObject.getString("isimpt"));
                    workOrderBean.setLinknumber(jsonObject.getString("linknumber"));
                    workOrderBean.setRqstsex(jsonObject.getString("rqstsex"));
                    workOrderBean.setAccordtypetwo(jsonObject.getString("accordtypetwo"));
                    workOrderBean.setRqsttype(jsonObject.getString("rqsttype"));
                    workOrderBean.setRqstcontent(jsonObject.getString("rqstcontent"));
                    workOrderBean.setManagetype(jsonObject.getString("managetype"));
                    workOrderBean.setRqsttitle(jsonObject.getString("rqsttitle"));
                    workOrderBean.setAnswercontent(jsonObject.getString("answercontent"));
                    workOrderBean.setLinknumber2(jsonObject.getString("linknumber2"));
                    workOrderBean.setRqstperson(jsonObject.getString("rqstperson"));
                    workOrderBean.setRqstaddress(jsonObject.getString("rqstaddress"));
                    workOrderBean.setRqstareatwo(jsonObject.getString("rqstareatwo"));
                    workOrderBean.setExtendnumber(jsonObject.getString("extendnumber"));
                    workOrderBean.setExtendnumber2(jsonObject.getString("extendnumber2"));
                    workOrderBean.setAccordtypeseven(jsonObject.getString("accordtypeseven"));
                    workOrderBean.setOufinishtime(jsonObject.getString("oufinishtime"));
                    workOrderBean.setCollect_time(jsonObject.getString("collect_time"));
                    workOrderBean.setEvlway(jsonObject.getString("evlway"));

                    boolean updateFlag = updateFlag(workOrderBean.getSerialnum());
                    if (!updateFlag) {
                        String address = jsonObject.getString("rqstaddress");
                        if (address != null && !"".equals(address)) {
                            Map<String, String> gpsMap = GpsUtils.getGps("nanjing", address);
                            String gpsx = "";
                            String gpsy = "";
                            if (!CollectionUtils.isEmpty(gpsMap)) {
                                double[] gps = GpsUtils.gcj02_To_Gps84(Double.valueOf(gpsMap.get("lat")),
                                        Double.valueOf(gpsMap.get("lon")));
                                // 这里假设对应单元格原来的类型也是String类型
                                gpsx = String.format("%.6f", gps[1]);
                                gpsy = String.format("%.6f", gps[0]);
                                workOrderBean.setGpsx(gpsx);
                                workOrderBean.setGpsy(gpsy);
                            }
                        }
                    }

                    if (updateFlag) {
                        log.info("insertWorkOrderList serialnum 已存在:" + jsonObject.toJSONString());
                        updateWorkOrder.add(workOrderBean);
                    } else {
                        log.info("insertWorkOrderList serialnum 不存在:" + jsonObject.toJSONString());
                        insertWorkOrder.add(workOrderBean);
                    }
                }
            }
            if (insertWorkOrder.size() != 0) {
                i += workOrderDao.insertWorkOrderList(insertWorkOrder);
            }
            if (updateWorkOrder.size() != 0) {
                try {
                    i += workOrderDao.updateWorkOrderList(updateWorkOrder);
                } catch (Exception e) {
                    log.error("updateWorkOrderList error " + updateWorkOrder.toString() + " exception : " + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public boolean updateFlag(String serialnum) {
        boolean flag = false;
        int count = workOrderDao.queryWorkOrderCount(null, null, serialnum, null, null);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}
