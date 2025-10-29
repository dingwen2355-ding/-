package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.FileImportFromEnum;
import com.grandlynn.spa.catalogue.common.enums.FileOperateTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.FileTypeEnum;
import com.grandlynn.spa.catalogue.common.utils.EasyExcel;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileImportRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueImportFileResponse;
import com.grandlynn.spa.catalogue.dto.ExportOrgTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.excel.listener.ValidExceLegalListener;
import com.grandlynn.spa.catalogue.mapper.CatalogueFileOperationLogMapper;
import com.grandlynn.spa.catalogue.service.AbstractValidFile;
import com.grandlynn.util.extension.ApplicationException;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("orgValidFilesAbstractImpl")
public class OrgValidFilesAbstractImpl extends AbstractValidFile {
   private static final Logger log = LoggerFactory.getLogger(OrgValidFilesAbstractImpl.class);
   @Autowired
   private FastFileStorageClient fastFileStorageClient;
   @Resource
   private CatalogueFileOperationLogMapper catalogueFileOperationLogMapper;

   public void validFiles(MultipartFile multipartFile, BaseRequest request) {
      if (multipartFile == null) {
         throw new ApplicationException("请选择一个文件上传！");
      } else if (multipartFile.isEmpty()) {
         throw new ApplicationException("上传文件不能为空！");
      } else {
         String fileName = multipartFile.getOriginalFilename();
         String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
         if (!FileTypeEnum.FILE_TYPE_XLS.getName().equals(fileExtName) && !FileTypeEnum.FILE_TYPE_XLSX.getName().equals(fileExtName)) {
            throw new ApplicationException("仅支持上传excel文件！");
         }
      }
   }

   public void validExistData(MultipartFile multipartFile) {
      log.info("validExistData==>准备校验文件中是否有数据");
      InputStream inputStream = null;
      AtomicBoolean existData = new AtomicBoolean(false);

      try {
         inputStream = multipartFile.getInputStream();
         ((ExcelReaderSheetBuilder) EasyExcel.read(inputStream, ExportOrgTemplateDTO.class, new ValidExceLegalListener(existData)).sheet().headRowNumber(2)).doRead();
         if (!existData.get()) {
            throw new ApplicationException("文件中数据为空！");
         } else {
            log.info("validExistData==>文件有数据");
         }
      } catch (IOException var5) {
         log.error("validFiles==》multipartFile.getInputStream异常:{}", var5.getMessage());
         throw new ApplicationException("获取文件流时异常！" + var5.getMessage());
      }
   }

   public CatalogueImportFileResponse uploadAndSaveFiles(MultipartFile multipartFile, BaseRequest request) {
      CatalogueImportFileResponse fileResponse = new CatalogueImportFileResponse();

      try {
         CatalogueFileOperationLogDO logDO = (CatalogueFileOperationLogDO)request.clone(CatalogueFileOperationLogDO.class);
         String fileName = multipartFile.getOriginalFilename();
         String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
         log.info("uploadAndSaveFiles====>fileName=={};fileExtName={}", fileName, fileExtName);
         InputStream inputStream = multipartFile.getInputStream();
         StorePath storePath = this.fastFileStorageClient.uploadFile(inputStream, multipartFile.getSize(), fileExtName, (Set)null);
         String fullPath = storePath.getFullPath();
         String path_1 = storePath.getPath();
         String group = storePath.getGroup();
         log.info("fullPath=={};path_1=={}；group=={}", new Object[]{fullPath, path_1, group});
         CatalogueFileImportRequest importRequest = (CatalogueFileImportRequest)request;
         logDO.setOperationFrom(FileImportFromEnum.findEnumByCode(importRequest.getImportPosition()).getNo());
         logDO.setOperationType(FileOperateTypeEnum.OPERATE_TYPE_IMPORT.getNo());
         logDO.setFileUrl(fullPath);
         logDO.setFileName(fileName);
         logDO.setFileAlias(fileName);
         logDO.setFileStatus(3);
         logDO.setFileSuffix(fileExtName);
         log.info("uploadAndSaveFiles====logDO:{}", logDO);
         this.catalogueFileOperationLogMapper.insert(logDO);
         log.info("文件id={}", logDO.getId());
         fileResponse.setFileId(logDO.getId());
         return fileResponse;
      } catch (Exception var13) {
         log.error("uploadAndSaveFiles===文件上传和文件路径入库异常:{}", var13.getMessage());
         throw new ApplicationException("文件上传异常:" + var13.getMessage());
      }
   }
}
