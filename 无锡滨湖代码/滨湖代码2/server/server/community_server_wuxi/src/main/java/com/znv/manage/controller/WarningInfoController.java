package com.znv.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.AqiServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: WarningInfoController
 * @Description:
 * @Author: ljx
 * @Date: 2022/09/08
 **/
@Slf4j
@RestController
@Api(tags = "预警信息")
@RequestMapping("/warningInfo")
public class WarningInfoController {


    @Value("${warningUrl:http://10.1.50.137:8092/commonApi/queryByGet}")
    private String warningUrl;

    @Value("${eventUrl:http://10.1.50.137:8092/event/12345/list}")
    private String eventUrl;

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AqiServiceImpl aqiService;

    @ApiOperation(value = "/预警信息数据")
    @GetMapping("/getInfo")
    public Result getInfo() {
        Result result = new Result();
        JSONObject retObj = new JSONObject();
        result.setData(retObj);
        JSONObject jsonObject;
        try {
            //空气质量预警
            JSONArray jsonArray207 = getRestemplate().getForObject(warningUrl + "?sqlId=207", JSONArray.class);
            if (jsonArray207 != null && jsonArray207.size() > 0) {
                jsonObject = jsonArray207.getJSONObject(0);
                retObj.put("空气质量预警", jsonObject.get("cnt"));
                int warnLevel = parseInt(jsonObject.getString("warn_level"));
                String warnLevelColor = "蓝色";
                if (warnLevel == 0) {
                    warnLevelColor = "蓝色";
                } else if (warnLevel == 1) {
                    warnLevelColor = "黄色";
                } else if (warnLevel == 2) {
                    warnLevelColor = "橙色";
                }
                retObj.put("空气质量预警等级颜色", warnLevelColor);
            }

            JSONArray jsonArray90 = getRestemplate().getForObject(warningUrl + "?sqlId=90", JSONArray.class);

            //灾害天气-暴雨预警
            //int byCount = getCount(jsonArray90, "暴雨预警") + getCount(jsonArray90, "暴雨预警信号");
            int byCount = getCount(jsonArray90, "暴雨预警");
            retObj.put("暴雨预警", byCount);
            retObj.put("暴雨预警等级颜色", getColor(jsonArray90, "暴雨预警"));

            //灾害天气-防汛预警
            //int fxCount = getCount(jsonArray90, "防汛应急响应") + getCount(jsonArray90, "防汛响应预警");
            int fxCount = getCount(jsonArray90, "防汛响应预警");
            retObj.put("防汛预警", fxCount);
            retObj.put("防汛响应预警等级颜色", getColor(jsonArray90, "防汛响应预警"));

            //自然灾害-地震预警
            int dzCount = getCount(jsonArray90, "地震预警");
            retObj.put("地震预警", dzCount);
            retObj.put("地震预警等级颜色", getColor(jsonArray90, "地震预警"));

            //自然灾害-山洪预警
            int shCount = getCount(jsonArray90, "山洪灾害预警");
            retObj.put("山洪预警", shCount);
            retObj.put("山洪灾害预警等级颜色", getColor(jsonArray90, "山洪灾害预警"));

            //燃气安全-今日预警
            JSONArray jsonArray241 = getRestemplate().getForObject(warningUrl + "?sqlId=241", JSONArray.class);
            for (Object obj : jsonArray241) {
                Map map = (Map) obj;
                if ("all".equals(map.get("type") + "")) {
                    retObj.put("今日预警", map.get("cnt"));
                }
            }

            //燃气安全-重大风险
            retObj.put("重大风险", 0);

            //城市交通-拥堵指数
            JSONArray jsonArray153 = getRestemplate().getForObject(warningUrl + "?sqlId=153", JSONArray.class);
            if (jsonArray153 != null && jsonArray153.size() > 0) {
                jsonObject = jsonArray153.getJSONObject(0);
                retObj.put("严重拥堵路段告警", jsonObject.get("yongdu_index"));
            }

            //城市交通-大客流预警
            int dklCount = getCount(jsonArray90, "地铁车站大客流告警");
            retObj.put("大客流预警", dklCount);

            //社会安全-群访集访
            retObj.put("群访集访", 0);

            //社会安全-刑事案件
            JSONArray jsonArray181 = getRestemplate().getForObject(warningUrl + "?sqlId=181", JSONArray.class);
            if (jsonArray181 != null && jsonArray181.size() > 0) {
                jsonObject = jsonArray181.getJSONObject(0);
                retObj.put("刑事案件", jsonObject.get("total_penal_cases"));
            }

            //AQI
            List<Map> aqiMap = aqiService.queryAqiInfo();
            if (aqiMap != null && aqiMap.size() > 0) {
                Map map = aqiMap.get(0);
                retObj.put("AQI", map.get("AQI"));
                retObj.put("空气质量", map.get("GradeDescription"));
                retObj.put("质量颜色", map.get("color"));
            }

            //[{"update_time":"2022-09-21 14:43:27","value":16380}]
            //12345来电总数
            JSONArray jsonArray245 = getRestemplate().getForObject(warningUrl + "?sqlId=245", JSONArray.class);
            if (jsonArray245 != null && jsonArray245.size() > 0) {
                jsonObject = jsonArray245.getJSONObject(0);
                retObj.put("12345来电总数", jsonObject.get("value"));
            }

            //[{"x":"咨询","y":10815},{"x":"投诉举报","y":8760},{"x":"求助","y":1453},{"x":"意见建议","y":378},{"x":"征集建言","y":40},{"x":"表扬","y":29}]
            //12345分类数据
            JSONArray jsonArray2 = getRestemplate().getForObject(warningUrl + "?sqlId=2", JSONArray.class);
            Map typeMap = new HashMap<>();
            if (jsonArray2 != null && jsonArray2.size() > 0) {
                for (Object obj : jsonArray2) {
                    Map map = (Map) obj;
                    typeMap.put(map.get("x"), map.get("y"));
                }
                retObj.put("12345事件类型", new JSONObject(typeMap));
            }
        } catch (Exception e) {
            result.setMessage(e.toString());
            log.error(e.toString());
        }

        return result;
    }

