package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.entity.SysTemplate;
import org.springframework.web.multipart.MultipartFile;

public interface SysTemplateService extends IService<SysTemplate> {
   SysTemplate getByCode(String code);

   void uploadFile(String code, MultipartFile multipartFile);
}
