package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.entity.SysTemplate;
import com.grandlynn.spa.catalogue.mapper.SysTemplateMapper;
import com.grandlynn.spa.catalogue.service.SysTemplateService;
import com.grandlynn.util.extension.ApplicationException;
import java.io.IOException;
import java.lang.invoke.SerializedLambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SysTemplateServiceImpl extends ServiceImpl<SysTemplateMapper, SysTemplate> implements SysTemplateService {
   private static final Logger log = LoggerFactory.getLogger(SysTemplateServiceImpl.class);

   public SysTemplate getByCode(String code) {
      LambdaQueryWrapper<SysTemplate> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(SysTemplate::getCode, code);
      queryWrapper.eq(BaseEntity::getDeleted, 0);
      queryWrapper.orderByDesc(BaseEntity::getId);
      queryWrapper.last(" limit 1");
      return (SysTemplate)this.getOne(queryWrapper);
   }

   public void uploadFile(String code, MultipartFile multipartFile) {
      try {
         byte[] bytes = StreamUtils.copyToByteArray(multipartFile.getInputStream());
         this.save(new SysTemplate(code, bytes, multipartFile.getOriginalFilename()));
      } catch (IOException var4) {
         log.error("模板文件上传失败=======>");
         var4.printStackTrace();
         throw new ApplicationException(var4.getMessage());
      }
   }

}