    private int getCount(JSONArray jsonArray, String key) {
        try {
            for (Object obj : jsonArray) {
                Map map = (Map) obj;
                if (key.equals(map.get("indexName") + "")) {
                    return (int) map.getOrDefault("count", 0);
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return 0;
    }

    private String getColor(JSONArray jsonArray, String key) {
        try {
            for (Object obj : jsonArray) {
                Map map = (Map) obj;
                if (key.equals(map.get("indexName") + "")) {
                    return map.get("riskLevel") + "";
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "";
    }


    @ApiOperation(value = "/查询空气质量")
    @GetMapping("/getApiInfo")
    public List<Map> getApiInfo() {
        return aqiService.queryAqiInfo();
    }

    @ApiOperation(value = "/查询12345事件列表")
    @GetMapping("/get12345EventList")
    public Result get12345EventList(@RequestParam(value = "pageNum") int pageNum,
                                    @RequestParam(value = "pageSize") int pageSize,
                                    @RequestParam(value = "type", required = false) String type) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(type)) {
                type = "";
            }
            JSONObject jsonObject = getRestemplate().getForObject(eventUrl + "?pageNum=" + pageNum + "&pageSize=" + pageSize + "&type=" + type, JSONObject.class);
            result.setData(jsonObject.getJSONObject("data"));
        } catch (Exception e) {
            result.setMessage(e.toString());
            log.error(e.toString());
        }
        return result;
    }

    @ApiOperation(value = "/查询12345事件详情")
    @GetMapping("/get12345EventDetail")
    public Result get12345EventDetail(@RequestParam(value = "pkId") String pkId, @RequestParam(value = "source") String source) {
        Result result = new Result();
        try {
            JSONObject jsonObject = getRestemplate().getForObject(eventUrl + "/detail?source=" + source + "&pkId=" + pkId, JSONObject.class);
            result.setData(jsonObject.getJSONObject("data"));
        } catch (Exception e) {
            result.setMessage(e.toString());
            log.error(e.toString());
        }
        return result;
    }

    @ApiOperation(value = "/查询值班体系")
    @GetMapping("/getDutyInfo")
    public Result getDutyInfo() {
        Result result = new Result();
        try {
            //增加token
            JSONArray jsonArray = getRestemplate().getForObject(warningUrl + "?sqlId=381&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjEzMDIyMTU3NjQxIiwiaWRDYXJkIjoiMzQwMTIxMTk5NzA4MzE2MTk4IiwiaXNzIjoiMTIzIiwid", JSONArray.class);
            result.setData(jsonArray);
        } catch (Exception e) {
            result.setMessage(e.toString());
            log.error(e.toString());
        }
        return result;
    }

    public static RestTemplate getRestemplate() {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    public int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }
}