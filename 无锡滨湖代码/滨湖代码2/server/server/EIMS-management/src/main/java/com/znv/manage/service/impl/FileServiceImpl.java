package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${file.name.white}")
    private String fileNameWhite;

    @Value("${file.name.white.onOff}")
    private boolean fileNameWhiteOnOff;

    @Value("${filePath}")
    private String filePath;
    @Value("${urlPath}")
    private String urlPath;

    @Override
    public Result uploadFile(MultipartFile file, String route) {
        Result result = new Result();
        try {
            if (fileNameWhiteOnOff && !CommonUtils.checkFileName(file, fileNameWhite)) {
                throw new BusinessException("不合法的文件形式");
            }

            String extend = "";
            if (StringUtils.isNotBlank(route)) {
                extend = "/" + route;
            }
            result.setData(CommonUtils.uploadFile(file, filePath + extend, urlPath + extend));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result delete(String url) {
        Result result = new Result();
        try {
            result.setData(CommonUtils.deleteFile(url, filePath, urlPath));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
