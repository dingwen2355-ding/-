package com.ruoyi.web.controller.insightApi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.system.service.IInsightApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: insightApiController
 * @Description：sy
 * @Author： ayf
 * @Date： 2024/2/27
 */
@RestController
@Anonymous
@RequestMapping("/insight")
public class insightApiController {

    @Autowired
    private IInsightApiService insightApiService;

    @GetMapping("/test")
    public JSONArray getMsg() {

        String str = "[\n" +
                "    {\n" +
                "        \"name\": \"信*思\",\n" +
                "        \"series0\": 59,\n" +
                "        \"series1\": 60\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"郑*\",\n" +
                "        \"series0\": 38,\n" +
                "        \"series1\": 80\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"权*\",\n" +
                "        \"series0\": 59,\n" +
                "        \"series1\": 50\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"自*\",\n" +
                "        \"series0\": 38,\n" +
                "        \"series1\": 89\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"黑*\",\n" +
                "        \"series0\": 38,\n" +
                "        \"series1\": 56\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"冷*毓\",\n" +
                "        \"series0\": 59,\n" +
                "        \"series1\": 32\n" +
                "    }\n" +
                "]";
        return JSON.parseArray(str);
    }

    @GetMapping("/getOnePersonMultiplyComplaints")
    public JSONArray getOnePersonMultiplyComplaints(@RequestBody(required = false) JSONObject jsonObject) {
        return insightApiService.getOnePersonMultiplyComplaints(jsonObject);
    }

    /**
     * 事件分类
     *
     * @return
     */
    @GetMapping("/getFlowsClassified")
    public JSONArray getFlowsClassified() {
        return insightApiService.getFlowsClassified();
    }

    /**
     * 部门多诉
     */
    @GetMapping("/getOneDepartmentMultiplyComplaints")
    public JSONArray getOneDepartmentMultiplyComplaints() {
        return insightApiService.getOneDepartmentMultiplyComplaints();
    }

    @GetMapping("/getComplaintsByHours")
    public JSONArray getComplaintsByHours(int max, int min) {
        return insightApiService.getComplaintsByHours(max, min);
    }


    @GetMapping("/getComplaintsCountInWeek")
    public JSONArray getComplaintsCountInWeek() {
        return insightApiService.getComplaintsCountInWeek();
    }

    @GetMapping("/getTotalFlow")
    public JSONObject getTotalFlow() {
        return insightApiService.getTotalFlow();
    }

    @GetMapping("/getWeather")
    public JSONObject getWeather() {
        return insightApiService.getWeather();
    }

    @GetMapping("/getAIInfos")
    public JSONArray getAIInfos() {
        return insightApiService.getAIInfos();
    }
    @GetMapping ("/get12345Infos")
    public JSONArray get12345Infos(){
        return  insightApiService.get12345Infos();
    }

    @GetMapping ("/getBasicLevelInfos")
    public JSONArray getBasicLevelInfos(){
        return  insightApiService.getBasicLevelInfos();
    }
    @GetMapping ("/getFlowSourceClassified")
    public JSONArray getFlowSourceClassified(){
        return  insightApiService.getFlowSourceClassified();
    }
}