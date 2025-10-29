package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.CommunicationInfo;
import com.znv.manage.common.bean.DeptInfo;
import com.znv.manage.common.bean.VideoConferencesInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class DingDingUtil {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(DingDingUtil.class);

    private static String tokenUrl = "https://api.dingtalk.com/v1.0/oauth2/accessToken";
    private static String deptInfoUrl = "https://oapi.dingtalk.com/topapi/v2/department/get?access_token=";
    private static String deptListUrl = "https://oapi.dingtalk.com/topapi/v2/department/listsubid?access_token=";
    private static String userListUrl = "https://oapi.dingtalk.com/topapi/v2/user/list?access_token=";
    private static String videoConferencesUrl = "https://api.dingtalk.com/v1.0/conference/videoConferences?access_token=";
    private static RestTemplate restTemplate = new RestTemplate();


    private static String unionId;

    @Value("${unionId}")
    public void setUnionId(String id){
        unionId = id;
    }

    /*
     * 获取部门信息
     * */
    public static DeptInfo getDeptInfo(String deptId) {
        DeptInfo deptInfo = new DeptInfo();
        String url = "";
        try {
            url = deptInfoUrl  + getAccessToken();
            JSONObject msg = new JSONObject();
            msg.put("language", "zh_CN");
            msg.put("dept_id", deptId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(url, request, JSONObject.class);
            deptInfo = JSON.toJavaObject(jsonObject.getJSONObject("result"),DeptInfo.class);
        } catch (Exception e) {
            logger.error(url + "数据获取异常：" + e.getMessage());
        }
        return deptInfo;
    }

    /*
    * 获取子部门
    * */
    public static List<String> getDeptList(String deptId) {
        List<String> list = new ArrayList<>();
        String url = "";
        try {
            url = deptListUrl  + getAccessToken();
            JSONObject msg = new JSONObject();
            msg.put("dept_id", deptId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(url, request, JSONObject.class);
            list = jsonObject.getJSONObject("result").getJSONArray("dept_id_list").toJavaList(String.class);
        } catch (Exception e) {
            logger.error(url + "数据获取异常：" + e.getMessage());
        }
        return list;
    }

    /*
     * 获取用户信息
     * */
    public static List<CommunicationInfo> getUserList(String deptId,Integer cursor,Integer size) {
        List<CommunicationInfo> list = new ArrayList<>();
        String url = "";
        try {
            url = userListUrl  + getAccessToken();
            JSONObject msg = new JSONObject();
            msg.put("cursor", cursor);
            msg.put("contain_access_limit", false);
            msg.put("size", size);
            msg.put("order_field", "modify_desc");
            msg.put("language", "zh_CN");
            msg.put("dept_id", deptId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(url, request, JSONObject.class);
            list = jsonObject.getJSONObject("result").getJSONArray("list").toJavaList(CommunicationInfo.class);
            for(CommunicationInfo communicationInfo : list){
                communicationInfo.setDeptIdList(communicationInfo.getDeptIdList()
                        .replace("[","").replace("]",""));
            }
        } catch (Exception e) {
            logger.error(url + "数据获取异常：" + e.getMessage());
        }
        return list;
    }

    /*
     * 创建视频会议
     * */
    public static VideoConferencesInfo videoConferences(List<String> unionIds,String titleName) {
        String url = "";
        VideoConferencesInfo videoConferencesInfo = new VideoConferencesInfo();
        try {
            url = videoConferencesUrl  + getAccessToken();
            JSONObject msg = new JSONObject();
            msg.put("userId", unionId);
            msg.put("confTitle", titleName);
            msg.put("inviteUserIds", unionIds);
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-acs-dingtalk-access-token",getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject jsonObject = restTemplate.postForObject(url, request, JSONObject.class);
            videoConferencesInfo = jsonObject.toJavaObject(VideoConferencesInfo.class);
        } catch (Exception e) {
            logger.error(url + "数据获取异常：" + e.getMessage());
        }
        return videoConferencesInfo;
    }

    private static String getAccessToken() {
        String accessToken = "";
        try {
            JSONObject msg = new JSONObject();
            msg.put("appKey", "ding84h26ldtsi3v3iww");
            msg.put("appSecret", "bcXps3NwF39eN3H-RTuzFPck6NTDsub4Do_rGIf3qB66o1J6SbjgWhrFWFVjBndO");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
            JSONObject result = restTemplate.postForObject(tokenUrl, request, JSONObject.class);
            accessToken = result.getString("accessToken");
        } catch (Exception e) {
            logger.error("accessToken 获取异常：" + e.getMessage());
        }
        return accessToken;
    }

    public static void main(String[] args) {
        //System.out.println(DingDingUtil.getDeptInfo("170279654"));

        //System.out.println(DingDingUtil.getDeptList("170279654"));

        //System.out.println(DingDingUtil.getUserList("171574321",10));
    }
}
