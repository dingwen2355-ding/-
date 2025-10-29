package cn.wxgis.jc.file.web.controller;

import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.file.FileUtils;
import cn.wxgis.jc.file.web.service.AiFilesService;
import cn.wxgis.jc.file.web.service.FilesService;
import cn.wxgis.jc.system.po.SysFile;
import cn.wxgis.jc.system.req.SysFileUploadRequest;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件请求处理
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(value = "SysFileApiController", tags = "附件相关Api(过滤token验证)")
public class SysFileApiController {

    private final FilesService fileService;
    private final AiFilesService aiFilesService;

    /**
     * 文件上传请求
     */
    @GetMapping("/test")
    public JsonResult test(String url) {
        return JsonResult.ok(aiFilesService.downTiFile(url, false));
    }


    /**
     * 文件上传请求
     */
    @PostMapping("/upload")
    public JsonResult upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = fileService.upload(file, false);
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", FileUtils.getName(url));
            map.put("url", url);
            return JsonResult.ok(map);
        }
        catch (Exception e) {
            log.error("上传文件失败", e);
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 文件上传请求
     */
    @PostMapping("/uploadFile")
    public JsonResult uploadFile(SysFileUploadRequest uploadRequest) {
        try {
            // 上传并返回访问地址
            SysFile sysFile = fileService.uploadFile(uploadRequest, false);
            return JsonResult.ok(sysFile);
        }
        catch (Exception e) {
            log.error("上传文件失败", e);
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 下载附件
     */
    @GetMapping("/downloadFile")
    public JsonResult downloadFile(String id,
                                   HttpServletResponse response) {
        fileService.downloadFile(id, response, false);
        return JsonResult.ok();
    }

    /**
     * 下载AI的文件
     */
    @GetMapping("/downloadAiFile")
    public JsonResult downloadAiFile(String url, HttpServletResponse response) {
        aiFilesService.downAiFile(url, response);
        return JsonResult.ok();
    }
}
