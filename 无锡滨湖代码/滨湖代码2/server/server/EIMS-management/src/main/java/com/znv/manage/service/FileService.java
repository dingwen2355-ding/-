package com.znv.manage.service;

import com.znv.manage.common.bean.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Result uploadFile(MultipartFile file, String route);

    Result delete(String url);
}
