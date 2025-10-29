package cn.wxgis.jc.file.web.service;

import javax.servlet.http.HttpServletResponse;

public interface AiFilesService {


    /**
     * 下载TI图片
     */
    String downTiFile(String url, boolean isToken);

    void downAiFile(String url, HttpServletResponse response);
}
