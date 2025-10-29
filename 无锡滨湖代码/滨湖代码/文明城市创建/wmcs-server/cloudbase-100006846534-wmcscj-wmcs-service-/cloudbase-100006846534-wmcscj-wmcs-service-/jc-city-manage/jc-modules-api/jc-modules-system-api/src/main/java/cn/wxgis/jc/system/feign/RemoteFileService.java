package cn.wxgis.jc.system.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.constant.ServiceNameConstants;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.system.feign.factory.RemoteFileFallbackFactory;
import cn.wxgis.jc.system.po.SysFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {

    /**
     * 通过文件id集合查询文件列表
     *
     * @param ids 文件id集合
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.FILE_LIST_BYIDS)
    JsonResult<List<SysFile>> listByIds(@RequestParam("ids") List<String> ids, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 删除文件
     * @param fileNames 文件名称
     * @param source
     */
    @PostMapping(ProviderConstant.FILE_DELETE)
    JsonResult delete(@RequestParam("fileNames") List<String> fileNames, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 下载地址
     * @param url
     * @param source
     */
    @PostMapping(ProviderConstant.FILE_DOWN_TI_FILE)
    JsonResult downTiFile(@RequestParam("url") String url, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
