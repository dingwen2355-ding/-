package cn.wxgis.jc.file.web.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.file.web.service.AiFilesService;
import cn.wxgis.jc.file.web.service.FilesService;
import cn.wxgis.jc.file.web.service.SysFileService;
import cn.wxgis.jc.security.annotation.InnerAuth;
import cn.wxgis.jc.system.po.SysFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class RemoteFileContrller {

    private final FilesService fileService;
    private final AiFilesService aiFilesService;
    private final SysFileService sysFileService;

    /**
     * 根据文件id获取文件列表
     */
    @InnerAuth
    @PostMapping(ProviderConstant.FILE_LIST_BYIDS)
    public JsonResult listByIds(@RequestParam("ids") List<String> ids) {
        List<SysFile> deptList = sysFileService.listByIds(ids);
        return JsonResult.ok(deptList);
    }

    @InnerAuth
    @PostMapping(ProviderConstant.FILE_DELETE)
    public JsonResult deleteFiles(@RequestParam("fileNames") List<String> fileNames) {
        fileService.delete(fileNames);
        return JsonResult.ok();
    }

    @InnerAuth
    @PostMapping(ProviderConstant.FILE_DOWN_TI_FILE)
    public JsonResult downTiFile(@RequestParam("url") String url) {
        return JsonResult.ok(aiFilesService.downTiFile(url, true));
    }
}
