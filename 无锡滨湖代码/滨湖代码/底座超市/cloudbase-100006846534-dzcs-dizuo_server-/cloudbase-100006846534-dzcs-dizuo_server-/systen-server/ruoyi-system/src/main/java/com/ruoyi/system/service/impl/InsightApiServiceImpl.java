package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.ServerUrlCon;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.domain.InsightFlow;
import com.ruoyi.system.domain.vo.InsightRequestVo;
import com.ruoyi.system.domain.vo.InsightResponseVo;
import com.ruoyi.system.service.ApiRequestService;
import com.ruoyi.system.service.IInsightApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InsightApiServiceImpl implements IInsightApiService {

    @Resource
    ApiRequestService apiRequestService;

    @Resource
    private RedisCache redisCache;


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public JSONArray getOnePersonMultiplyComplaints(JSONObject obj) {
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().size(999).build());
        List<InsightFlow> rows = JSONArray.parseArray(jsonObject.get("rows").toString(), InsightFlow.class);
        Map<String, Integer> collect = rows.stream().filter(x -> x.getNickName() != null)
                .collect(Collectors.groupingBy(InsightFlow::getNickName, Collectors.summingInt(x -> 1)));
        List<InsightResponseVo.InsightResponseData> returnList =
                collect.entrySet().stream().sorted(Comparator.comparingInt(x -> -x.getValue())).limit(5)
                        .map(x -> new InsightResponseVo.InsightResponseData(x.getKey().charAt(0) + "*", x.getValue())).collect(Collectors.toList());
        return new JSONArray(returnList);
    }

    @Override
    public JSONArray getFlowsClassified() {
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().size(999).build());
        List<InsightFlow> rows = JSONArray.parseArray(jsonObject.get("rows").toString(), InsightFlow.class);
        Map<String, Integer> collect = rows.stream().filter(x -> x.getClass1() != null)
                .collect(Collectors.groupingBy(InsightFlow::getClass1, Collectors.summingInt(x -> 1)));
        List<InsightResponseVo.InsightResponseData> returnList =
                collect.entrySet().stream().sorted(Comparator.comparingInt(x -> -x.getValue())).limit(5)
                        .map(x -> new InsightResponseVo.InsightResponseData(x.getKey(), x.getValue())).collect(Collectors.toList());
        return new JSONArray(returnList);
    }

    @Override
    public JSONArray getOneDepartmentMultiplyComplaints() {
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().size(999).modelKey("SUB_12345").build());
        List<InsightFlow> rows = JSONArray.parseArray(jsonObject.get("rows").toString(), InsightFlow.class);
        Map<String, Integer> collect = rows.stream().filter(x -> x.getDepartment() != null)
                .collect(Collectors.groupingBy(x -> x.getDepartment().split("/").length > 1 ? x.getDepartment().split("/")[1] : x.getDepartment(), Collectors.summingInt(x -> 1)));
        List<InsightResponseVo.InsightResponseData> returnList =
                collect.entrySet().stream().sorted(Comparator.comparingInt(x -> -x.getValue())).limit(5)
                        .map(x -> new InsightResponseVo.InsightResponseData(x.getKey(), x.getValue())).collect(Collectors.toList());
        return new JSONArray(returnList);
    }

    @Override
    public JSONArray getComplaintsByHours(int max, int min) {
        int hour = Math.max(Math.min(LocalDateTime.now().getHour(), max), min);
        final int[] hourArray = new int[]{hour - 6, hour - 5, hour - 4, hour - 3, hour - 2, hour - 1, hour};
        ArrayList<Integer> counts = new ArrayList<>();
        for (int i = 0; i < hourArray.length; i++) {
            int finalI = i;
            JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().dateRange(new JSONObject() {{
                put("creatDate", getTimeString(hourArray[finalI], hourArray[finalI]));
            }}).build());
            Integer count = jsonObject.getInteger("total");
            counts.add(count);
        }
        ArrayList<InsightResponseVo.InsightResponseData> returnList = new ArrayList<>();
        for (int i = 0; i < counts.size(); i++) {
            returnList.add(new InsightResponseVo.InsightResponseData(hourArray[i] + ":00", counts.get(i)));
        }
        return new JSONArray(returnList);
    }

    @Override
    public JSONArray getComplaintsCountInWeek() {
        ArrayList<InsightResponseVo.InsightResponseData> counts = new ArrayList<>();
        for (int i = 30; i >= 0; i--) {
            LocalDate ld = LocalDate.now().minusDays(i);
            JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().dateRange(new JSONObject() {{
                put("creatDate", getTimeString(ld));
            }}).build());
            Integer count = (Integer) jsonObject.get("total");
            counts.add(new InsightResponseVo.InsightResponseData(ld.format(DateTimeFormatter.ofPattern("MM/dd")), count));
        }
        return new JSONArray(counts);
    }

    @Override
    public JSONObject getTotalFlow() {
        JSONObject jsonObject1 = getApiFlowJSONObject(MapVoBuilder().dateRange(null).build());
        Integer aiTotal = redisCache.getCacheObject("AITotal");
        JSONObject jsonObject2 = getApiFlowJSONObject(MapVoBuilder().modelKey("CHENGYUN_MAIN").dateRange(null).build());
        Integer count = jsonObject1.getInteger("total") + aiTotal + jsonObject2.getInteger("total");
        return new JSONObject() {{
            put("value", count);
        }};
    }

    @Override
    public JSONObject getWeather() {
        if (!redisCache.hasKey("todayWeather")) {
            apiRequestService.getWeather();
        }
        return redisCache.getCacheObject("todayWeather");
    }


    @Override
    public JSONArray getAIInfos() {
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().size(999).modelKey("NORMAL_MAIN").build());
        List<InsightFlow> rows = JSONArray.parseArray(jsonObject.get("rows").toString(), InsightFlow.class);
        String value1 = String.valueOf(rows.stream().filter(x -> x.getFlowSource().equals("告警事件")).count());
        String value2 = String.valueOf(rows.stream().filter(x -> x.getFlowSource().equals("告警事件")).count());
        String value3 = String.valueOf(getAiTotal());
        String value4 = "0";
        String name = "AI智能处置";
        return JSONArray.of(JSONObject.from(new InsightResponseVo.InsightResponseDatas(name, value1, value2, value3, value4)));
    }

    String getTimeString(int startHour, int finishHour) {
        LocalDate ldt = LocalDate.now();
        String todayMin = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), startHour, 0, 0).format(dateTimeFormatter);
        String todayMax = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), finishHour, 59, 59).format(dateTimeFormatter);
        return todayMin + ',' + todayMax;
    }

    String getTimeString(LocalDate ldt) {
        String todayMin = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), 0, 0, 0).format(dateTimeFormatter);
        String todayMax = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), 23, 59, 59).format(dateTimeFormatter);
        return todayMin + ',' + todayMax;
    }

    JSONObject getApiFlowJSONObject(InsightRequestVo.MapVo build) {
        InsightRequestVo insightSearchVo = new InsightRequestVo(JSONObject.from(build));
        CommonRes commonRes = apiRequestService.insightPostJson(ServerUrlCon.firstApiUrl.getFlowInfo, insightSearchVo);
        return JSONObject.from(commonRes.getData());
    }

    InsightRequestVo.MapVo.MapVoBuilder MapVoBuilder() {
        return InsightRequestVo.MapVo.builder();
    }

    @Async
    @Scheduled(cron = "0 0 * * * ?")
    public void countAi() {
        final int size = 1000;
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().size(size).modelKey("NORMAL_MAIN").dateRange(null).build());
        Integer count = jsonObject.getInteger("total");
        int pages = (int) Math.ceil((double) count / size);
        int counts = 0;
        for (int i = 1; i <= pages; i++) {
            JSONObject res = getApiFlowJSONObject(MapVoBuilder().size(size).page(i).modelKey("NORMAL_MAIN").dateRange(null).build());
            List<InsightFlow> rows = JSONArray.parseArray(res.get("rows").toString(), InsightFlow.class);
            int count1 = (int) rows.stream().filter(x -> x.getFlowSource().equals("告警事件")).count();
            counts += count1;
        }
        redisCache.setCacheObject("AITotal", counts);
    }

    @Override
    public JSONArray get12345Infos() {
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().size(999).build());
        Integer total = jsonObject.getInteger("total");
        List<InsightFlow> rows = JSONArray.parseArray(jsonObject.get("rows").toString(), InsightFlow.class);
        long count = rows.stream().filter(x -> x.getCloseDate().startsWith(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))).count();
        String value1 = String.valueOf(count);
        String value2 = String.valueOf(total - count);
        String value3 = count * 100 / total + "%";
        String value4 = "0";
        String name = "热线12345";
        return JSONArray.of(JSONObject.from(new InsightResponseVo.InsightResponseDatas(name, value1, value2, value3, value4)));
    }

    @Override
    public JSONArray getBasicLevelInfos() {
        JSONObject jsonObject = getApiFlowJSONObject(MapVoBuilder().modelKey("CHENGYUN_MAIN").size(999).dateRange(null).build());
        Integer total = jsonObject.getInteger("total");
        List<InsightFlow> rows = JSONArray.parseArray(jsonObject.get("rows").toString(), InsightFlow.class);
        long notStart = rows.stream().filter(x -> x.getFlowStatus().equals("105")).count();//未开单
        long done = rows.stream().filter(x -> x.getFlowStatus().equals("107")).count();//未开单
        String value1 = String.valueOf(done);
        String value2 = String.valueOf(notStart);
        String value3 = String.valueOf(rows.stream().filter(x -> x.getUrgency().contains("大")).count());
        String value4 = String.valueOf(total);
        String name = "基层吹哨";
        return JSONArray.of(JSONObject.from(new InsightResponseVo.InsightResponseDatas(name, value1, value2, value3, value4)));
    }

    @Override
    public JSONArray getFlowSourceClassified() {
        Integer flow12345Total  = getApiFlowJSONObject(MapVoBuilder().dateRange(null).build()).getInteger("total") ;
        Integer aiTotal = redisCache.getCacheObject("AITotal");
        Integer basicTotal = getApiFlowJSONObject(MapVoBuilder().modelKey("CHENGYUN_MAIN").dateRange(null).build()).getInteger("total");
        return JSONArray.of(
                new InsightResponseVo.InsightResponseData("12345", flow12345Total),
                new InsightResponseVo.InsightResponseData("基层吹哨", basicTotal),
                new InsightResponseVo.InsightResponseData("AI智能处置", aiTotal)
        );
    }

    public int getAiTotal() {
        if (!redisCache.hasKey("AITotal")) {
            countAi();
        }
        return redisCache.getCacheObject("AITotal");
    }
}