package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.domain.request.CatalogueFileDownloadRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileImportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileOperationLogGetCurrentStatusRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileOperationLogGetLogRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileTemplateDownloadRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueFileOperationLogGetCurrentStatusResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueFileOperationLogGetLogResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueImportFileResponse;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueHelpDocument;
import com.grandlynn.spa.catalogue.mq.producer.SendExcelFileIdProducer;
import com.grandlynn.spa.catalogue.service.CatalogueFileOperationLogService;
import com.grandlynn.spa.catalogue.service.CatalogueHelpDocumentService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.spa.catalogue.service.SysTemplateService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(
   tags = {"文件操作模块包含文件上传和下载"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/file/operationLog"})
public class FileOperationLogController {
   private static final Logger log = LoggerFactory.getLogger(FileOperationLogController.class);
   @Autowired
   private CatalogueFileOperationLogService catalogueFileOperationLogService;
   @Autowired
   private FastFileStorageClient fastFileStorageClient;
   @Autowired
   private SendExcelFileIdProducer producer;
   @Autowired
   private SysTemplateService sysTemplateService;
   @Autowired
   private CatalogueVersionLogService catalogueVersionLogService;
   @Resource
   private CatalogueOrgVersionService catalogueOrgVersionService;
   @Autowired
   private CatalogueHelpDocumentService catalogueHelpDocumentService;

   @ApiOperation(
      value = "excel导入模板下载",
      nickname = "downloadTemplate"
   )
   @PostMapping({"downloadTemplate"})
   public void downloadTemplate(HttpServletResponse response, @RequestBody @Valid CatalogueFileTemplateDownloadRequest request) throws IOException {
      this.catalogueFileOperationLogService.downloadTemplate(response, request);
   }

   @ApiOperation(
      value = "系统模板上传",
      nickname = "uploadTemplate"
   )
   @PostMapping({"uploadTemplate"})
   public void uploadTemplate(@RequestParam("file") MultipartFile multipartFile, @RequestParam String code) {
      this.sysTemplateService.uploadFile(code, multipartFile);
   }

   @ApiOperation(
      value = "导入文件（根据标志导入对应的文件，包含对应的文件处理）",
      nickname = "uploadFiles"
   )
   @PostMapping({"uploadFiles"})
   public Payload<CatalogueImportFileResponse> uploadFiles(@RequestParam("file") MultipartFile multipartFile, @RequestParam String importPosition, @RequestParam String tenantId, @RequestParam Long appId) {
      CatalogueFileImportRequest request = new CatalogueFileImportRequest();
      request.setImportPosition(importPosition);
      request.setTenantId(tenantId);
      request.setAppId(appId);
      return new Payload(this.catalogueFileOperationLogService.uploadAndValidFiles(multipartFile, request));
   }

   @ApiOperation(
      value = "导出文件（根据标志导出对应的文件）",
      nickname = "exportFile"
   )
   @PostMapping({"exportFile"})
   public void exportFile(HttpServletResponse response, @RequestBody @Valid CatalogueFileExportRequest request) {
      this.catalogueFileOperationLogService.exportFile(response, request);
   }

   @ApiOperation(
      value = "查询某个上传的文件当前操作状态",
      nickname = "getFileCurrentStatus"
   )
   @PostMapping({"getFileCurrentStatus"})
   public Payload<List<CatalogueFileOperationLogGetCurrentStatusResponse>> getFileCurrentStatus(@RequestBody @Valid CatalogueFileOperationLogGetCurrentStatusRequest request) {
      return new Payload(this.catalogueFileOperationLogService.getFileCurrentStatus(request));
   }

   @ApiOperation(
      value = "查看文件导入导出记录",
      nickname = "getFileOperationLog"
   )
   @PostMapping({"getFileOperationLog"})
   public Payload<PageBean<CatalogueFileOperationLogGetLogResponse>> getFileOperationLog(@RequestBody @Valid CatalogueFileOperationLogGetLogRequest request) {
      return new Payload(this.catalogueFileOperationLogService.getFileOperationLog(request));
   }

   @ApiOperation(
      value = "文件上传到dfs,并返回文件路径",
      nickname = "fastDfsUpload"
   )
   @PostMapping({"fastDfsUpload"})
   public Payload<String> fastDfsUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam String tenantId, @RequestParam Long appId) {
      String fullPath = null;

      try {
         String fileName = multipartFile.getOriginalFilename();
         log.info("测试文件上传===fileName==={}", fileName);
         String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
         log.info("测试文件上传==fileExtName==={}", fileExtName);
         InputStream inputStream = multipartFile.getInputStream();
         StorePath storePath = this.fastFileStorageClient.uploadFile(inputStream, multipartFile.getSize(), fileExtName, (Set)null);
         fullPath = storePath.getFullPath();
         String tempPath = storePath.getPath();
         String group = storePath.getGroup();
         log.info("fullPath=={};path_1=={}；group=={}", new Object[]{fullPath, tempPath, group});
      } catch (Exception var11) {
         log.error("测试文件上传===测试fdfs异常信息{}", var11.getMessage());
      }

      return new Payload(fullPath);
   }

   @ApiOperation(
      value = "根据dfs返回的文件路径下载文件",
      nickname = "downloadFileByPath"
   )
   @PostMapping({"downloadFileByPath"})
   public void downloadFileByPath(@RequestBody @Valid CatalogueFileDownloadRequest request, HttpServletResponse response) {
      try {
         String fileName = request.getFilePath().substring(request.getFilePath().lastIndexOf(File.separator) + 1);
         log.info("testFastDfsDownload文件下载====testFastDfsDownload==fileName=={}", fileName);
         DownloadByteArray downloadByteArray = new DownloadByteArray();
         byte[] bytes = (byte[])this.fastFileStorageClient.downloadFile(request.getFileGroup(), request.getFilePath(), downloadByteArray);
         response.setCharacterEncoding("UTF-8");
         response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(request.getFilePath(), "UTF-8"));
         OutputStream outputStream = response.getOutputStream();
         outputStream.write(bytes);
         outputStream.flush();
         log.info("testFastDfsDownload文件下载==>ok");
      } catch (Exception var7) {
         log.error("testFastDfsDownload文件下载====异常信息:{}", var7.getMessage());
      }

   }

   @ApiOperation(
      value = "测试mq消息",
      nickname = "testMqProducer"
   )
   @PostMapping({"testMqProducer"})
   public Payload<Boolean> testMqProducer(@RequestBody @Valid CatalogueFileImportRequest request) {
      ExcelFileIdMessageDTO dto = (ExcelFileIdMessageDTO)request.clone(ExcelFileIdMessageDTO.class);
      dto.setFileId(1L);
      log.info("controller===dto====dto={}", dto);
      this.producer.sendFileIdMessage(dto);
      return new Payload(Boolean.TRUE);
   }

   private CatalogueFileExportRequest initFileExportReq(String versionNo) {
      CatalogueFileExportRequest request = new CatalogueFileExportRequest();
      request.setTenantId("11");
      request.setAppId(11L);
      request.setFileType(1);
      request.setVersionNo(versionNo);
      request.setExportPosition("VERSION");
      request.setExportAll(true);
      return request;
   }

   @PostMapping({"/helpDocument/upload"})
   @ApiOperation(
      value = "帮助文档上传",
      nickname = "helpDocument"
   )
   private Payload uploadFile(@RequestParam("file") MultipartFile multipartFile) {
      String fullPath = null;

      try {
         String fileName = multipartFile.getOriginalFilename();
         log.info("帮助文档上传：", fileName);
         String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
         InputStream inputStream = multipartFile.getInputStream();
         StorePath storePath = this.fastFileStorageClient.uploadFile(inputStream, multipartFile.getSize(), fileExtName, (Set)null);
         fullPath = storePath.getFullPath();
         String tempPath = storePath.getPath();
         String group = storePath.getGroup();
         log.info("帮助文档上传", new Object[]{fullPath, tempPath, group});
         CatalogueHelpDocument catalogueHelpDocument = new CatalogueHelpDocument(fileName.substring(0, fileName.indexOf(".")), tempPath, group, System.currentTimeMillis());
         this.catalogueHelpDocumentService.insert(catalogueHelpDocument);
      } catch (Exception var10) {
         log.error("测试文件上传===测试fdfs异常信息{}", var10.getMessage());
      }

      return new Payload(fullPath);
   }

   @GetMapping({"/helpDocument/download"})
   @ApiOperation(
      value = "帮助文档下载",
      nickname = "helpDocument/download"
   )
   private void downloadFile(CatalogueHelpDocument catalogueHelpDocument, HttpServletResponse response) {
      OutputStream outputStream = null;

      try {
         log.info("帮助文档下载：", catalogueHelpDocument.getFileName());
         byte[] bytes = (byte[])this.fastFileStorageClient.downloadFile(catalogueHelpDocument.getFileGroup(), catalogueHelpDocument.getFilePath(), new DownloadByteArray());
         response.setCharacterEncoding("UTF-8");
         response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(catalogueHelpDocument.getFilePath(), "UTF-8"));
         outputStream = response.getOutputStream();
         outputStream.write(bytes);
         outputStream.flush();
         log.info("帮助文档下载完毕");
      } catch (Exception var13) {
         log.error("testFastDfsDownload文件下载====异常信息:{}", var13.getMessage());
      } finally {
         try {
            outputStream.close();
         } catch (IOException var12) {
            var12.printStackTrace();
         }

      }

   }

   @GetMapping({"/helpDocument/list"})
   @ApiOperation(
      value = "查询帮助文档列表",
      nickname = "helpDocument/list"
   )
   private Payload getList() {
      return new Payload(this.catalogueHelpDocumentService.getList());
   }
}
