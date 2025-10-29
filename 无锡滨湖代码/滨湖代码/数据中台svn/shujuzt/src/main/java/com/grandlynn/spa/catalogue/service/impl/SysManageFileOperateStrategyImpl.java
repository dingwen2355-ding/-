package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.utils.ConvertDataFromEnumUtil;
import com.grandlynn.spa.catalogue.common.utils.EasyExcel;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.dto.ExportSysTemplateDTO;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.excel.listener.SysExcelListener;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueFileOperationLogService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgContentRelationService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.FileOperateStrategy;
import com.grandlynn.spa.catalogue.service.ReportCatalogueService;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.SerializedLambda;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sysManageFileOperateStrategy")
public class SysManageFileOperateStrategyImpl implements FileOperateStrategy {
   private static final Logger log = LoggerFactory.getLogger(SysManageFileOperateStrategyImpl.class);
   @Resource
   private CatalogueOrgService catalogueOrgService;
   @Resource
   private CatalogueOrgSysService catalogueOrgSysService;
   @Resource
   private CatalogueOrgContentRelationService orgContentRelationService;
   @Resource
   private CatalogueFileOperationLogService fileOperationLogService;
   @Autowired
   private FastFileStorageClient fastFileStorageClient;
   @Resource
   private SysAdminUserService sysAdminUserService;
   @Resource
   private ReportCatalogueService reportCatalogueService;

