package com.wxgis.topic.influx.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.protobuf.ServiceException;
import com.wxgis.topic.influx.config.TiConfig;
import com.wxgis.topic.influx.service.TopicService;
import com.wxgis.topic.influx.util.DateUtils;
import com.wxgis.topic.influx.util.FileUtils;
import com.wxgis.topic.influx.util.MultipartFileUtil;
import com.wxgis.topic.influx.util.tisign.TiSign;
import com.wxgis.topic.vo.FileVo;
import com.wxgis.topic.vo.WmcsWarnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("ti")
public class TiController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/create")
    public String create(@RequestBody WmcsWarnVo warn){
         topicService.setInfluxDataByTopic(warn);
         return "ok";
    }

    @GetMapping("/DownloadFile")
    public String DownloadFile(String imageUrl) {
        try{
            String host = TiConfig.ip;               //访问网关的host
            String createFileAction = "CreateTIDataCenterFileManagerDownloadUrl";
            String createFileVersion = "2021-06-06";
            String createFileService = "ti-dc-data-source-backend";
            String contentType = "application/json";
            String createFileHttpMethod = "POST";

            // 生成创建文件的TiSign对象
            TiSign createFileTs = new TiSign(host, createFileAction, createFileVersion, createFileService, contentType, createFileHttpMethod, TiConfig.secretId, TiConfig.secretKey);
            String createFileAuthorization = createFileTs.CreateHeaderWithSignature(new HashMap<>());
            log.info("==================== 创建文件的签名字符串 Authorization ====================");
            log.info("authorization: " + createFileAuthorization);
            // 封装返回调用参数
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("TiBusinessId", 1);    //公共参数，业务ID
            tokenMap.put("TiProjectId", 1);     //公共参数，项目ID
            tokenMap.put("Language", "zh-CN");      //公共参数，错误信息的返回语言，取值：中文zh-CN、英文en-US。
            tokenMap.put("IsTemp", false);
            tokenMap.put("MaxAge", 1);
            tokenMap.put("Keys", imageUrl.split(","));
            log.info("======请求参数：" + JSONObject.toJSONString(tokenMap));
            // 封装请求头部参数
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", createFileAuthorization);
            headers.put("Content-Type", contentType);
            headers.put("Host", host);
            headers.put("X-TC-Service", createFileService);
            headers.put("X-TC-Action", createFileAction);
            headers.put("X-TC-Timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            headers.put("X-TC-Version", createFileVersion);
            String tokenObj = HttpUtil.createPost(TiConfig.getGateway).body(JSON.toJSONBytes(tokenMap)).addHeaders(headers).execute().body();
            log.info("==================== 执行方法CreateTIDataCenterFileManagerDownloadUrl ====================");
            log.info(JSONObject.toJSONString(HttpUtil.createPost(TiConfig.getGateway).body(JSON.toJSONBytes(tokenMap)).addHeaders(headers)));
            FileVo createFile = JSONObject.parseObject(tokenObj, FileVo.class);
            log.info("==================== 方法返回结果  ====================");
            if (StringUtils.isEmpty(createFile.getResponse().getURLs()) || createFile.getResponse().getURLs().length <= 0) throw new ServiceException("下载图片出错，请检查下载接口");
            List<String> imageList = new ArrayList<>();
            if (!StringUtils.isEmpty(createFile)) {
                String[] fileUrls = createFile.getResponse().getURLs();
                String fileUrl = fileUrls[0];
                String downFileAction = "DownloadFile";
                String downFileVersion = "";
                String downFileService = "";
                String downFileHttpMethod = "GET";
                // 生成下载文件的TiSign对象
                TiSign downFileTs = new TiSign(host, downFileAction, downFileVersion, downFileService, contentType, downFileHttpMethod, TiConfig.secretId, TiConfig.secretKey);
                String downFileAuthorization = downFileTs.CreateHeaderWithSignature(new HashMap<>());
                log.info("==================== 下载文件的签名字符串 Authorization ====================");
                log.info("authorization: " + downFileAuthorization);
                String downUrl = "http://" + host + "/" + fileUrl;
                log.info("======下载图片地址：" + downUrl);
                Map<String, String> hears = new HashMap<>();
                hears.put("Authorization", downFileAuthorization);
                hears.put("Host", host);
                hears.put("Content-Type", contentType);
                hears.put("X-TC-Service", downFileService);
                hears.put("X-TC-Action", downFileAction);
                hears.put("X-TC-Timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                hears.put("X-TC-Version", downFileVersion);
                InputStream inputStream = HttpUtil.createGet(downUrl).addHeaders(hears).execute().bodyStream();
                log.info("==================== 执行方法DownloadFile ====================");
                // 图片
                String fileName = FileUtils.getName(fileUrl);
                MultipartFile file = new MultipartFileUtil(fileName, fileName, "application/octet-stream", inputStream);
            }
            if (!StringUtils.isEmpty(imageList) && !StringUtils.isEmpty(imageList.get(0))) {
                return String.join(",", imageList);
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
        return "ok";
    }

}
