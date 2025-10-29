package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.VideoDevice;
import com.znv.manage.common.utils.*;
import com.znv.manage.dao.DeviceMapper;
import com.znv.manage.dao.FusionVideoDao;
import com.znv.manage.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    FusionVideoDao fusionVideoDao;

    private String danbingStatusUrl = "192.166.4.140:8080/dispatch-web/api/call/listStatus?token=eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6InpoengyIiwidXNlcklkIjozLCJJbnZhbGlkVGltZSI6IlRodSBBdWcgMTkgMTg6Mzg6MzAgQ1NUIDIwMjEifQ.Bl1RhakiurRLr6GYSf8-n_835Pcc61iRp6gOoEW3NdM_3";


    @Value("${chengdu.danbin.acount}")
    private String acount;
    @Value("${chengdu.danbin.password}")
    private String password;
    @Value("${chengdu.danbin.address}")
    private String address;
    @Value("${chengdu.danbin.loginAddr}")
    private String loginAddr;
    @Value("${chengdu.danbin.companyAddr}")
    private String companyAddr;
    @Value("${chengdu.danbin.gpsAddr}")
    private String gpsAddr;

    @Value("${qinbaijiang.danbin.gpsAddr:http://221.237.108.93:11800/rtv/api/3rd/v1/query_gps_info}")
    private String qbjGpsUrl;
    @Value("${qinbaijiang.danbin.apiKey:123456}")
    private String apiKey;
    @Value("${qinbaijiang.danbin.securityKey:C7CAE01FFDEE19DD6EB448EFE638A4C9D8FDFD3559}")
    private String securityKey;
    @Value("${wenjiang.danbin.gps.gpsUrl:http://10.14.216.182:8080/scooper-trace/data/trace/listTraceByArgs?propType=0}")
    private String wenjiangDanbinGpsUrl;
    @Value("${wenjiang.danbin.gps.tokenUrl:http://10.14.216.182:8080/scooper-core-rest/data/system/authManage/loginTo?accUsername=cs&accPassword=e9cee71ab932fde863338d08be4de9dfe39ea049bdafb342ce659ec5450b69ae}")
    private String wenjiangDanbinTokenUrl;
    @Value("${chongzhou.danbin.gps.url:http://10.37.190.23:18080/keeper-special/internetThings/getTrack}")
    private String chongzhouDanbinGpsUrl;
    @Value("${gaoxin.danbin.gps.url:http://8.134.59.244:8081/api/adinfoViewsFuzzy}")
    private String gaoxinDanbinGpsUrl;
    @Value("${gaoxin.danbin.token.url:http://8.134.59.244:8081/api/login?para=eyJ1c2VybmFtZSI6IkNER1hRVGVzdCIsInBhc3N3b3JkIjoiQ0RHWFFAMTIzIn0=}")
    private String gaoxinDanbinTokenUrl;
    @Value("${xindu.danbin.gps.url:http://10.1.213.219:8080/scooper-core-rest/data/system/authManage/loginTo?accUsername=xindu&accPassword=e9cee71ab932fde863338d08be4de9dfe39ea049bdafb342ce659ec5450b69ae}")
    private String xinduDanbinTokenUrl;
    @Value("${xindu.danbin.gps.url:http://10.1.213.219:8080/scooper-trace/data/trace/listTraceByArgs?propType=0}")
    private String xinduDanbinGpsUrl;

    @Value("${weierxing.danbin.gps.url:http://10.1.248.104:8003/Services/MailHandler.ashx?Method=PersonList&TypeID=2&area_key=}")
    private String weierxingDanbinGpsUrl;

    @Value("${dongbu.danbin.gps.url:http://10.190.162.5:8080/scooper-core-rest/data/system/authManage/loginTo?accUsername=zhzx&accPassword=e9cee71ab932fde863338d08be4de9dfe39ea049bdafb342ce659ec5450b69ae}")
    private String dongbuDanbinTokenUrl;
    @Value("${dongbu.danbin.gps.url:http://10.190.162.5:8080/scooper-trace/data/trace/listTraceByArgs?propType=0}")
    private String dongbuDanbinGpsUrl;
    @Value("${xinjin.danbin.gps.url:http://10.45.184.46:8080/scooper-core-rest/data/system/authManage/loginTo?accUsername=xinjin&accPassword=e9cee71ab932fde863338d08be4de9dfe39ea049bdafb342ce659ec5450b69ae}")
    private String xinjinDanbinTokenUrl;
    @Value("${xinjin.danbin.gps.url:http://10.45.184.46:8080/scooper-trace/data/trace/listTraceByArgs?propType=0}")
    private String xinjinDanbinGpsUrl;

    @Value("${shuangliu.danbin.login.url:http://10.39.235.53:8098/ps/data/login}")
    private String shuangliuDanbinLoginUrl;
    @Value("${shuangliu.danbin.userName:100001}")
    private String shuangliuUserName;
    @Value("${shuangliu.danbin.password:100001}")
    private String shuangliuPassword;
    @Value("${shuangliu.danbin.gps.url:http://10.39.235.53:8098/ps/data/groupSubUserList/1000000}")
    private String shuangliuDanbinGpsUrl;

    @Value("${xfc.url:http://218.6.242.32:800/cdxfapi/locationmsg}")
    private String xfcUrl;
    @Value("${xfc.AppKey:945627669616132096}")
    private String xfcAppKey;
    @Value("${wrj.url:http://10.1.248.104:8003/Services/MediaHandler.ashx?Method=MediaList&VideoType=3&area_key=}")
    private String wrjUrl;

    @Value("${both.type.danbin.precinctIds:510187}")
    private String noPrecinctIds;

    @Value("${phone.url}")
    private String rhtxUrl;


    private static Map<String, String> AREA_KEY = new HashMap<>();

    static {
        AREA_KEY.put("1", "510104");
        AREA_KEY.put("2", "510105");
        AREA_KEY.put("3", "510107");
        AREA_KEY.put("4", "510121");
        AREA_KEY.put("5", "510129");
        AREA_KEY.put("6", "510183");
        AREA_KEY.put("7", "510181");
        AREA_KEY.put("8", "510185");
        AREA_KEY.put("9", "510187");
        AREA_KEY.put("12", "510182");
        AREA_KEY.put("13", "510131");
        AREA_KEY.put("18", "510108");
        AREA_KEY.put("19", "510106");
    }


    @Override
    public List<VideoDevice> getDeviceFixedVideo(String deviceTypeName, String precinctName) {
        return deviceMapper.getDeviceFixedVideo(deviceTypeName, precinctName);
    }


    @Override
    public List<Map<String, String>> getDeviceFixedVideoType(String precinctName) {
        return deviceMapper.getDeviceFixedVideoType(precinctName);
    }

    @Override
    public void saveHikDevice() {
        try {
            log.info("同步海康hik设备开始");
            String result = HikUtils.getCameras(1, 1000);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                int total = data.getInteger("total");
                int totalPage = total / 1000 + 1;
                for (int i = 1; i <= totalPage; i++) {
                    final int page = i;
                    new Thread(() -> {
                        log.info("page:" + page);
                        String result2 = HikUtils.getCameras(page, 1000);
                        JSONArray jsonArray = JSONObject.parseObject(result2).getJSONObject("data").getJSONArray("list");
                        int count = deviceMapper.saveHikDevice(jsonArray);
                        log.info("count:" + count);
                    }).start();
                }
            }
            log.info("同步海康hik设备结束");
        } catch (Exception e) {
            log.error("同步海康hik设备异常：" + e.getMessage());
        }
    }

    @Override
    public void syncDbEquip() {
        try {
            log.info("同步单兵设备开始");
            RestTemplate restTemplate = new RestTemplate();
            String url = rhtxUrl + "/scooper-core-rest/data/contacts/orgDeptManage/listDeptMemberByDeotId?" +
                    "token=" + PhoneServiceImpl.getToken() + "&id=" + "1";
            JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
            if (jsonObject != null && ObjectUtils.equals(jsonObject.getInteger("code"), 0)) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                int result = 0;
                if (!CollectionUtils.isEmpty(dataArray)) {
                    result = deviceMapper.insertDb(dataArray);
                }
                log.info("同步成功{}条单兵设备", result);
            } else {
                log.error("获取单兵设备失败,url:{},返回值:{}", url, jsonObject);
            }
        } catch (Exception e) {
            log.error("同步单兵设备失败,{}", e.toString());
        }
    }

    @Override
    public List<Map<String, String>> getDeviceRotationMapping(String rotationType, String precinctName) {
        return deviceMapper.getDeviceRotationMapping(rotationType, precinctName);
    }

    @Override
    public List<Map<String, String>> getDeviceRotationType(String precinctName) {
        return deviceMapper.getDeviceRotationType(precinctName);
    }

    @Override
    public int getDeviceGpsIsNotNull() {
        int count = 0;
        List<Map<String, Object>> list = deviceMapper.getDeviceGpsIsNotNull();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String gpsx = map.get("gpsx").toString();
            String gpsy = map.get("gpsy").toString();
            String deviceId = map.get("deviceId").toString();
            Map<String, String> resultMap = GeometryUtil.getPrecinctIdByGps(gpsx, gpsy);
            String precinctName = resultMap.get("precinctName");
            if (precinctName != null) {
                count += deviceMapper.updateDeviceStreet(precinctName, deviceId);
            }
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> queryIndividualList(String precinctId, String deviceId) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (!StringUtils.isEmpty(precinctId)) {
            result.addAll(deviceMapper.queryIndividualListByPrecinctId(precinctId, deviceId, noPrecinctIds, null, null));
            result.addAll(deviceMapper.queryIndividualListByPrecinctId(noPrecinctIds, deviceId, null, null, "威尔信"));
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String companyUrl = address + companyAddr + getJsession();
            JSONObject jsonObject1 = restTemplate.getForObject(companyUrl, JSONObject.class);
            String company = jsonObject1.getJSONArray("companys").getJSONObject(0).getString("nm");
            String gpsUrl = address + gpsAddr + getJsession() + "&toMap=1";
            JSONObject jsonObject2 = restTemplate.getForObject(gpsUrl, JSONObject.class);
            JSONArray status = jsonObject2.getJSONArray("status");
            if (!CollectionUtils.isEmpty(status)) {
                for (int i = 0; i < status.size(); i++) {
                    JSONObject tempJson = status.getJSONObject(i);
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("deviceId", tempJson.getString("id"));
                    temp.put("deviceName", tempJson.getString("abbr"));
                    temp.put("gpsx", tempJson.getString("mlng"));
                    temp.put("gpsy", tempJson.getString("mlat"));
                    temp.put("unit", company);
                    temp.put("precinctId", "510100");
                    temp.put("onofflineState", "0");
                    result.add(temp);
                }
            }
            result.addAll(deviceMapper.queryIndividualListByPrecinctId(null, deviceId, noPrecinctIds, null, null));
            result.addAll(deviceMapper.queryIndividualListByPrecinctId(noPrecinctIds, deviceId, null, "威尔信", null));
        }
        if (!StringUtils.isEmpty(deviceId) && !CollectionUtils.isEmpty(result)) {
            for (Map<String, Object> map : result) {
                if (!deviceId.equals(map.get("deviceId"))) {
                    result.remove(map);
                }
            }
        }
        for (Map<String, Object> map : result) {
            if ("510113,510100".contains(map.get("precinctId").toString())) {
                if (!StringUtils.isEmpty(map.get("gpsy")) && !StringUtils.isEmpty(map.get("gpsx"))) {
                    double[] gps_84 = GpsUtils.gcj02_To_Gps84(Double.valueOf(String.valueOf(map.get("gpsy"))), Double.valueOf(String.valueOf(map.get("gpsx"))));
                    map.put("gpsx", gps_84[1]);
                    map.put("gpsy", gps_84[0]);
                } else {
                    map.put("gpsx", "");
                    map.put("gpsy", "");
                }
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryIndividualList2(String precinctId) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (!StringUtils.isEmpty(precinctId)) {
            result.addAll(deviceMapper.queryIndividualListByPrecinctId2(precinctId));
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String companyUrl = address + companyAddr + getJsession();
            JSONObject jsonObject1 = restTemplate.getForObject(companyUrl, JSONObject.class);
            String company = jsonObject1.getJSONArray("companys").getJSONObject(0).getString("nm");
            String gpsUrl = address + gpsAddr + getJsession() + "&toMap=1";
            JSONObject jsonObject2 = restTemplate.getForObject(gpsUrl, JSONObject.class);
            JSONArray status = jsonObject2.getJSONArray("status");
            if (!CollectionUtils.isEmpty(status)) {
                for (int i = 0; i < status.size(); i++) {
                    JSONObject tempJson = status.getJSONObject(i);
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("deviceId", tempJson.getString("id"));
                    temp.put("deviceName", tempJson.getString("abbr"));
                    temp.put("gpsx", tempJson.getString("mlng"));
                    temp.put("gpsy", tempJson.getString("mlat"));
                    temp.put("unit", company);
                    temp.put("precinctId", "510100");
                    result.add(temp);
                }
            }
            result.addAll(deviceMapper.queryIndividualListByPrecinctId2(null));
        }
        for (Map<String, Object> map : result) {
            if ("510113,510100".contains(map.get("precinctId").toString())) {
                double[] gps_84 = GpsUtils.gcj02_To_Gps84(Double.valueOf(map.get("gpsy").toString()), Double.valueOf(map.get("gpsx").toString()));
                map.put("gpsx", gps_84[1]);
                map.put("gpsy", gps_84[0]);
            }
        }
        return result;
    }

    private String getJsession() {
        String result = "";
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = address + loginAddr + "?account=" + acount + "&password=" + password;
            JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
            result = jsonObject.getString("jsession");
        } catch (Exception e) {
            log.error("成都单兵api登陆异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> query32DeviceList() {
//        RestTemplate restTemplate = new RestTemplate();
//        JSONObject jsonObject = restTemplate.getForObject(danbingStatusUrl,JSONObject.class);
//        JSONArray jsonArray = jsonObject.getJSONArray("list");
//        if(!CollectionUtils.isEmpty(jsonArray)){
//            for(int i = 0;i<jsonArray.size();i++){
//                if(jsonArray.getJSONObject(i).getString())
//            }
//        }
        return deviceMapper.query32DeviceList();
    }

    @Override
    public List<Map<String, Object>> query34DeviceList() {
        return deviceMapper.query34DeviceList();
    }

    @Override
    public List<DeviceInfo> query35DeviceList(String precinctId, String deviceKind) {
        return fusionVideoDao.query35DeviceList(precinctId, deviceKind);
    }

    @Override
    public List<DeviceInfo> query33DeviceList(String precinctId) {
        return fusionVideoDao.query33DeviceList(precinctId);
    }

    @Override
    public void updateDanBinGps() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Date date = new Date();
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            map.add("timestamp", String.valueOf(date.getTime()));
            map.add("api_key", apiKey);
            map.add("sign", getSign(date.getTime()));
            map.add("endtime", "86400");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
            JSONObject jsonObject = restTemplate.postForObject(qbjGpsUrl, request, JSONObject.class);
            if ("0".equals(jsonObject.getString("ret"))) {
                JSONArray gpsinfos = jsonObject.getJSONArray("gpsinfos");
                if (!CollectionUtils.isEmpty(gpsinfos)) {
                    int count = deviceMapper.updateQbjDanbinGps(gpsinfos);
                    log.info("青白江区单兵坐标更新：" + count);
                }
            } else {
                log.error(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateWenJiangDanBin() {
        try {
            log.error("温江单兵坐标同步");
            RestTemplate restTemplate1 = new RestTemplate();
            //获取token
            JSONObject tokenObj = restTemplate1.getForObject(wenjiangDanbinTokenUrl, JSONObject.class);
            if ("0".equals(tokenObj.getString("code"))) {
                String token = tokenObj.getString("data");
                List<Map<String, Object>> list = deviceMapper.queryXujianDanbin("510100");
                if (!CollectionUtils.isEmpty(list)) {
                    String url = wenjiangDanbinGpsUrl + "&token=" + token + "&startTime=" + DateUtil.getStringDateShort()
                            + " 00:00:00" + "&endTime=" + DateUtil.getStringDateShort() + " 23:59:59";
                    List<Map<String, Object>> updateList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        String temUrl = url + "&propKey=" + map.get("deviceId");
//                        log.error("温江url:"+temUrl);
                        JSONObject gpsObj = restTemplate1.getForObject(temUrl, JSONObject.class);
//                        log.error("温江gpsObj:"+gpsObj);
                        if ("0".equals(gpsObj.getString("code")) && !CollectionUtils.isEmpty(gpsObj.getJSONArray("data"))) {
                            JSONArray data = gpsObj.getJSONArray("data");
                            map.put("gpsx", data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("longitude"));
                            map.put("gpsy", data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("latitude"));
                            updateList.add(map);
                        }
                    }
                    if (!CollectionUtils.isEmpty(updateList)) {
                        deviceMapper.updateXujianIndividualGps(updateList, "510100");
                    }
                }
            } else {
                log.error("温江单兵获取token失败" + tokenObj);
            }

        } catch (Exception e) {
            log.error("温江单兵同步异常：{}" , e.toString());
        }
    }

    @Override
    public void updateDongbuDanBin() {
        try {
            log.error("东部新区单兵坐标同步");
            RestTemplate restTemplate1 = new RestTemplate();
            //获取token
            JSONObject tokenObj = restTemplate1.getForObject(dongbuDanbinTokenUrl, JSONObject.class);
            if ("0".equals(tokenObj.getString("code"))) {
                String token = tokenObj.getString("data");
                List<Map<String, Object>> list = deviceMapper.queryXujianDanbin("510186");
                if (!CollectionUtils.isEmpty(list)) {
                    String url = dongbuDanbinGpsUrl + "&token=" + token + "&startTime=" + DateUtil.getStringDateShort()
                            + " 00:00:00" + "&endTime=" + DateUtil.getStringDateShort() + " 23:59:59";
                    List<Map<String, Object>> updateList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        String temUrl = url + "&propKey=" + map.get("deviceId");
//                        log.error("东部新区url:"+temUrl);
                        JSONObject gpsObj = restTemplate1.getForObject(temUrl, JSONObject.class);
//                        log.error("东部新区gpsObj:"+gpsObj);
                        if ("0".equals(gpsObj.getString("code")) && !CollectionUtils.isEmpty(gpsObj.getJSONArray("data"))) {
                            JSONArray data = gpsObj.getJSONArray("data");
                            map.put("gpsx", StringUtils.isEmpty(data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("longitude")) ? "" : data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("longitude"));
                            map.put("gpsy", StringUtils.isEmpty(data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("latitude")) ? "" : data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("latitude"));
                            updateList.add(map);
                        }
                    }
                    if (!CollectionUtils.isEmpty(updateList)) {
                        deviceMapper.updateXujianIndividualGps(updateList, "510186");
                    }
                }
            }

        } catch (Exception e) {
            log.error("东部新区单兵同步异常：" + e.getMessage());
        }
    }

    @Override
    public void updateXinjinDanBin() {
        try {
            log.error("新津区单兵坐标同步");
            RestTemplate restTemplate1 = new RestTemplate();
            //获取token
            JSONObject tokenObj = restTemplate1.getForObject(xinjinDanbinTokenUrl, JSONObject.class);
            if ("0".equals(tokenObj.getString("code"))) {
                String token = tokenObj.getString("data");
                List<Map<String, Object>> list = deviceMapper.queryXujianDanbin("510118");
                if (!CollectionUtils.isEmpty(list)) {
                    String url = xinjinDanbinGpsUrl + "&token=" + token + "&startTime=" + DateUtil.getStringDateShort()
                            + " 00:00:00" + "&endTime=" + DateUtil.getStringDateShort() + " 23:59:59";
                    List<Map<String, Object>> updateList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        String temUrl = url + "&propKey=" + map.get("deviceId");
//                        log.error("新津区url:"+temUrl);
                        JSONObject gpsObj = restTemplate1.getForObject(temUrl, JSONObject.class);
//                        log.error("新津区gpsObj:"+gpsObj);
                        if ("0".equals(gpsObj.getString("code")) && !CollectionUtils.isEmpty(gpsObj.getJSONArray("data"))) {
                            JSONArray data = gpsObj.getJSONArray("data");
                            map.put("gpsx", data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("longitude"));
                            map.put("gpsy", data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("latitude"));
                            updateList.add(map);
                        }
                    }
                    if (!CollectionUtils.isEmpty(updateList)) {
                        deviceMapper.updateXujianIndividualGps(updateList, "510118");
                    }
                }
            } else {
                log.error("新津区单兵获取token失败" + tokenObj);
            }

        } catch (Exception e) {
            log.error("新津区单兵同步异常：" + e.getMessage());
        }
    }

    @Override
    public void updateXinduDanBin() {
        try {
            log.error("新都区单兵坐标同步");
            RestTemplate restTemplate1 = new RestTemplate();
            //获取token
            JSONObject tokenObj = restTemplate1.getForObject(xinduDanbinTokenUrl, JSONObject.class);
            if ("0".equals(tokenObj.getString("code"))) {
                String token = tokenObj.getString("data");
                List<Map<String, Object>> list = deviceMapper.queryXujianDanbin("510114");
                if (!CollectionUtils.isEmpty(list)) {
                    String url = xinduDanbinGpsUrl + "&token=" + token + "&startTime=" + DateUtil.getStringDateShort()
                            + " 00:00:00" + "&endTime=" + DateUtil.getStringDateShort() + " 23:59:59";
                    List<Map<String, Object>> updateList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        String temUrl = url + "&propKey=" + map.get("deviceId");
//                        log.error("新都区url:"+temUrl);
                        JSONObject gpsObj = restTemplate1.getForObject(temUrl, JSONObject.class);
//                        log.error("新都区gpsObj:"+gpsObj);
                        if ("0".equals(gpsObj.getString("code")) && !CollectionUtils.isEmpty(gpsObj.getJSONArray("data"))) {
                            JSONArray data = gpsObj.getJSONArray("data");
                            map.put("gpsx", data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("longitude"));
                            map.put("gpsy", data.getJSONObject(data.size() - 1).getJSONObject("posLocation").getString("latitude"));
                            updateList.add(map);
                        }
                    }
                    if (!CollectionUtils.isEmpty(updateList)) {
                        deviceMapper.updateXujianIndividualGps(updateList, "510114");
                    }
                }
            } else {
                log.error("新都区单兵获取token失败" + tokenObj);
            }

        } catch (Exception e) {
            log.error("新都区单兵同步异常：" + e.getMessage());
        }
    }

    @Override
    public void saveChongzhouDanbin() {
        try {
            log.error("崇州单兵坐标同步");
            RestTemplate restTemplate = new RestTemplate();
            JSONObject result = restTemplate.getForObject(chongzhouDanbinGpsUrl, JSONObject.class);
            log.error("崇州单兵坐标同步:" + result);
            if ("200".equals(result.getString("code"))) {
                JSONArray data = result.getJSONObject("data").getJSONArray("records");
                if (!CollectionUtils.isEmpty(data)) {
                    deviceMapper.updateChongZhouDanbinGps(data);
                }
            }
        } catch (Exception e) {
            log.error("崇州单兵坐标同步：" + e.getMessage());
        }
    }

    @Override
    public void saveGaoxinDanbin() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> tokenEntity = restTemplate.exchange(gaoxinDanbinTokenUrl, HttpMethod.POST, new HttpEntity<>(new HttpHeaders()), JSONObject.class);
            if ("SUCCESS".equals(tokenEntity.getBody().getString("result"))) {
                HttpHeaders headers = tokenEntity.getHeaders();
                String JSESSIONID = String.valueOf(headers.get("Set-Cookie"));
                JSESSIONID = JSESSIONID.substring(JSESSIONID.indexOf("[") + 1, JSESSIONID.indexOf(";"));
                HttpHeaders newHeaders = new HttpHeaders();
                newHeaders.add("Cookie", JSESSIONID);
                newHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, String> msg = new LinkedMultiValueMap<>();
                msg.add("para", "eyIiOiIifQ==");
                ResponseEntity<JSONObject> gpsEntity = restTemplate.exchange(gaoxinDanbinGpsUrl, HttpMethod.POST, new HttpEntity<>(msg, newHeaders), JSONObject.class);
                JSONObject jsonObject = gpsEntity.getBody();
                if ("SUCCESS".equals(jsonObject.getString("result"))) {
                    JSONObject result = JSONObject.parseObject(new String(Base64.getDecoder().decode(jsonObject.getString("data"))));
                    JSONArray data = result.getJSONArray("data");
                    if (!CollectionUtils.isEmpty(data)) {
                        log.error("高新：" + data);
                        deviceMapper.updateGaoxinDanbinGps(data);
                    }
                } else {
                    log.error("高新单兵获取坐标失败：" + jsonObject);
                }
            } else {
                log.error("高新单兵登陆失败：" + tokenEntity.getBody());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void saveShuangliuDanbin() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String timeStamp = DateUtil.getStringDate();
            String encrypt = "PTYT" + timeStamp + shuangliuUserName + shuangliuPassword;
            encrypt = CommonUtils.MD5(encrypt);
            //登录
            MultiValueMap<String, Object> msg = new LinkedMultiValueMap<>();
            msg.add("number", shuangliuUserName);
            msg.add("timeStamp", timeStamp);
            msg.add("encrypt", encrypt);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity request = new HttpEntity(msg, headers);
            String loginResult = restTemplate.postForObject(shuangliuDanbinLoginUrl, request, String.class);
            log.info("shuangliu loginResult:" + loginResult);
            JSONObject loginJson = JSONObject.parseObject(loginResult);
            if (null != loginJson.getJSONObject("data")) {
                String token = loginJson.getJSONObject("data").getString("token");
                log.info("shuangliu token:" + token);
                HttpHeaders headers1 = new HttpHeaders();
                headers1.set("token", token);
                ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(shuangliuDanbinGpsUrl, HttpMethod.GET, new HttpEntity<>(headers1), JSONObject.class);
                JSONArray userList = responseEntity.getBody().getJSONArray("data");
                if (!CollectionUtils.isEmpty(userList)) {
                    List<Map<String, String>> saveList = new ArrayList<>();
                    for (int i = 0; i < userList.size(); i++) {
                        Map<String, String> temp = new HashMap<>();
                        JSONObject user = userList.getJSONObject(i);
                        temp.put("deviceId", user.getString("number"));
                        temp.put("deviceName", user.getString("name"));
                        if (null != user.getJSONObject("statusInfo")) {
                            temp.put("gpsx", user.getJSONObject("statusInfo").getString("longitude"));
                            temp.put("gpsy", user.getJSONObject("statusInfo").getString("latitude"));
                        }
                        saveList.add(temp);
                    }
                    if (!CollectionUtils.isEmpty(saveList)) {
                        deviceMapper.updateShuangliuDanbinGps(saveList);
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void saveWEXDanbin() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            for (String key : AREA_KEY.keySet()) {
                String url = weierxingDanbinGpsUrl + key;
                JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
                if (null != jsonObject && "获取数据成功".equals(jsonObject.getString("ReturnMsg"))) {
                    JSONArray jsonArray = jsonObject.getJSONArray("ReturnObject");
                    if (!CollectionUtils.isEmpty(jsonArray)) {
                        JSONArray insertArray = new JSONArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            if ("2".equals(temp.getString("TypeID"))) {
                                String CustomerTel = temp.getString("CustomerTel");
                                if (CustomerTel.contains(",")) {
                                    temp.put("CustomerTel", CustomerTel.substring(0, CustomerTel.indexOf(",")));
                                }
                                if (!StringUtils.isEmpty(temp.getString("Lat")) && !StringUtils.isEmpty(temp.getString("Lng"))) {
                                    //百度转84
                                    double[] gps_84 = GpsUtils.bd09_To_gps84(Double.valueOf(temp.getString("Lat")), Double.valueOf(temp.getString("Lng")));
                                    temp.put("Lng", gps_84[1]);
                                    temp.put("Lat", gps_84[0]);
                                }
                                insertArray.add(temp);
                            }
                        }
                        if (!CollectionUtils.isEmpty(insertArray)) {
                            deviceMapper.updateWEXDanbinGps(insertArray, AREA_KEY.get(key));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("同步威尔信单兵异常：" + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> queryCityEyeDeviceList(String precinctId, String deviceName) {
        return deviceMapper.queryCityEyeDeviceList(precinctId, deviceName);
    }

    @Override
    public void saveXFC() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("AppKey", xfcAppKey);
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(xfcUrl, HttpMethod.GET, new HttpEntity<>(headers), JSONObject.class);
            JSONObject jsonObject = responseEntity.getBody();
            if (null != jsonObject && "0".equals(jsonObject.getString("code"))) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (!CollectionUtils.isEmpty(data)) {
                    deviceMapper.saveXFC(data);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void saveWRJ() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            for (String key : AREA_KEY.keySet()) {
                String url = wrjUrl + key;
                JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
                if (null != jsonObject && "获取数据成功".equals(jsonObject.getString("ReturnMsg"))) {
                    JSONArray jsonArray = jsonObject.getJSONArray("ReturnObject");
                    if (!CollectionUtils.isEmpty(jsonArray)) {
                        JSONArray insertArray = new JSONArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);

//                            if(!StringUtils.isEmpty(temp.getString("Pos_x")) && !StringUtils.isEmpty(temp.getString("Pos_y"))){
//                                //百度转84
//                                double[] gps_84 = GpsUtils.bd09_To_gps84(Double.valueOf(temp.getString("Pos_x")),Double.valueOf(temp.getString("Pos_y")));
//                                temp.put("Pos_y",gps_84[1]);
//                                temp.put("Pos_x",gps_84[0]);
//                            }
                            insertArray.add(temp);

                        }
                        if (!CollectionUtils.isEmpty(insertArray)) {
                            deviceMapper.updateWEXWurenjiGps(insertArray, AREA_KEY.get(key));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private String getSign(long time) {
        String sign = "";
        try {
            String str = "api_key" + apiKey + "timestamp" + time + "/rtv/api/3rd/v1/query_gps_info";
            sign = CommonUtils.HMACSHA256(str, securityKey);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sign;
    }
}
