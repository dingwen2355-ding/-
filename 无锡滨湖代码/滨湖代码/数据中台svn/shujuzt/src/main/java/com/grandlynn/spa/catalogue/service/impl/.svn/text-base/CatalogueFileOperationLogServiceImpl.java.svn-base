package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.FileImportFromEnum;
import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.utils.FileOperateFactory;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileImportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileOperationLogGetCurrentStatusRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileOperationLogGetLogRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileTemplateDownloadRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueFileOperationLogGetCurrentStatusResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueFileOperationLogGetLogResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueImportFileResponse;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueFileOperationLogMapper;
import com.grandlynn.spa.catalogue.mq.producer.SendExcelFileIdProducer;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.AbstractValidFile;
import com.grandlynn.spa.catalogue.service.CatalogueFileOperationLogService;
import com.grandlynn.spa.catalogue.service.FileOperateStrategy;
import com.grandlynn.util.pageHelper.PageBean;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.SerializedLambda;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("catalogueFileOperationLogService")
public class CatalogueFileOperationLogServiceImpl extends ServiceImpl<CatalogueFileOperationLogMapper, CatalogueFileOperationLogDO> implements CatalogueFileOperationLogService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueFileOperationLogServiceImpl.class);
   @Autowired
   private FastFileStorageClient fastFileStorageClient;
   @Autowired
   private SendExcelFileIdProducer producer;

   public void downloadTemplate(HttpServletResponse response, CatalogueFileTemplateDownloadRequest request) throws IOException {
      FileTemplateEnum templateEnum = FileTemplateEnum.findFullPathByCode(request.getTemplateName());
      if (templateEnum != null) {
         try {
            String name = templateEnum.getName();
            byte[] buffer = FileUtil.readResourceFile(templateEnum);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      }

   }

   public CatalogueImportFileResponse uploadAndValidFiles(MultipartFile multipartFile, CatalogueFileImportRequest request) {
      AbstractValidFile validFileAbstract = FileOperateFactory.getAbstractValidFile(request.getImportPosition());
      CatalogueImportFileResponse response = validFileAbstract.uploadAndValidFiles(multipartFile, request);
      ExcelFileIdMessageDTO messageDTO = request.clone(ExcelFileIdMessageDTO.class);
      messageDTO.setFileId(response.getFileId());
      messageDTO.setOperateFrom(FileImportFromEnum.findEnumByCode(request.getImportPosition()).getNo());
      messageDTO.setImportPosition(request.getImportPosition());
      messageDTO.setUser(SecurityUtil.currentUser());
      this.producer.sendFileIdMessage(messageDTO);
      return response;
   }

   public void exportFile(HttpServletResponse response, CatalogueFileExportRequest request) {
      FileOperateStrategy fileOperateStrategy = FileOperateFactory.getFileOperateStrategy(request.getExportPosition());
      log.info("开始执行exportFile==>ok");
      if (request.getFileType() != null && request.getFileType() == 1) {
         log.info("开始执行exportWord==>ok");
         fileOperateStrategy.exportWord(response, request);
      } else if (request.getFileType() != null && request.getFileType() == 2) {
         log.info("开始执行exportZip==>ok");
         fileOperateStrategy.exportZip(response, request);
      } else {
         log.info("开始执行exportExcel==>ok");
         fileOperateStrategy.exportExcel(response, request);
      }

      log.info("执行exportFile==>ok");
   }

   public PageBean<CatalogueFileOperationLogGetLogResponse> getFileOperationLog(CatalogueFileOperationLogGetLogRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      QueryWrapper<CatalogueFileOperationLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(CatalogueFileOperationLogDO::getOperationFrom, request.getOperationFrom())
              .eq(CatalogueFileOperationLogDO::getOperationType, request.getOperationType())
              .eq(request.getFileSuffix() != null, CatalogueFileOperationLogDO::getFileSuffix, request.getFileSuffix())
              .like(request.getFileName() != null, CatalogueFileOperationLogDO::getFileName, request.getFileName())
              .orderByDesc(BaseEntity::getCreatedTime);
      List<CatalogueFileOperationLogDO> list = this.baseMapper.selectList(queryWrapper);
      return ObjectCloneUtils.convertPageBean(new PageBean(list), CatalogueFileOperationLogGetLogResponse.class);
   }

   public List<CatalogueFileOperationLogGetCurrentStatusResponse> getFileCurrentStatus(CatalogueFileOperationLogGetCurrentStatusRequest request) {
      QueryWrapper<CatalogueFileOperationLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .in(BaseEntity::getId, request.getIdList());
      List<CatalogueFileOperationLogDO> list = this.baseMapper.selectList(queryWrapper);
      return ObjectCloneUtils.convertList(list, CatalogueFileOperationLogGetCurrentStatusResponse.class);
   }

}
