package cn.wxgis.jc.system.feign;


import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.constant.ServiceNameConstants;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.system.feign.factory.RemoteMinioFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "remoteMinioService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteMinioFallbackFactory.class)
public interface RemoteMinioService {


    /**
     * 删除文件
     * @param fileNames 文件名称
     * @param source
     */
    @PostMapping(ProviderConstant.FILE_DELETE)
    JsonResult delete(@RequestParam("fileNames") List<String> fileNames, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


}
