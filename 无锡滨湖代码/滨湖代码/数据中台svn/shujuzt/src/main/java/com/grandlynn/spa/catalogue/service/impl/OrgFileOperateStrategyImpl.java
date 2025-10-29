package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.enums.NodeLeafEnum;
import com.grandlynn.spa.catalogue.common.utils.EasyExcel;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.dto.ExportOrgTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.excel.listener.OrgExcelListener;
import com.grandlynn.spa.catalogue.service.*;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

@Component("orgFileOperateStrategy")
public class OrgFileOperateStrategyImpl implements FileOperateStrategy {
   private static final Logger log = LoggerFactory.getLogger(OrgFileOperateStrategyImpl.class);
   @Resource
   private CatalogueOrgService catalogueOrgService;
   @Resource
   private CatalogueFileOperationLogService fileOperationLogService;
   @Autowired
   private FastFileStorageClient fastFileStorageClient;
   @Resource
   private CatalogueOrgTypeService catalogueOrgTypeService;
   @Resource
   private ReportCatalogueService reportCatalogueService;

   public void importExcel(ExcelFileIdMessageDTO messageDto) {
      CatalogueFileOperationLogDO logDO = new CatalogueFileOperationLogDO();
      logDO.setFileStatus(4);
      logDO.setId(messageDto.getFileId());

      try {
         log.info("importExcel==>开始查询文件数据，入参：{}", messageDto);
         QueryWrapper<CatalogueFileOperationLogDO> queryWrapper = new QueryWrapper();
         queryWrapper.lambda().eq(BaseEntity::getTenantId, messageDto.getTenantId()).eq(messageDto.getAppId() != null, BaseEntity::getAppId, messageDto.getAppId()).eq(BaseEntity::getId, messageDto.getFileId()).eq(CatalogueFileOperationLogDO::getOperationFrom, messageDto.getOperateFrom());
         CatalogueFileOperationLogDO fileOperationLogDO = (CatalogueFileOperationLogDO)this.fileOperationLogService.getOne(queryWrapper);
         if (fileOperationLogDO != null) {
            log.info("importExcel==>查询到文件数据,开始获取文件流");
            String fullPath = fileOperationLogDO.getFileUrl();
            String group = fullPath.substring(0, fullPath.indexOf("/"));
            String path = fullPath.substring(fullPath.indexOf("/") + 1);
            DownloadByteArray downloadByteArray = new DownloadByteArray();
            byte[] bytes = (byte[])this.fastFileStorageClient.downloadFile(group, path, downloadByteArray);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            log.info("importExcel==>获取到文件流,开始解析文件");
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setTenantId(messageDto.getTenantId());
            baseRequest.setAppId(messageDto.getAppId());
            ((ExcelReaderSheetBuilder) EasyExcel.read(inputStream, ExportOrgTemplateDTO.class, new OrgExcelListener(this.fastFileStorageClient, this.catalogueOrgService, baseRequest, logDO, this.reportCatalogueService)).sheet().headRowNumber(2)).doRead();
            log.info("importExcel==>文件解析完毕,开始更新文件操作状态");
         }
      } catch (Exception var15) {
         log.error("文件解析失败：{}", var15.getMessage());
         logDO.setOperationStatus(2);
         logDO.setOperationMsg(var15.getMessage());
      } finally {
         this.fileOperationLogService.updateById(logDO);
      }

   }

   public void exportExcel(HttpServletResponse response, CatalogueFileExportRequest request) {
      try {
         byte[] bytes = FileUtil.readResourceFile(FileTemplateEnum.ORG_EXPORT_TEMPLATE);
         InputStream inputStream = new ByteArrayInputStream(bytes);
         log.info("downloadTemplate下载ok,开始导出组织excel===");
         String fileName = "组织导出数据" + System.currentTimeMillis() + ".xlsx";
         response.setContentType("application/vnd.ms-excel");
         response.setCharacterEncoding("UTF-8");
         String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
         response.setHeader("Content-disposition", "attachment;filename=" + finalName);
         EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).sheet().doFill(this.constructExportData(request));
      } catch (Exception var7) {
         log.error("导出文件异常{}", var7.getMessage());
         throw new ApplicationException("导出文件时异常错误！");
      }
   }

   public void exportWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      log.error("OrgFileOperateStrategyImpl中不实现exportWord方法");
   }

   public String exportAllWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      return "";
   }

   public void exportZip(HttpServletResponse response, CatalogueFileExportRequest request) {
   }

   private List<ExportOrgTemplateDTO> constructExportData(BaseRequest request) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .orderByAsc(CatalogueOrgDO::getNodeCode);
      List<CatalogueOrgDO> list = this.catalogueOrgService.list(queryWrapper);
      Iterator var4 = list.iterator();

      while(var4.hasNext()) {
         CatalogueOrgDO catalogueOrgDO = (CatalogueOrgDO)var4.next();
         NodeLeafEnum leafEnum = NodeLeafEnum.getNameByCode(Integer.parseInt(catalogueOrgDO.getLeaf()));
         if (leafEnum != null) {
            catalogueOrgDO.setLeaf(leafEnum.getName());
         }

         if (catalogueOrgDO.getOrgTypeId() != null) {
            catalogueOrgDO.setOrgTypeName(this.catalogueOrgTypeService.getTypeNameById(catalogueOrgDO.getOrgTypeId()));
         }
      }

      return ObjectCloneUtils.convertList(list, ExportOrgTemplateDTO.class);
   }

}
