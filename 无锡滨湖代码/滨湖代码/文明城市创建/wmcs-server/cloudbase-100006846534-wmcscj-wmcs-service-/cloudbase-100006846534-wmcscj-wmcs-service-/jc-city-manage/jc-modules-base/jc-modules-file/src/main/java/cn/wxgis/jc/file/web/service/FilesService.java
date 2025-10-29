package cn.wxgis.jc.file.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysFile;
import cn.wxgis.jc.system.req.SysFileUploadRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FilesService extends BaseService<SysFile> {

    /**
     * 文件上传接口
     * @param file 文件参数
     * @param isToken 是否传token
     * @return 存放路径
     */
    String upload(MultipartFile file, boolean isToken);

    /**
     * 文件下载接口
     * @param file
     * @param response
     * @return 存放路径
     */
    void download(String file, HttpServletResponse response, boolean isToken);

    /**
     * 文件上传接口
     * @param request 文件信息
     * @param isToken 是否传token
     * @return 存放对象
     */
    SysFile uploadFile(SysFileUploadRequest request, boolean isToken);

    /**
     * 下载文件
     * @param fileName 文件名
     * @param response
     * @param isToken 是否传token
     */
    void downloadFile(String fileName, HttpServletResponse response, boolean isToken);

    /**
     * 删除文件
     * @param fileNames
     */
    void delete(List<String> fileNames);

    void downloadZip(String ids, HttpServletResponse response, boolean isToken);

}
