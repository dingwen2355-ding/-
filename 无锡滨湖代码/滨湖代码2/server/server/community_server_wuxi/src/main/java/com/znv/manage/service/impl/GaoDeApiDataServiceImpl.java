package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.WeatherBean;
import com.znv.manage.common.utils.LoadProperties;
import com.znv.manage.dao.GaoDeApiDataDao;
import com.znv.manage.service.GaoDeApiDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@Slf4j
@Service
@PropertySource(value = "classpath:application-dev.properties", encoding = "utf-8")
public class GaoDeApiDataServiceImpl implements GaoDeApiDataService, InitializingBean {

    @Autowired
    private GaoDeApiDataDao gaoDeApiDataDao;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gaode.user.key:9e2da9d522f7ca341f4384fd60c658e9}")
    private String apiKey;

    @Value("${gaode.weather.restapi.url:https://restapi.amap.com}")
    private String apiAddress;

    //    @Value("${gaode.weather.city:无锡}")
    private String weatherCity;

    //    @Value("${gaode.road.city:无锡}")
    private String roadCity;

    //    @Value("${gaode.road.strlist:鑫湖大道}")
    private String roadStrList;

    @Override
    public void syncWeatherInfo() {
        String adcode = getAdcode(weatherCity);
        if (StringUtils.isEmpty(adcode)) {
            log.info("GaoDe adcode 为空! ");
            return;
        }

        String apiUrl = apiAddress + "/v3/weather/weatherInfo?city=" + adcode + "&key=" + apiKey;
        JSONObject result = restTemplate.getForObject(apiUrl, JSONObject.class);
        log.info("Request GaoDe weather api: [{}], response: {}", apiUrl, result);

        if (!CollectionUtils.isEmpty(result)
                && "10000".equals(result.getString("infocode"))) {

            JSONArray jsonArray = result.getJSONArray("lives");
            JSONObject sonJson = jsonArray.getJSONObject(0);

            WeatherBean bean = new WeatherBean();
            bean.setObsTime(sonJson.getString("reporttime"));
            bean.setTemp(sonJson.getString("temperature"));
            bean.setText(sonJson.getString("weather"));
            bean.setWindDir(sonJson.getString("winddirection"));
            bean.setWindScale(sonJson.getString("windScale"));
            String windSpeed = sonJson.getString("windpower");
            bean.setWindSpeed(windSpeed.substring(1));
            bean.setHumidity(sonJson.getString("humidity"));

            gaoDeApiDataDao.insertWeather(bean);
        }
    }

    @Override
    public void syncRoadInfo() {

        String adcode = getAdcode(roadCity);
        if (StringUtils.isEmpty(adcode)) {
            log.info("GaoDe adcode 为空! ");
            return;
        }

        if (StringUtils.isEmpty(roadStrList)) {
            log.info("The gaode.road.strlist config value in application-dev.properties is empty!");
            return;
        }

        String[] roadArray = roadStrList.split(",");
        for (String roadName : roadArray) {

            String apiUrl = apiAddress + "/v3/traffic/status/road?level=4&adcode=" + adcode + "&name=" + roadName + "&key=" + apiKey;

            try {
                JSONObject result = restTemplate.getForObject(apiUrl, JSONObject.class);
                log.info("Request GaoDe road info api: [{}], response: {}", apiUrl, result);

                if (CollectionUtils.isEmpty(result) || !"10000".equals(result.getString("infocode"))) {
                    continue;
                }

                JSONObject trafficinfo = result.getJSONObject("trafficinfo");
                if (CollectionUtils.isEmpty(trafficinfo)) {
                    continue;
                }

                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("road_name", roadName);
                dataMap.put("description", trafficinfo.getString("description"));

                JSONObject evaluation = trafficinfo.getJSONObject("evaluation");
                dataMap.put("evaluation_expedite", evaluation.getString("expedite"));
                dataMap.put("evaluation_congested", evaluation.getString("congested"));
                dataMap.put("evaluation_blocked", evaluation.getString("blocked"));
                dataMap.put("evaluation_unknown", evaluation.getString("unknown"));
                dataMap.put("evaluation_status", evaluation.getString("status"));
                dataMap.put("evaluation_description", evaluation.getString("description"));

                gaoDeApiDataDao.insertRoadInfo(dataMap);

                sleep(1000);
            } catch (Exception e) {
                log.error("Request GaoDe road info api: [{}] occur an exception! ", apiUrl, e);
            }
        }
    }

    @Override
    public List<Map<String, Object>> queryWeather() {
        return gaoDeApiDataDao.queryWeather();
    }

    @Override
    public List<Map<String, Object>> queryRoadInfoList(String roadName) {

        if (StringUtils.isEmpty(roadName)) {
            roadName = roadStrList;
        }

        List<Map<String, Object>> dataList = new ArrayList<>();
        String[] roadNameArray = roadName.split(",");
        for (String tempRoadName : roadNameArray) {
            Map<String, Object> roadInfo = gaoDeApiDataDao.queryLatestRoadInfo(tempRoadName);
            if (!CollectionUtils.isEmpty(roadInfo)) {
                dataList.add(roadInfo);
            }
        }

        Function<Map<String, Object>, Double> keyExtractor = map -> {
            String evaluationBlocked = String.valueOf(map.get("evaluation_blocked"));
            if (StringUtils.isEmpty(evaluationBlocked)) {
                return 0d;
            }
            return Double.parseDouble(evaluationBlocked.replace("%", ""));
        };

        return dataList.stream()
                .sorted(Comparator.comparing(keyExtractor)
                        .reversed()
                        .thenComparing(map -> {
                            String evaluationCongested = String.valueOf(map.get("evaluation_congested"));
                            if (StringUtils.isEmpty(evaluationCongested)) {
                                return 0d;
                            }
                            return Double.parseDouble(evaluationCongested.replace("%", ""));
                        }, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * 获取高德城市 adcode
     *
     * @return String
     */

    public String getAdcode(String city) {
        String apiUrl = apiAddress + "/v3/geocode/geo?key=" + apiKey + "&city=" + city + "&address=" + city;
        JSONObject result = getHttp(apiUrl);
        log.info("Request GaoDe adccode api: [{}], response: {}", apiUrl, result);
        String adcode = "";
        if (!CollectionUtils.isEmpty(result) && "10000".equals(result.getString("infocode"))) {
            JSONArray jsonArray = result.getJSONArray("geocodes");
            if (!CollectionUtils.isEmpty(jsonArray)) {
                JSONObject sonJson = (JSONObject) jsonArray.get(0);
                adcode = sonJson.getString("adcode");
            }
        }
        return adcode;
    }

    /**
     * 发起 Http 请求
     *
     * @param url 请求路径
     * @return 请求接口返回结果
     */
    public JSONObject getHttp(String url) {
        JSONObject resultJson = new JSONObject();
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        try {
            // 由客户端执行(发送)Get请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            resultJson = JSONObject.parseObject(content);
        } catch (Exception e) {
            log.error("getHttp exception: ", e);
        }
        return resultJson;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            weatherCity = LoadProperties.getProperty("application-dev.properties", "gaode.weather.city");
            roadCity = LoadProperties.getProperty("application-dev.properties", "gaode.road.city");
            roadStrList = LoadProperties.getProperty("application-dev.properties", "gaode.road.strlist");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
