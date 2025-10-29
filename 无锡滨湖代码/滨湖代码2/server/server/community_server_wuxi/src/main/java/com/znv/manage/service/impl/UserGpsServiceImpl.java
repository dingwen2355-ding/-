package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.dao.XyxcUserDao;
import com.znv.manage.service.UserGpsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class UserGpsServiceImpl implements UserGpsService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(UserGpsServiceImpl.class);

    private static String getTokenUrl = "/pluto/user/getToken";
    private static String clockListUrl = "/pluto/clock/list?departmentId=1&pageSize=1000&pageNum=";
    private static String deptUrl = "/pluto/department/info?fetch=1&direction=up&accessToken=";
    @Value("${user.gps.address}")
    private String gpsAddress;

    @Value("${user.gps.corpId}")
    private String corpId;
    @Value("${user.gps.corpSecret}")
    private String corpSecret;

    private static String accessToken;

    @Autowired
    private XyxcUserDao xyxcUserDao;

    @Autowired
    SyncEventServiceImpl syncEventService;

    @Override
    public List<Map<String, Object>> queryUserGpsList(String userId,String eventId) {
//        JSONArray dealList= syncEventService.getDealContentForWeb2(eventId).getJSONArray("rows");
//        List<Map<String, Object>> list = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(dealList)){
////            List<String> nameList = new ArrayList<>();
//            for(int i=0;i<dealList.size();i++){
//                JSONObject jsonObject = dealList.getJSONObject(i);
////                if(null != jsonObject.getJSONObject("projectTaskInfo")){
////                    String member_nikeName = jsonObject.getJSONObject("projectTaskInfo").getString("member_nikeName");
////                    if(!StringUtils.isEmpty(member_nikeName)){
////                        nameList.add(member_nikeName);
////                    }
////                    String to_member_name = jsonObject.getJSONObject("projectTaskInfo").getString("to_member_name");
////                    if(!StringUtils.isEmpty(to_member_name)){
////                        nameList.add(to_member_name);
////                    }
////                }
//                if(null != jsonObject.getJSONObject("projectTaskInfo")){
//                    String to_member_name = jsonObject.getJSONObject("projectTaskInfo").getString("to_member_name");
//                    if(!StringUtils.isEmpty(to_member_name)){
//                        list.addAll(xyxcUserDao.queryUserGpsList(to_member_name,null,null));
//                    }
//                }
//                String content = jsonObject.getString("operationContent");
//                if(!StringUtils.isEmpty(content) && !content.contains("处置完成") && content.contains("/")){
//                    String str2 = content.substring(content.indexOf("/"));
//                    if(!StringUtils.isEmpty(str2) && str2.contains("【") && str2.contains("】")){
////                        nameList.add(str2.substring(str2.indexOf("【")+1,str2.indexOf("】")));
//                        list.addAll(xyxcUserDao.queryUserGpsList(userId,str2.substring(str2.indexOf("【") + 1, str2.indexOf("】")),
//                                str2.substring(1,str2.indexOf("【"))));
//                    }
//                    if(str2.contains("向")){
//                        String str3 = content.substring(content.indexOf("向"));
//                        String name2 = str3.substring(str3.indexOf("【") + 1, str3.indexOf("】"));
//                        String dept2 = str3.substring(str3.indexOf("/")+1,str3.indexOf("【"));
//                        list.addAll(xyxcUserDao.queryUserGpsList(userId,name2,
//                                dept2));
//                    }
//
//                }
//            }
//        }
        List<Map<String,Object>> users = xyxcUserDao.queryUserGpsList(userId,null,null);
//        List<Map<String,Object>> result = new ArrayList<>();
//        for(Map<String,Object> map:list){
//            for(Map<String,Object> user:users){
//                if(user.get("userId").toString().equals(map.get("userId").toString())){
//                    result.add(map);
//                    continue;
//                }
//            }
//        }
        return users;
    }

    @Override
    public Map<String, Object> queryUserGpsListByUserId(String userId) {
        Map<String,Object> result = new HashMap<>();
        result.put("gps",xyxcUserDao.queryUserGpsListByUserId(userId));
        List<Map<String,Object>> list = xyxcUserDao.queryUserInfo2(userId,null,null,null);
//        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
//        String positions = "";
//        if(!CollectionUtils.isEmpty(jsonArray)){
//            positions = StringUtils.join(jsonArray.getJSONObject(0).getJSONArray("positions")," ");
//        }
//        list.get(0).put("positions",positions);
        result.put("userInfo",list);
        return result;
    }

    @Override
    public String handleToken(){
        logger.info("开始获取勤务人员token");
        RestTemplate restTemplate = new RestTemplate();
        try {
            JSONObject jsonObject = restTemplate.getForObject(gpsAddress + getTokenUrl+"?corpId="+corpId+"&corpSecret="+corpSecret,JSONObject.class);
            logger.info("勤务人员："+jsonObject);
            if(null != jsonObject && "200".equals(jsonObject.getString("code"))){
                accessToken = jsonObject.getJSONObject("data").getString("accessToken");
                logger.info("accessToken:"+accessToken);
            }
        }catch (Exception e){
            logger.error("获取勤务人员token异常："+e.getMessage());
        }
        return accessToken;
    }

    @Override
    public void syncUserGps(){
        logger.info("开始获取坐标列表");
        RestTemplate restTemplate = new RestTemplate();
        int pageNum = 1;
        boolean flag = true;
        try {
            do {
                JSONObject jsonObject = restTemplate.getForObject(gpsAddress + clockListUrl + pageNum + "&accessToken=" +accessToken,JSONObject.class);
                if(null != jsonObject && "200".equals(jsonObject.getString("code"))){
                    JSONArray data = jsonObject.getJSONArray("data");
                    JSONArray insertData = new JSONArray();
                    if(!CollectionUtils.isEmpty(data)){
                        for(int i = 0;i<data.size();i++){
                            JSONObject job = data.getJSONObject(i);
                            String gps = job.getString("currentPosition");
                            if(!StringUtils.isEmpty(gps)){
                                data.getJSONObject(i).put("gpsx",gps.substring(0, gps.indexOf(",")));
                                data.getJSONObject(i).put("gpsy",gps.substring(gps.indexOf(",")+1));
                            }else {
                                data.getJSONObject(i).put("gpsx","");
                                data.getJSONObject(i).put("gpsy","");
                            }
                            data.getJSONObject(i).put("dept",getDepartment(job.getString("userId"),job.getString("department")));
                            if(!CollectionUtils.isEmpty(job.getJSONArray("clockInfo"))){
                                JSONArray clockInfo = job.getJSONArray("clockInfo");
                                JSONObject tempObj = new JSONObject();
                                tempObj = data.getJSONObject(i);
                                tempObj.put("clockTime",clockInfo.getJSONObject(clockInfo.size()-1).getString("clockTime"));
                                insertData.add(tempObj);
                            }else {
                                JSONObject tempObj = data.getJSONObject(i);
                                tempObj.put("clockTime","");
                                insertData.add(tempObj);
                            }
                        }
                        if(!CollectionUtils.isEmpty(insertData)){
                            xyxcUserDao.saveUserGps(insertData);
                        }
                    }
                    if(data.size() < 1000){
                        flag = false;
                    }
                    pageNum++;
                }else if(null != jsonObject && "401".equals(jsonObject.getString("code"))){
                    logger.info("token 过期 重新获取");
                    if(!StringUtils.isEmpty(handleToken())){
                        syncUserGps();
                    }
                }
            }while (flag);
        }catch (Exception e){
            logger.error("同步勤务人员坐标异常："+e.getMessage());
        }
    }

    private String getDepartment(String userId,String department) {
        String result = "";
        String dept = xyxcUserDao.queryUserDept(userId);
        if(!StringUtils.isEmpty(dept)){
            result = dept;
        }
        RestTemplate restTemplate = new RestTemplate();
        try {
            if(StringUtils.isEmpty(dept) && !StringUtils.isEmpty(department)){
                List<String> departmentIdList = Arrays.asList(department.split(","));
                for(String departmentId:departmentIdList){
                    String tempName = "";
                    if("鹿溪村".equals(departmentId)){
                        tempName = departmentId;
                    }else {
                        String url = gpsAddress + deptUrl + accessToken + "&departmentId=" + departmentId;
//                logger.error("获取部门信息url："+url);
                        JSONObject jsonObject = restTemplate.getForObject(url,JSONObject.class);
                        //logger.error("jsonObject:"+jsonObject);
//                logger.error("获取部门信息jsonObject："+jsonObject);
                        if(null != jsonObject && !"500".equals(jsonObject.getString("code"))){
                            JSONObject data = jsonObject.getJSONObject("data");
                            String departmentName = data.getString("name");
                            Boolean flag = true;
                            do {
                                if(null != data.getJSONObject("parent")){
                                    data = data.getJSONObject("parent");
                                    departmentName = data.getString("name") + "/" + departmentName;
                                }else {
                                    flag = false;
                                }
                            }while (flag);
                            tempName= departmentName;
                        }
                    }
                    if(!StringUtils.isEmpty(result)){
                        result = result + "," + tempName;
                    }else {
                        result = tempName;
                    }
                }
            }
        }catch (Exception e){
            logger.error("获取部门信息异常："+e.getMessage());
        }
        return result;
    }


//    public static void main(String[] args) {
//       String str = "";
//       JSONObject jsonObject = JSONObject.parseObject(str);
//        if("200".equals(jsonObject.getString("code"))){
//            JSONArray data = jsonObject.getJSONArray("data");
//            if(!CollectionUtils.isEmpty(data)){
//                for(int i = 0;i<data.size();i++){
//                    JSONObject job = data.getJSONObject(i);
//                    String gps = job.getString("currentPosition");
//                    data.getJSONObject(i).put("gpsx",gps.substring(0, gps.indexOf(",")));
//                    data.getJSONObject(i).put("gpsy",gps.substring(gps.indexOf(",")+1));
//                    data.getJSONObject(i).put("clockTime",job.getJSONArray("clockInfo").getJSONObject(0).getString("clockTime"));
//                    data.getJSONObject(i).put("dept",null == job.getJSONObject("departments")?null : String.valueOf(job.getJSONObject("departments").values()));
//                }
//            }
//        }
//    }
}