   public void importExcel(ExcelFileIdMessageDTO messageDto) {
      CatalogueFileOperationLogDO logDO = new CatalogueFileOperationLogDO();
      logDO.setFileStatus(4);
      logDO.setId(messageDto.getFileId());
      ExcelReader excelReader = null;

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
            AtomicBoolean existWarnData = new AtomicBoolean(false);
            excelReader = EasyExcel.read(inputStream).build();
            ReadSheet readSheet0 = ((ExcelReaderSheetBuilder)((ExcelReaderSheetBuilder)((ExcelReaderSheetBuilder)EasyExcel.readSheet(0).head(ExportSysTemplateDTO.class)).registerReadListener(new SysExcelListener(baseRequest, logDO, existWarnData, this.fastFileStorageClient, this.catalogueOrgService, this.catalogueOrgSysService, this.orgContentRelationService, messageDto.getUser(), this.reportCatalogueService))).headRowNumber(2)).build();
            excelReader.read(new ReadSheet[]{readSheet0});
            log.info("importExcel==>文件解析完毕,开始更新文件操作状态");
         }
      } catch (Exception var18) {
         var18.printStackTrace();
         log.error("文件解析失败：{}", var18.getMessage());
         logDO.setOperationStatus(2);
         logDO.setOperationMsg(var18.getMessage());
      } finally {
         this.fileOperationLogService.updateById(logDO);
         if (excelReader != null) {
            excelReader.finish();
         }

         log.info("importExcel==>更新文件操作状态完毕!");
      }

   }

   public void exportExcel(HttpServletResponse response, CatalogueFileExportRequest request) {
      try {
         byte[] bytes = FileUtil.readResourceFile(FileTemplateEnum.SYS_MANAGE_EXPORT_TEMPLATE);
         InputStream inputStream = new ByteArrayInputStream(bytes);
         log.info("===downloadTemplate下载ok,开始导出系统管理系统数据excel===》");
         String fileName = "系统管理导出系统数据" + System.currentTimeMillis() + ".xlsx";
         response.setContentType("application/vnd.ms-excel");
         response.setCharacterEncoding("UTF-8");
         String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
         response.setHeader("Content-disposition", "attachment;filename=" + finalName);
         String absolutePath = System.getProperty("user.dir");
         String tempFileName = absolutePath + File.separator + "template/excel" + File.separator + fileName;
         log.info("临时文件路径,tempFilePath={}", tempFileName);
         File tempFile = FileUtil.createTempNewFile(tempFileName);
         log.info("准备创建临时文件===》tempFileName={}", tempFileName);
         List<Long> sysIds = new ArrayList();
         log.info("创建临时文件完成===》开始填充数据");
         ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(inputStream).build();
         WriteSheet writeSheet0 = EasyExcel.writerSheet(0).build();
         excelWriter.fill(new FillWrapper(this.constructExportSysData(request, sysIds, IntermediateEnum.INTERMEDIATE.getCode())), writeSheet0);
         excelWriter.finish();
         log.info("临时文件写入ok!---开始写入response===>");
         FileUtil.readFile(response, tempFileName);
         log.info("写入response完毕===>");
      } catch (IOException var13) {
         log.error("导出文件异常{}", var13.getMessage());
         throw new ApplicationException("导出文件时异常错误！");
      }
   }

   public void exportWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      log.error("SysManageFileOperateStrategyImpl中不实现exportWord方法");
   }

   public String exportAllWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      return "";
   }

   public void exportZip(HttpServletResponse response, CatalogueFileExportRequest request) {
   }

   private List<ExportSysTemplateDTO> constructExportSysData(CatalogueFileExportRequest request, List<Long> sysIds, int intermediate) {
      List<String> nodeNos = new ArrayList();
      Long orgId = null;
      SysAdminUser user = SecurityUtil.currentUser();
      if (null != user) {
         if (user.getOrgLimit()) {
            if (request.getOrgId() != null) {
               if (Arrays.asList(user.getOrgids()).contains(request.getOrgId())) {
                  orgId = request.getOrgId();
               }
            } else {
               orgId = this.sysAdminUserService.getUserOrgId(user.getId(), false)[0];
            }
         }

         if (orgId != null) {
            CatalogueOrgQueryOrgTreeRequest treeRequest = (CatalogueOrgQueryOrgTreeRequest)request.clone(CatalogueOrgQueryOrgTreeRequest.class);
            treeRequest.setId(orgId);
            CatalogueOrgQueryOrgTreeResponse treeResult = this.catalogueOrgService.queryOrgTree(treeRequest, intermediate, false);
            log.info("======>treeResult={}", treeResult);
            OrgNode orgNode = this.catalogueOrgService.getOrgNodeById(request, orgId, intermediate);
            if (treeResult != null && treeResult.getOrgList() != null && !treeResult.getOrgList().isEmpty()) {
               treeResult.getOrgList().add(orgNode);
            } else {
               treeResult = new CatalogueOrgQueryOrgTreeResponse();
               List<OrgNode> orgList = new ArrayList();
               orgList.add(orgNode);
               treeResult.setOrgList(orgList);
            }

            this.getNodeNoFromTree(treeResult.getOrgList(), nodeNos);
            log.info("====>递归获取nodeNos={}", nodeNos);
         }

         QueryWrapper<CatalogueOrgSysDO> queryWrapper = new QueryWrapper();
         queryWrapper.lambda()
                 .eq(BaseEntity::getTenantId, request.getTenantId())
                 .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
                 .in(!nodeNos.isEmpty(), CatalogueOrgSysDO::getNodeNo, nodeNos);
         List<CatalogueOrgSysDO> list = this.catalogueOrgSysService.list(queryWrapper);
         Iterator var13 = list.iterator();

         while(var13.hasNext()) {
            CatalogueOrgSysDO orgSysDO = (CatalogueOrgSysDO)var13.next();
            sysIds.add(orgSysDO.getId());
            ConvertDataFromEnumUtil.convert2NameFromSysDo(orgSysDO);
            log.info("==>上线时间=》upTime={};建成时间buildTime={}", orgSysDO.getUpTime(), orgSysDO.getBuildTime());
         }

         return ObjectCloneUtils.convertList(list, ExportSysTemplateDTO.class);
      } else {
         return null;
      }
   }

   private void getNodeNoFromTree(List<OrgNode> orgList, List<String> nodeNos) {
      if (orgList != null && !orgList.isEmpty()) {
         Iterator var4 = orgList.iterator();

         while(var4.hasNext()) {
            OrgNode orgNode = (OrgNode)var4.next();
            nodeNos.add(orgNode.getNodeNo());
            List<OrgNode> tempOrgList = orgNode.getChildOrgList();
            this.getNodeNoFromTree(tempOrgList, nodeNos);
         }

      }
   }

}
