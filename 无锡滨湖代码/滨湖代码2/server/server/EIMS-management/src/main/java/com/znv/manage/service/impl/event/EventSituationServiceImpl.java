package com.znv.manage.service.impl.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.dao.event.EventSituationDao;
import com.znv.manage.service.event.EventSituationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TCfgResourceList表服务实现类
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:14
 */
@Service
@Slf4j
public class EventSituationServiceImpl implements EventSituationService {
    @Resource
    private EventSituationDao eventSituationDao;

    @Override
    public Result quartAnalysis() {
        Result result = new Result();
        try {
            Map map = new HashMap();
            List<Map<String, Object>> list = eventSituationDao.quartAnalysis();
            map.put("list", list);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result eventLevelCount(String beginTime, String endTime) {
        Result result = new Result();
        try {
            Map map = new HashMap();
            List<Map<String, Object>> list = eventSituationDao.eventLevelCount(beginTime, endTime);
            map.put("list", list);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result eventStreetCount(String beginTime, String endTime) {
        Result result = new Result();
        try {
            Map map = new HashMap();
            List<Map<String, Object>> list = eventSituationDao.eventStreetCount(beginTime, endTime);
            map.put("list", list);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result burstEventTotalCount(String beginTime, String endTime, String type) {
        Result result = new Result();
        try {
            Map map = new HashMap();
            String lastBeginTime = DateUtils.getTimeDay(beginTime, -1);
            String lastEndTime = DateUtils.getTimeDay(endTime, -1);
            List<String> hbs = DateUtils.getHBDay(beginTime, endTime);
            List<Map<String, Object>> list = eventSituationDao.burstEventTotalCount(beginTime, endTime);
            List<Map<String, Object>> lastList = eventSituationDao.burstEventTotalCount(lastBeginTime, lastEndTime);
            List<Map<String, Object>> hbList = eventSituationDao.burstEventTotalCount(hbs.get(0), hbs.get(1));
            if ("1".equals(type)) {
                for (Map data : list) {
                    String typeName = data.get("typeName").toString();
                    for (Map lastData : lastList) {
                        if (lastData.get("typeName").equals(typeName)) {
                            data.put("totalCountRing", RingRatio(Integer.valueOf(data.get("totalCount").toString()),
                                    Integer.valueOf(lastData.get("totalCount").toString())));
                            data.put("deathCountRing", RingRatio(Integer.valueOf(data.get("deathCount").toString()),
                                    Integer.valueOf(lastData.get("deathCount").toString())));
                            data.put("hurtCountRing", RingRatio(Integer.valueOf(data.get("hurtCount").toString()),
                                    Integer.valueOf(lastData.get("hurtCount").toString())));
                        }
                    }
                }
            } else if ("2".equals(type)) {
                for (Map data : list) {
                    String typeName = data.get("typeName").toString();
                    for (Map hbData : hbList) {
                        if (hbData.get("typeName").equals(typeName)) {
                            data.put("totalCountRing", RingRatio(Integer.valueOf(data.get("totalCount").toString()),
                                    Integer.valueOf(hbData.get("totalCount").toString())));
                            data.put("deathCountRing", RingRatio(Integer.valueOf(data.get("deathCount").toString()),
                                    Integer.valueOf(hbData.get("deathCount").toString())));
                            data.put("hurtCountRing", RingRatio(Integer.valueOf(data.get("hurtCount").toString()),
                                    Integer.valueOf(hbData.get("hurtCount").toString())));
                        }
                    }
                }
            }
            map.put("list", list);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result burstEventTypeCount(String beginTime, String endTime, String type) {
        Result result = new Result();
        try {
            Map map = new HashMap();
            String lastBeginTime = DateUtils.getTimeDay(beginTime, -1);
            String lastEndTime = DateUtils.getTimeDay(endTime, -1);
            List<Map<String, Object>> list = eventSituationDao.burstEventTypeCount(beginTime, endTime, type);
            List<Map<String, Object>> lastList = eventSituationDao.burstEventTypeCount(lastBeginTime, lastEndTime, type);
            for (Map data : list) {
                String typeName = data.get("typeName").toString();
                for (Map lastData : lastList) {
                    if (lastData.get("typeName").equals(typeName)) {
                        data.put("totalCountRing", RingRatio(Integer.valueOf(data.get("totalCount").toString()),
                                Integer.valueOf(lastData.get("totalCount").toString())));
                    }
                }
            }
            map.put("list", list);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result burstEventTypeCountAll(String beginTime, String endTime) {
        Result result = new Result();
        try {
            Map map = new HashMap();
            List<Map<String, Object>> dataList = new ArrayList<>();
            List<Map<String, Object>> typeList = eventSituationDao.getTypeList();
            for (Map data : typeList) {
                Map dataMap = new HashMap();
                List<Map<String, Object>> list = eventSituationDao.burstEventTypeCount(beginTime, endTime, data.get("name").toString());
                dataMap.put(data.get("name").toString(), list);
                dataList.add(dataMap);
            }
            map.put("list", dataList);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    public String RingRatio(Integer count, Integer lastCount) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        String percent;
        if (lastCount > 0) {
            percent = decimalFormat.format((float) (count - lastCount) / (float) (lastCount));
        } else {
            percent = "0.00%";
        }
        return percent;
    }

    @Override
    public Result burstEventParentType(String beginTime, String endTime) {
        Result result = new Result();
        try {
            List<Map<String, Object>> typeList = eventSituationDao.getTypeList();
            List<Map<String, Object>> resultList = new ArrayList<>();
            Long total = 0L;
            for (Map<String, Object> map : typeList) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("type", map.get("name"));
                Long num = eventSituationDao.burstEventByType(beginTime, endTime, map.get("id").toString());
                resultMap.put("num", num);
                resultList.add(resultMap);
                total += num;
            }
            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put("type", "总计");
            totalMap.put("num", total);
            resultList.add(totalMap);
            result.setData(resultList);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
