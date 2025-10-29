package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */
@Slf4j
@Component
public class HikUtils {

    private static String host;
    private static String appKey;
    private static String appSecret;

    @Value("${hik.host}")
    public void setHost(String host) {
        HikUtils.host = host;
    }

    @Value("${hik.appKey}")
    public void setAppKey(String appKey) {
        HikUtils.appKey = appKey;
    }

    @Value("${hik.appSecret}")
    public void setAppSecret(String appSecret) {
        HikUtils.appSecret = appSecret;
    }

    @PostConstruct
    private void initMethod() {
        ArtemisConfig.host = host; // 代理API网关nginx服务器ip端口
        ArtemisConfig.appKey = appKey;  // 秘钥appkey
        ArtemisConfig.appSecret = appSecret;// 秘钥appSecret
        jsonObject = new JSONObject();
    }

    /**
     * 能力开放平台的网站路径
     * TODO 路径不用修改，就是/artemis
     */
    public static final String ARTEMIS_PATH = "/artemis";

    public static Map<String,String> cameraMap;
    public static Map<String,String> cameraMapRtsp;

    public static JSONObject jsonObject;

    // public static String getHls(String indexCode){
    //
    //     final String getSecurityApi = ARTEMIS_PATH + "/api/mss/v1/hls/" + indexCode;
    //     Map<String, String> path = new HashMap<String, String>(2) {
    //         {
    //             put("https://", getSecurityApi);
    //         }
    //     };
    //     String result ;
    //     try{
    //         result = ArtemisHttpUtil.doGetArtemis(path, null, null, null, null);
    //     }catch (Exception e){
    //         result = e.getMessage();
    //     }
    //
    //     return result;
    // }

    // public static String getRtsp(String indexCode){
    //     final String getSecurityApi = ARTEMIS_PATH + "/api/vms/v1/rtsp/basic/" + indexCode;
    //     Map<String, String> path = new HashMap<String, String>(2) {
    //         {
    //             put("https://", getSecurityApi);
    //         }
    //     };
    //     String result ;
    //     try{
    //         result = ArtemisHttpUtil.doGetArtemis(path, null, null, null, null);
    //     }catch (Exception e){
    //         result = e.getMessage();
    //     }
    //
    //     return result;
    // }

    /**
     * {\"code\":\"0\",\"msg\":\"success\",\"data\":{\"url\":\"rtsp://2.14.29.31:554/openUrl/TgTLySI\"}}
     * @param indexCode
     * @return
     */
    public static String getRtsp(String indexCode){

        String previewURLsDataApi = ARTEMIS_PATH +"/api/video/v1/cameras/previewURLs";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",previewURLsDataApi);
            }
        };

        /**
         * "streamType": 0,
         *     "protocol": "rtsp",
         *     "transmode": 1,
         *     "expand": "transcode=0",
         *     "streamform": "ps"
         */
        JSONObject param  = new JSONObject();
        param.put("cameraIndexCode",indexCode);
        param.put("protocol","rtsp");
        // param.put("streamType",0);
        // param.put("transmode",1);
        // param.put("expand","transcode=0");
        // param.put("streamform","rtp");
        String body= JSON.toJSONString(param);
        String temp =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json",null);
        JSONObject jsonObject = JSONObject.parseObject(temp);
        if ("success".equals(jsonObject.getString("msg").toLowerCase())){
            return jsonObject.getJSONObject("data").getString("url");
        }else {
            return "";
        }

    }

    public static String getHls(String indexCode){

        String previewURLsDataApi = ARTEMIS_PATH +"/api/video/v1/cameras/previewURLs";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",previewURLsDataApi);
            }
        };
        JSONObject param  = new JSONObject();

        //取cameraIndexCode
        param.put("cameraIndexCode",indexCode);

        //协议类型（rtsp-rtsp协议,rtmp-rtmp协议,hls-hLS协议,ws-Websocket协议），未填写为rtsp协议
        param.put("protocol","hls");//hik、rtsp、rtmp、hls

        //码流类型(0-主码流,1-子码流),未填默认为主码流
        param.put("streamType",1);

        //传输协议（传输层协议），0:UDP 1:TCP 默认是TCP 注：GB28181 2011及以前版本只支持UDP传输
        param.put("transmode",1);

        //此字段非必要不建议指定，拓展字段（标准协议取流不需要扩展字段信息 ）
        //当protocol为rtsp时： 支持指定streamform=rtp，表示使用标准RTSP协议，典型如使用VLC播放。
        //当使用海康取流播放工具如视频SDK时，请勿指定streamform=rtp。
        //支持指定transcode=1，表示将H265编码视频转换成H264编码，典型使用场景如HLS播放。
        //指定transcode=0表示不转码，默认为transcode=0。
        //可以同时指定streamform与transcode，但二者必须使用“&”连接起来，如transcode=1&streamform=rtp
        param.put("expand","transcode=1&streamform=rtp");

        String body= JSON.toJSONString(param);
        String temp =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json",null);
        JSONObject jsonObject = JSONObject.parseObject(temp);
        if ("success".equals(jsonObject.getString("msg").toLowerCase())){
            return jsonObject.getJSONObject("data").getString("url");
        }else {
            return "";
        }

    }

    //分页获取监控点资源
    public static String getCameras(Integer pageNo,Integer pageSize){
        String camerasDataApi = ARTEMIS_PATH +"/api/resource/v1/cameras";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",camerasDataApi);
            }
        };
        CamerasRequest camerasRequest = new CamerasRequest();
        camerasRequest.setPageNo(pageNo);
        camerasRequest.setPageSize(pageSize);
        camerasRequest.setTreeCode("0");
        String body= JSON.toJSONString(camerasRequest);
        String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json",null);
        return result;
    }

    public static class CamerasRequest {
        private Integer pageNo;
        private Integer pageSize;
        private String treeCode;

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public String getTreeCode() {
            return treeCode;
        }

        public void setTreeCode(String treeCode) {
            this.treeCode = treeCode;
        }
    }

}
