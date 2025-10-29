package com.wxgis.topic.influx.controller;

import com.wxgis.topic.influx.service.InfluxdbService;
import com.wxgis.topic.influx.service.TopicService;
import com.wxgis.topic.influx.util.DateUtils;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.Object;
import java.util.*;

@RestController
@RequestMapping("influxdb")
public class InfluxController {

    @Resource
    InfluxdbService influxdbService;
    @Autowired
    TopicService topicService;

    @GetMapping("/select")
    public Object list() {
        String sql = "SELECT * FROM \"wmcs\" WHERE timestamp = '%s'";
        QueryResult query = influxdbService.query(sql);
        List<Map<String, Object>> maps = influxdbService.queryResultProcess(query);
        if (maps.size() > 0) {

        }
        return maps;
    }


    @GetMapping("/groupList")
    public Object groupList() {
        String currentMonthFirth = DateUtils.getBeginDayByMonth(DateUtils.getCurrentMonth()) + "T00:00:00Z";
        String currentMonthEnd = DateUtils.getEndDayByMonth(DateUtils.getCurrentMonth()) + "T23:59:59Z";
        String sql = "SELECT count(content) FROM \"wmcs\" where time >= '%s' and time <= '%s' group by type, channelId";
        QueryResult query = influxdbService.query(String.format(sql, currentMonthFirth, currentMonthEnd));
        List<Map<String, String>> list = new ArrayList<>();
        for(QueryResult.Result r : query.getResults()){
            List<QueryResult.Series> seriesList = r.getSeries();
            if(seriesList != null && seriesList.size() != 0) {
                for(QueryResult.Series series : seriesList){
                    Map<String, String> tagMap = series.getTags();
                    List<String> columns = series.getColumns();
                    String[] keys = columns.toArray(new String[columns.size()]);
                    List<List<Object>> values = series.getValues();
                    if(values != null && values.size() != 0) {
                        for(List<Object> value : values){
                            for (int i = 0; i < keys.length; i++) {
                                tagMap.put(keys[i], String.valueOf(value.get(i)));
                            }
                        }
                    }
                    list.add(tagMap);
                }
            }
        }
//        List<Map<String, Object>> maps = influxdbService.queryResultProcess(query);
//        if (maps.size() > 0) {
//            maps.forEach(o -> {
//                String str = String.valueOf(o);
//                System.out.println();
//            });
//        }
        return list;
    }


    @GetMapping("/topic1")
    public Object topic1() throws InterruptedException {
        topicService.runTopic1();
        return "OK";
    }

    @GetMapping("/topic2")
    public Object topic2() {
        topicService.runTopic2();
        return "OK";
    }



}
