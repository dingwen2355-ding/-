package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.utils.EasyExcel;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.dto.ExportDatasourceTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDO;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.excel.listener.DataSourceExcelListener;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceDatabaseService;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceService;
import com.grandlynn.spa.catalogue.service.CatalogueFileOperationLogService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.FileOperateStrategy;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.SerializedLambda;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("dataSourceFileOperateStrategy")
public class DataSourceFileOperateStrategyImpl implements FileOperateStrategy {
   private static final Logger log = LoggerFactory.getLogger(DataSourceFileOperateStrategyImpl.class);
   @Resource
   private CatalogueDataSourceService dataSourceService;
   @Resource
   private CatalogueDataSourceDatabaseService dataSourceDatabaseService;
   @Resource
   private CatalogueOrgService orgService;
   @Resource
   private CatalogueOrgSysService orgSysService;
   @Resource
   private CatalogueFileOperationLogService fileOperationLogService;
   @Resource
   private FastFileStorageClient fastFileStorageClient;

   public void importExcel(ExcelFileIdMessageDTO messageDto) {
      CatalogueFileOperationLogDO logDO = new CatalogueFileOperationLogDO();
      logDO.setFileStatus(4);
      logDO.setId(messageDto.getFileId());

      try {
         log.info("importExcel==>开始查询文件数据，入参：{}", messageDto);
         CatalogueFileOperationLogDO fileOperationLogDO = this.getFileOperationLogDO(messageDto);
         if (fileOperationLogDO == null) {
            log.warn("importExcel==>未查询到文件数据，入参：{}", messageDto);
            return;
         }

         log.info("importExcel==>查询到文件数据,开始获取文件流");
         String fullPath = fileOperationLogDO.getFileUrl();
         String group = fullPath.substring(0, fullPath.indexOf("/"));
         String path = fullPath.substring(fullPath.indexOf("/") + 1);
         DownloadByteArray downloadByteArray = new DownloadByteArray();
         byte[] bytes = (byte[])this.fastFileStorageClient.downloadFile(group, path, downloadByteArray);
         InputStream inputStream = new ByteArrayInputStream(bytes);
         log.info("importExcel==>获取到文件流,开始解析文件");
         BaseRequest baseRequest = (BaseRequest)messageDto.clone(BaseRequest.class);
         DataSourceExcelListener listener = new DataSourceExcelListener(this.fastFileStorageClient, this.dataSourceService, this.dataSourceDatabaseService, this.orgService, this.orgSysService, logDO, baseRequest);
         ((ExcelReaderSheetBuilder) EasyExcel.read(inputStream, ExportDatasourceTemplateDTO.class, listener).sheet().headRowNumber(1)).doRead();
         log.info("importExcel==>文件解析完毕,开始更新文件操作状态");
      } catch (Exception var15) {
         var15.printStackTrace();
         log.error("文件解析失败：{}", var15.getMessage());
         logDO.setOperationStatus(2);
         logDO.setOperationMsg(var15.getMessage());
      } finally {
         this.fileOperationLogService.updateById(logDO);
      }

   }

   private CatalogueFileOperationLogDO getFileOperationLogDO(ExcelFileIdMessageDTO messageDto) {
      QueryWrapper<CatalogueFileOperationLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, messageDto.getTenantId()).eq(messageDto.getAppId() != null, BaseEntity::getAppId, messageDto.getAppId()).eq(BaseEntity::getId, messageDto.getFileId()).eq(CatalogueFileOperationLogDO::getOperationFrom, messageDto.getOperateFrom());
      CatalogueFileOperationLogDO fileOperationLogDO = (CatalogueFileOperationLogDO)this.fileOperationLogService.getOne(queryWrapper);
      return fileOperationLogDO;
   }

   public void exportExcel(HttpServletResponse response, CatalogueFileExportRequest request) {
      try {
         byte[] bytes = FileUtil.readResourceFile(FileTemplateEnum.ORG_EXPORT_TEMPLATE);
         InputStream inputStream = new ByteArrayInputStream(bytes);
         log.info("downloadTemplate下载ok,开始导出数源excel===");
         String fileName = "数据源管理导出数据" + System.currentTimeMillis() + ".xlsx";
         response.setContentType("application/vnd.ms-excel");
         response.setCharacterEncoding("UTF-8");
         String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
         response.setHeader("Content-disposition", "attachment;filename=" + finalName);
         EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).sheet().doFill(this.constructExportData(request));
      } catch (IOException var7) {
         log.error("导出文件异常{}", var7.getMessage());
         throw new ApplicationException("导出文件时异常错误！");
      }
   }

   public void exportWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      log.error("DataSourceFileOperateStrategyImpl中不实现exportWord方法");
   }

   public String exportAllWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      return "";
   }

   public void exportZip(HttpServletResponse response, CatalogueFileExportRequest request) {
   }

   private List<ExportDatasourceTemplateDTO> constructExportData(BaseRequest request) {
      LambdaQueryWrapper<CatalogueDataSourceDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      queryWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      queryWrapper.orderByAsc(BaseEntity::getId);
      List<CatalogueDataSourceDO> list = this.dataSourceService.list(queryWrapper);
      List<ExportDatasourceTemplateDTO> result = ObjectCloneUtils.convertList(list, ExportDatasourceTemplateDTO.class);
      return result;
   }

}
