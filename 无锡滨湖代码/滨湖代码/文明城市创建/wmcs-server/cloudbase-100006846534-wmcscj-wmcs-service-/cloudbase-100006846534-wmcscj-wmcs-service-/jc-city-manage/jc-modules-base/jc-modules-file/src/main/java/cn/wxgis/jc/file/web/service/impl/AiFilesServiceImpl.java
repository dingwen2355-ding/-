package cn.wxgis.jc.file.web.service.impl;

import cn.hutool.http.HttpUtil;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.util.file.FileUtils;
import cn.wxgis.jc.common.util.file.MultipartFileUtil;
import cn.wxgis.jc.common.util.tisign.TiSign;
import cn.wxgis.jc.file.web.service.AiFilesService;
import cn.wxgis.jc.file.web.service.FilesService;
import cn.wxgis.jc.file.web.vo.FileVo;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AiFilesServiceImpl implements AiFilesService {

    private final String getGateway = "http://2.21.138.49/gateway";
    private final String host = "2.21.138.49";               //访问网关的host
    private final String secretId = "a40c373844364e69b646100a49";
    private final String secretKey = "acd182953fd64cf0ba01a87de0";
    private final String createFileAction = "CreateTIDataCenterFileManagerDownloadUrl";
    private final String createFileVersion = "2021-06-06";
    private final String createFileService = "ti-dc-data-source-backend";
    private final String contentType = "application/json";
    private final String createFileHttpMethod = "POST";

    @Autowired
    private FilesService filesService;

    @Override
    public String downTiFile(String url, boolean isToken) {
        try {
            FileVo createFile = this.getFileVo(url);
            log.info("==================== 方法返回结果  ====================");
            if (org.springframework.util.StringUtils.isEmpty(createFile.getResponse().getURLs()) || createFile.getResponse().getURLs().length <= 0) throw new ServiceException("下载图片出错，请检查下载接口");
            if (!org.springframework.util.StringUtils.isEmpty(createFile)) {
                String[] fileUrls = createFile.getResponse().getURLs();
                String fileUrl = fileUrls[0];
                String downFileAction = "DownloadFile";
                String downFileVersion = "";
                String downFileService = "";
                String downFileHttpMethod = "GET";
                // 生成下载文件的TiSign对象
                TiSign downFileTs = new TiSign(host, downFileAction, downFileVersion, downFileService, contentType, downFileHttpMethod, secretId, secretKey);
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
                String fileName = FileUtils.getName(url);
                MultipartFile file = new MultipartFileUtil(fileName, fileName, "application/octet-stream", inputStream);
                return filesService.upload(file, isToken);
            }
            return null;
        } catch (Exception e) {
            log.info("=============获取图片地址失败" + JSONObject.toJSONString(e.getMessage()));
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void downAiFile(String url, HttpServletResponse response) {
        try {
            FileVo createFile = this.getFileVo(url);
            log.info("==================== 方法返回结果  ====================");
            if (org.springframework.util.StringUtils.isEmpty(createFile.getResponse().getURLs()) || createFile.getResponse().getURLs().length <= 0) throw new ServiceException("下载图片出错，请检查下载接口");
            if (!org.springframework.util.StringUtils.isEmpty(createFile)) {
                String[] fileUrls = createFile.getResponse().getURLs();
                String fileUrl = fileUrls[0];
                String downFileAction = "DownloadFile";
                String downFileVersion = "";
                String downFileService = "";
                String downFileHttpMethod = "GET";
                // 生成下载文件的TiSign对象
                TiSign downFileTs = new TiSign(host, downFileAction, downFileVersion, downFileService, contentType, downFileHttpMethod, secretId, secretKey);
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
                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                int n;
                byte[] buff = new byte[1024];
                while((n=inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, n);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }
        } catch (Exception e) {
            log.info("=============获取图片地址失败" + JSONObject.toJSONString(e.getMessage()));
            e.printStackTrace();
        }
    }


    /**
     * 通过调用Ti获取文件信息
     */
    private FileVo getFileVo(String url) throws Exception {
        // 生成创建文件的TiSign对象
        TiSign createFileTs = new TiSign(host, createFileAction, createFileVersion, createFileService, contentType, createFileHttpMethod, secretId, secretKey);
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
        tokenMap.put("Keys", url.split(","));
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
        String tokenObj = HttpUtil.createPost(getGateway).body(JSON.toJSONBytes(tokenMap)).addHeaders(headers).execute().body();
        log.info("==================== 执行方法CreateTIDataCenterFileManagerDownloadUrl ====================");
        log.info(JSONObject.toJSONString(HttpUtil.createPost(getGateway).body(JSON.toJSONBytes(tokenMap)).addHeaders(headers)));
        FileVo createFile = JSONObject.parseObject(tokenObj, FileVo.class);
        return createFile;
    }
}
