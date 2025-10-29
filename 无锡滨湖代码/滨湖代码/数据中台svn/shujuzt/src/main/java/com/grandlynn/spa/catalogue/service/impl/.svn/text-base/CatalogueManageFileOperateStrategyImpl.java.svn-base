package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.OpenTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.ShareStatusEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.UpdatePeriodEnum;
import com.grandlynn.spa.catalogue.common.utils.ConvertDataFromEnumUtil;
import com.grandlynn.spa.catalogue.common.utils.ConvertWordDataUtil;
import com.grandlynn.spa.catalogue.common.utils.EasyExcel;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.common.utils.WordsUtils;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgGetDetailRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueListRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSysByOrgIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgDetailResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.WordModelData;
import com.grandlynn.spa.catalogue.dto.WordModelFieldData;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.CatalogueVersionLogDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.excel.listener.CatalogueExcelListener;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceApplicationSceneMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceCatalogueMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceCategoryMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceDomainMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceIndustryCategoryMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceItemMapper;
import com.grandlynn.spa.catalogue.mapper.OrgDsServiceMapper;
import com.grandlynn.spa.catalogue.mapper.TableMatterMapper;
import com.grandlynn.spa.catalogue.mq.producer.SendOrgDsServiceProducer;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueFileOperationLogService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgContentRelationService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.spa.catalogue.service.FastFileService;
import com.grandlynn.spa.catalogue.service.FileOperateStrategy;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;


import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.DateUtils;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.SerializedLambda;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("catalogueManageFileOperateStrategy")
public class CatalogueManageFileOperateStrategyImpl implements FileOperateStrategy {
   private static final Logger log = LoggerFactory.getLogger(CatalogueManageFileOperateStrategyImpl.class);
   @Resource
   private CatalogueOrgService catalogueOrgService;
   @Resource
   private CatalogueOrgSysService catalogueOrgSysService;
   @Resource
   private CatalogueSysTableService catalogueSystableService;
   @Resource
   private CatalogueOrgContentRelationService catalogueOrgContentRelationService;
   @Resource
   private CatalogueTableFieldService catalogueTableFieldService;
   @Resource
   private CatalogueFileOperationLogService fileOperationLogService;
   @Autowired
   private FastFileStorageClient fastFileStorageClient;
   @Resource
   private FastFileService fastFileService;
   @Resource
   private CatalogueVersionLogService catalogueVersionLogService;
   @Autowired
   private OrgDsServiceMapper orgDsServiceMapper;
   @Resource
   private SendOrgDsServiceProducer sendOrgDsServiceProducer;
   @Resource
   private CatalogueResourceCategoryMapper resourceCategoryMapper;
   @Resource
   private CatalogueResourceItemMapper resourceItemMapper;
   @Resource
   private CatalogueResourceCatalogueMapper resourceCatalogueMapper;
   @Resource
   private CatalogueResourceDomainMapper resourceDomainMapper;
   @Resource
   private CatalogueResourceIndustryCategoryMapper resourceIndustryCategoryMapper;
   @Resource
   private CatalogueResourceApplicationSceneMapper resourceApplicationSceneMapper;
   @Resource
   private SysAdminUserService sysAdminUserService;
   @Resource
   private TableMatterMapper tableMatterMapper;

   public void importExcel(ExcelFileIdMessageDTO messageDto) {
      CatalogueFileOperationLogDO logDO = new CatalogueFileOperationLogDO();
      logDO.setFileStatus(4);
      logDO.setId(messageDto.getFileId());
      ExcelReader excelReader = null;

      try {
         log.info("catalogueManageFileOperateStrategy===>importExcel==>开始查询文件数据，入参：{}", messageDto);
         QueryWrapper<CatalogueFileOperationLogDO> queryWrapper = new QueryWrapper();
         queryWrapper.lambda().eq(BaseEntity::getTenantId, messageDto.getTenantId())
                 .eq(messageDto.getAppId() != null, BaseEntity::getAppId, messageDto.getAppId())
                 .eq(BaseEntity::getId, messageDto.getFileId())
                 .eq(CatalogueFileOperationLogDO::getOperationFrom, messageDto.getOperateFrom());
         CatalogueFileOperationLogDO fileOperationLogDO = (CatalogueFileOperationLogDO)this.fileOperationLogService.getOne(queryWrapper);
         if (fileOperationLogDO != null) {
            log.info("catalogueManageFileOperateStrategy===>importExcel==>查询到文件数据,开始获取文件流");
            String fullPath = fileOperationLogDO.getFileUrl();
            String group = fullPath.substring(0, fullPath.indexOf("/"));
            String path = fullPath.substring(fullPath.indexOf("/") + 1);
            DownloadByteArray downloadByteArray = new DownloadByteArray();
            log.info("====>fullPath:{};group:{};path:{}", new Object[]{fullPath, group, path});
            byte[] bytes = (byte[])this.fastFileStorageClient.downloadFile(group, path, downloadByteArray);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            log.info("importExcel==>获取到文件流bytes：{},开始解析文件", bytes.length > 0);
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setTenantId(messageDto.getTenantId());
            baseRequest.setAppId(messageDto.getAppId());
            excelReader = EasyExcel.read(inputStream).build();
            ReadSheet readSheet0 = EasyExcel.readSheet(4).head(ExportSysCatalogueTemplateDTO.class).registerReadListener(new CatalogueExcelListener(baseRequest, logDO, this.fastFileStorageClient, this.catalogueOrgService, this.catalogueOrgSysService, this.catalogueSystableService, this.catalogueTableFieldService, this.orgDsServiceMapper, messageDto.getCheckAdd(), this.sendOrgDsServiceProducer, this.resourceCategoryMapper, this.resourceItemMapper, this.resourceCatalogueMapper, this.resourceDomainMapper, this.resourceIndustryCategoryMapper, this.resourceApplicationSceneMapper, this.tableMatterMapper, messageDto.getUser())).headRowNumber(4).build();
            excelReader.read(new ReadSheet[]{readSheet0});
            log.info("catalogueManageFileOperateStrategy===>importExcel==>文件解析完毕,开始更新文件操作状态");
         }
      } catch (Exception var17) {
         log.error("catalogueManageFileOperateStrategy===>文件解析失败：{}", var17.getMessage());
         logDO.setOperationStatus(2);
         logDO.setOperationMsg(var17.getMessage());
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
         byte[] bytes = FileUtil.readResourceFile(FileTemplateEnum.CATALOGUE_MANAGE_EXPORT_TEMPLATE);
         InputStream inputStream = new ByteArrayInputStream(bytes);
         log.info("===目录编制==downloadTemplate下载ok,开始导出无锡市滨湖区公共数据资源excel===》");
         String fileName = "无锡市滨湖区公共数据资源资源目录-" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
         if (request.getOrgId() != null && request.getOrgSysId() != null) {
            CatalogueOrgGetDetailRequest orgTreeRequest = new CatalogueOrgGetDetailRequest();
            orgTreeRequest.setId(request.getOrgId());
            orgTreeRequest.setTenantId(SecurityUtil.currentUser().getTenantId());
            CatalogueOrgDetailResponse orgDetail = this.catalogueOrgService.getDetailById(orgTreeRequest, 1);
            CatalogueGetSysBySysIdRequest sysRequest = new CatalogueGetSysBySysIdRequest();
            sysRequest.setTenantId(SecurityUtil.currentUser().getTenantId());
            sysRequest.setId(request.getOrgSysId());
            CatalogueGetSysByOrgIdResponse sysDetail = this.catalogueOrgSysService.getSysDetailBySysId(sysRequest, 1);
            fileName = orgDetail.getNodeName() + sysDetail.getSysName() + "资源目录-" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
         }

         response.setContentType("application/vnd.ms-excel");
         response.setCharacterEncoding("UTF-8");
         String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
         response.setHeader("Content-disposition", "attachment;filename=" + finalName);
         String absolutePath = System.getProperty("user.dir");
         String tempFileName = absolutePath + File.separator + "template/excel" + File.separator + fileName;
         log.info("临时文件路径,tempFilePath={}", tempFileName);
         File tempFile = FileUtil.createTempNewFile(tempFileName);
         log.info("准备创建临时文件===》tempFileName={}", tempFileName);
         new ArrayList();
         log.info("创建临时文件完成===》开始填充数据");
         if (SecurityUtil.currentUser().getOrgLimit()) {
            request.setOrgIds(new Long[]{SecurityUtil.currentUser().getOrgids()[0]});
         }

         List<ExportSysCatalogueTemplateDTO> exportSysCatalogueTemplateDTOS = this.constructExportSysCatalogueData(CollectionUtil.isNotEmpty(Arrays.asList(request.getOrgIds())) ? Arrays.asList(request.getOrgIds()) : null, IntermediateEnum.INTERMEDIATE.getCode());
         ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(inputStream).build();
         WriteSheet writeSheet1 = EasyExcel.writerSheet(4).build();
         excelWriter.fill(new FillWrapper(exportSysCatalogueTemplateDTOS), writeSheet1);
         excelWriter.finish();
         log.info("临时文件写入ok!---开始写入response===>");
         FileUtil.readFile(response, tempFileName);
         log.info("写入response完毕===>");
      } catch (IOException var14) {
         log.error("导出文件异常{}", var14.getMessage());
         throw new ApplicationException("导出文件时异常错误！");
      }
   }

   public void exportWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      if (request.getExportAll() != null && request.getExportAll()) {
         log.info("导出word===>exportAll:{}", request.getExportAll());
         this.downloadWord(response, request);
         log.info("===>downloadWord ok！");
      } else if (request.getOrgIds() == null) {
         throw new ApplicationException("组织节点id数组不能为空");
      } else {
         try {
            byte[] bytes = FileUtil.readResourceFile(FileTemplateEnum.CATALOGUE_WORD_TEMPLATE);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            log.info("===downloadTemplate下载ok,开始把word模板文件写入到磁盘===>");
            String targetWordFilePath = FileUtil.getFileFullPathOnDisk(FileTemplateEnum.CATALOGUE_WORD_TEMPLATE.getName());
            FileUtil.createTempNewFile(targetWordFilePath);
            FileOutputStream os = new FileOutputStream(targetWordFilePath);
            FileUtil.writeFile(inputStream, os);
            log.info("word模板文件写入到磁盘:{}", targetWordFilePath);
            String fileName = "无锡市滨湖区大数据资源目录体系" + System.currentTimeMillis() + ".docx";
            response.setContentType("application/vnd.ms-word");
            response.setCharacterEncoding("UTF-8");
            String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename=" + finalName);
            String tempFileName = FileUtil.getFileFullPathOnDisk(fileName);
            log.info("临时文件路径,tempFilePath={}", tempFileName);
            FileUtil.createTempNewFile(tempFileName);
            log.info("准备创建临时文件===》tempFileName={}", tempFileName);
            File templatePathFile = new File(targetWordFilePath);
            log.info("输出的word文件临时保存位置:{}", tempFileName);
            FileUtil.createTempNewFile(tempFileName);
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            WordsUtils.insertTableOfContents(builder);
            builder.getParagraphFormat().setAlignment(0);
            this.queryAndFillWord(builder, templatePathFile, request);
            doc.updateFields();
            WordsUtils.insertWatermarkText(doc, "内部传阅");
            WordsUtils.setHeaderAndFooter(builder);
            doc.save(tempFileName);
            log.info("word文档加载完毕!---开始写入response===>");
            FileUtil.readFile(response, tempFileName);
            log.info("写入response完毕===>");
         } catch (Exception var13) {
            log.error("导出word文件异常{}", var13.getMessage());
            throw new ApplicationException("导出word文件时异常");
         }
      }
   }

   private synchronized void downloadWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      String versionNo = this.catalogueVersionLogService.getMaxVersionNo();
      CatalogueVersionLogDO versionLogDO = this.catalogueVersionLogService.findVersionLogByVersionNo(request, versionNo);
      if (null == versionLogDO) {
         throw new ApplicationException(ResultCodeEnum.WITHOUT_VERSION_NO_DATA);
      } else {
         String versionUrl = versionLogDO.getVersionUrl();
         if (StringUtils.isBlank(versionUrl)) {
            log.info("开始查询上一天的url");
            List<CatalogueVersionLogDO> allVersionLogs = this.catalogueVersionLogService.findAllVersionLogs(request);
            Iterator var7 = allVersionLogs.iterator();

            while(var7.hasNext()) {
               CatalogueVersionLogDO logDO = (CatalogueVersionLogDO)var7.next();
               if (StringUtils.isNotBlank(logDO.getVersionUrl())) {
                  versionUrl = logDO.getVersionUrl();
                  break;
               }
            }
         }

         if (StringUtils.isBlank(versionUrl)) {
            throw new ApplicationException(ResultCodeEnum.WITHOUT_WORD_DATA);
         } else {
            byte[] bytes = this.fastFileService.downloadFileFromDfs(versionUrl);

            try {
               OutputStream outputStream = response.getOutputStream();
               response.setCharacterEncoding("UTF-8");
               response.setContentType("application/vnd.ms-word");
               String fileName = "无锡市滨湖区大数据资源目录体系" + request.getVersionNo() + ".docx";
               String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
               response.setHeader("Content-Disposition", "attachment;filename=" + finalName);
               outputStream = response.getOutputStream();
               outputStream.write(bytes);
               outputStream.flush();
               log.info("testFastDfsDownload文件下载==>ok");
            } catch (IOException var10) {
               log.error("字节流写入response.getOutputStream()出错：{}", var10.getMessage());
            }

         }
      }
   }

   public String exportAllWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      log.info("编目管理exportAllWord（）=====>do nothing");
      return "";
   }

   public void exportZip(HttpServletResponse response, CatalogueFileExportRequest request) {
      try {
         byte[] bytes = FileUtil.readResourceFile(FileTemplateEnum.CATALOGUE_MANAGE_EXPORT_TEMPLATE);
         InputStream inputStream = new ByteArrayInputStream(bytes);
         log.info("===目录编制==downloadTemplate下载ok,开始导出无锡市滨湖区公共数据资源excel===》");
         String fileName = "无锡市滨湖区公共数据资源资源目录-" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
         if (request.getOrgId() != null && request.getOrgSysId() != null) {
            CatalogueOrgGetDetailRequest orgTreeRequest = new CatalogueOrgGetDetailRequest();
            orgTreeRequest.setId(request.getOrgId());
            orgTreeRequest.setTenantId(SecurityUtil.currentUser().getTenantId());
            CatalogueOrgDetailResponse orgDetail = this.catalogueOrgService.getDetailById(orgTreeRequest, 1);
            CatalogueGetSysBySysIdRequest sysRequest = new CatalogueGetSysBySysIdRequest();
            sysRequest.setTenantId(SecurityUtil.currentUser().getTenantId());
            sysRequest.setId(request.getOrgSysId());
            CatalogueGetSysByOrgIdResponse sysDetail = this.catalogueOrgSysService.getSysDetailBySysId(sysRequest, 1);
            fileName = orgDetail.getNodeName() + sysDetail.getSysName() + "资源目录-" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
         }

         String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
         String absolutePath = System.getProperty("user.dir");
         String tempFileName = absolutePath + File.separator + "template/excel" + File.separator + fileName;
         log.info("临时文件路径,tempFilePath={}", tempFileName);
         File tempFile = FileUtil.createTempNewFile(tempFileName);
         log.info("准备创建临时文件===》tempFileName={}", tempFileName);
         new ArrayList();
         log.info("创建临时文件完成===》开始填充数据");
         if (SecurityUtil.currentUser().getOrgLimit()) {
            request.setOrgIds(new Long[]{SecurityUtil.currentUser().getOrgids()[0]});
         }

         List<ExportSysCatalogueTemplateDTO> exportSysCatalogueTemplateDTOS = this.constructExportSysCatalogueData(CollectionUtil.isNotEmpty(Arrays.asList(request.getOrgIds())) ? Arrays.asList(request.getOrgIds()) : null, IntermediateEnum.INTERMEDIATE.getCode());
         ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(inputStream).build();
         WriteSheet writeSheet1 = EasyExcel.writerSheet(4).build();
         excelWriter.fill(new FillWrapper(exportSysCatalogueTemplateDTOS), writeSheet1);
         excelWriter.finish();
         log.info("临时文件写入ok!---开始写入response===>");
         log.info("写入response完毕===>");
         log.info("名字{},{}", fileName, tempFileName);
         response.setContentType("application/octet-stream");
         response.setHeader("Content-Disposition", "attachment; filename=" + finalName.replace("xlsx", "zip"));
         ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
         Throwable var15 = null;

         try {
            ZipEntry excelEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(excelEntry);
            FileInputStream fileIn = new FileInputStream(tempFileName);
            Throwable var18 = null;

            try {
               byte[] buffer = new byte[1024];

               int len;
               while((len = fileIn.read(buffer)) > 0) {
                  zipOut.write(buffer, 0, len);
               }
            } catch (Throwable var44) {
               var18 = var44;
               throw var44;
            } finally {
               if (fileIn != null) {
                  if (var18 != null) {
                     try {
                        fileIn.close();
                     } catch (Throwable var43) {
                        var18.addSuppressed(var43);
                     }
                  } else {
                     fileIn.close();
                  }
               }

            }

            zipOut.closeEntry();
            zipOut.close();
         } catch (Throwable var46) {
            var15 = var46;
            throw var46;
         } finally {
            if (zipOut != null) {
               if (var15 != null) {
                  try {
                     zipOut.close();
                  } catch (Throwable var42) {
                     var15.addSuppressed(var42);
                  }
               } else {
                  zipOut.close();
               }
            }

         }

         FileUtil.readFile(response, tempFileName);
         System.out.println("ZIP file has been exported successfully!");
      } catch (IOException var48) {
         log.error("导出文件异常{}", var48.getMessage());
         throw new ApplicationException("导出文件时异常错误！");
      }
   }

   private void queryAndFillWord(DocumentBuilder builder, File templatePathFile, CatalogueFileExportRequest request) {
      CatalogueGetSystemsByOrgIdsRequest systemsByOrgIdsRequest = (CatalogueGetSystemsByOrgIdsRequest)request.clone(CatalogueGetSystemsByOrgIdsRequest.class);
      List<SystemsDTO> systemsDTOS = this.catalogueOrgSysService.getSystemsByOrgIds(systemsByOrgIdsRequest, IntermediateEnum.INTERMEDIATE.getCode());
      log.info("=====编目管理导出word==queryAndFillWord==>systemsDTOS:{}", systemsDTOS);
      Map<Long, String> orgMap = new HashMap();
      Map<Long, String> sysMap = new HashMap();
      Iterator var8 = systemsDTOS.iterator();

      Long sysId;
      while(var8.hasNext()) {
         SystemsDTO systemsDTO = (SystemsDTO)var8.next();
         Long orgId = systemsDTO.getOrgId();
         String nodeName = systemsDTO.getNodeName();
         sysId = systemsDTO.getSysId();
         String sysName = systemsDTO.getSysName();
         if (orgMap.get(orgId) == null) {
            orgMap.put(orgId, nodeName);
         }

         if (sysMap.get(sysId) == null) {
            sysMap.put(sysId, sysName);
         }
      }

      int orgIndex = 0;
      Map<Long, List<SystemsDTO>> map = (Map)systemsDTOS.stream().collect(Collectors.groupingBy(SystemsDTO::getOrgId));
      WordsUtils.fillTitle(builder, "第二章 结构化数据资源目录");
      Iterator var18 = map.entrySet().iterator();

      while(var18.hasNext()) {
         Map.Entry<Long, List<SystemsDTO>> entry = (Map.Entry)var18.next();
         ++orgIndex;
         sysId = (Long)entry.getKey();
         List<SystemsDTO> tempSystems = (List)entry.getValue();
         String nodeName = (String)orgMap.get(sysId);
         log.info("=====>tempOrgId:{};===tempSystems:{}", sysId, tempSystems);
         String orgPre = WordsUtils.fillOrg(builder, orgMap, sysId, orgIndex, nodeName);
         this.queryTableAndFields(builder, templatePathFile, tempSystems, request, orgIndex, sysId, orgMap, sysMap, orgPre);
      }

   }

   private void queryTableAndFields(DocumentBuilder builder, File templatePathFile, List<SystemsDTO> tempSystems, CatalogueFileExportRequest request, int orgIndex, Long tempOrgId, Map<Long, String> orgMap, Map<Long, String> sysMap, String orgPre) {
      int sysIndex = 0;
      Iterator var11 = tempSystems.iterator();

      while(var11.hasNext()) {
         SystemsDTO tempSystem = (SystemsDTO)var11.next();
         Long sysId = tempSystem.getSysId();
         log.info("======>queryTableAndFields==>处理org标题==orgIndex:{};tempOrgId:{};sysId:{};", new Object[]{orgIndex, tempOrgId, sysId});
         ++sysIndex;
         String sysPre = null;
         if (sysId != null) {
            sysPre = WordsUtils.fillSys(builder, sysMap, sysId, sysIndex, orgPre);
         }

         CatalogueGetTablesAndFieldsBySysIdRequest tableBySysIdsRequest = (CatalogueGetTablesAndFieldsBySysIdRequest)request.clone(CatalogueGetTablesAndFieldsBySysIdRequest.class);
         tableBySysIdsRequest.setSysId(sysId);
         List<TablesAndFieldsDTO> result = this.catalogueOrgSysService.getTablesAndFieldsBySysId(tableBySysIdsRequest);
         if (result != null && sysId != null) {
            try {
               List<WordModelData> tempWordModelDataList = this.parseWordDataFromMap(result, tempOrgId, orgMap, sysMap, sysId);
               WordsUtils.fillAndInsertDoc(builder, templatePathFile, tempWordModelDataList, sysPre);
            } catch (IOException var18) {
               var18.printStackTrace();
            }
         }
      }

   }

   private List<WordModelData> parseWordDataFromMap(List<TablesAndFieldsDTO> result, Long tempOrgId, Map<Long, String> orgMap, Map<Long, String> sysMap, Long sysId) {
      Map<Long, List<TablesAndFieldsDTO>> tableIdGroupMap = (Map)result.stream().collect(Collectors.groupingBy(TablesAndFieldsDTO::getTableId));
      if (tableIdGroupMap != null) {
         List<WordModelData> wordList = new ArrayList();

         WordModelData wordModelData;
         for(Iterator var8 = tableIdGroupMap.entrySet().iterator(); var8.hasNext(); wordList.add(wordModelData)) {
            Map.Entry<Long, List<TablesAndFieldsDTO>> tableEntry = (Map.Entry)var8.next();
            wordModelData = new WordModelData();
            wordModelData.setOrgId(tempOrgId);
            wordModelData.setOrgName((String)orgMap.get(tempOrgId));
            wordModelData.setSysName((String)sysMap.get(sysId));
            Long tempTableId = (Long)tableEntry.getKey();
            List<TablesAndFieldsDTO> fieldList = (List)tableEntry.getValue();
            log.info("====parseWordDataFromMap=>tempTableId:{};fieldList:{}", tempTableId, fieldList);
            if (fieldList != null) {
               log.info("开始构造wordModelData===》");
               wordModelData = this.constructWordModelData(wordModelData, fieldList);
            } else {
               log.info("开始初始化initWordModelData===》");
               ConvertWordDataUtil.initWordModelData(wordModelData);
               wordModelData.setFieldList(ConvertWordDataUtil.initWordFieldData());
            }
         }

         return wordList;
      } else {
         return Collections.emptyList();
      }
   }

   private WordModelData constructWordModelData(WordModelData wordModelData, List<TablesAndFieldsDTO> tempTableAndFields) {
      if (tempTableAndFields != null && !tempTableAndFields.isEmpty()) {
         int i = 0;
         TablesAndFieldsDTO tempFieldDto = (TablesAndFieldsDTO)tempTableAndFields.get(0);
         wordModelData = (WordModelData)tempFieldDto.clone(WordModelData.class);
         if (tempFieldDto.getAdministrationName() == null) {
            wordModelData.setAdministrationName("");
         }

         if (tempFieldDto.getTableRemark() == null) {
            wordModelData.setTableRemark("");
         }

         UpdatePeriodEnum updatePeriodEnum = UpdatePeriodEnum.getNameByCode(StringUtils.defaultString(wordModelData.getUpdatePeriod(), "0"));
         wordModelData.setUpdatePeriod(updatePeriodEnum == null ? "" : updatePeriodEnum.getName());
         ShareStatusEnum shareStatusEnum = ShareStatusEnum.getNameByCode(ConvertDataFromEnumUtil.parseType2Integer(wordModelData.getShareStatus()));
         wordModelData.setShareStatus(shareStatusEnum == null ? "" : shareStatusEnum.getName());
         OpenTypeEnum openTypeEnum = OpenTypeEnum.getNameByCode(ConvertDataFromEnumUtil.parseType2Integer(wordModelData.getOpenType()));
         wordModelData.setOpenType(openTypeEnum == null ? "" : openTypeEnum.getName());
         List<WordModelFieldData> fieldList = ObjectCloneUtils.convertList(tempTableAndFields, WordModelFieldData.class);
         Iterator var9 = fieldList.iterator();

         while(var9.hasNext()) {
            WordModelFieldData wordModelFieldData = (WordModelFieldData)var9.next();
            ++i;
            wordModelFieldData.setIndex(i);
         }

         wordModelData.setFieldList(fieldList);
      }

      return wordModelData;
   }

   private void constructExportSysIdsByOrgId(CatalogueFileExportRequest request, List<Long> sysIds, int intermediate) {
      SysAdminUser user = SecurityUtil.currentUser();
      this.orgLimit(request.getOrgId(), user);
      QueryWrapper<CatalogueOrgContentRelationDO> queryWrapper = new QueryWrapper();
     queryWrapper.lambda()
             .eq(BaseEntity::getTenantId, request.getTenantId())
             .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
             .eq(BaseEntity::getDeleted, 0)
             .eq(CatalogueOrgContentRelationDO::getContentType, 1)
             .in(request.getOrgIds() != null, CatalogueOrgContentRelationDO::getOrgId, request.getOrgIds());
      List<CatalogueOrgContentRelationDO> list = this.catalogueOrgContentRelationService.list(queryWrapper);
      Iterator var8 = list.iterator();

      while(var8.hasNext()) {
         CatalogueOrgContentRelationDO contentRelationDO = (CatalogueOrgContentRelationDO)var8.next();
         sysIds.add(contentRelationDO.getContentId());
      }

   }

   private Long[] orgLimit(Long orgId, SysAdminUser user) {
      if (null == orgId) {
         return user.getOrgLimit() ? user.getOrgids() : null;
      } else if (user.getOrgLimit()) {
         return Arrays.asList(user.getOrgids()).contains(orgId) ? this.sysAdminUserService.getOrgIdRecursive(orgId) : new Long[]{0L};
      } else {
         return this.sysAdminUserService.getOrgIdRecursive(orgId);
      }
   }

   private void constructExportSysIds(CatalogueFileExportRequest request, List<Long> sysIds, int intermediate) {
      List<String> nodeNos = new ArrayList();
      if (request.getOrgId() != null) {
         CatalogueOrgQueryOrgTreeRequest treeRequest = (CatalogueOrgQueryOrgTreeRequest)request.clone(CatalogueOrgQueryOrgTreeRequest.class);
         treeRequest.setId(request.getOrgId());
         CatalogueOrgQueryOrgTreeResponse treeResult = this.catalogueOrgService.queryOrgTree(treeRequest, intermediate, false);
         OrgNode orgNode = this.catalogueOrgService.getOrgNodeById(request, request.getOrgId(), intermediate);
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
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .in(!nodeNos.isEmpty(), CatalogueOrgSysDO::getNodeNo, nodeNos);
      List<CatalogueOrgSysDO> list = this.catalogueOrgSysService.list(queryWrapper);
      Iterator var11 = list.iterator();

      while(var11.hasNext()) {
         CatalogueOrgSysDO orgSysDO = (CatalogueOrgSysDO)var11.next();
         sysIds.add(orgSysDO.getId());
      }

   }

   private void getNodeNoFromTree(List<OrgNode> orgList, List<String> nodeNos) {
      if (orgList != null && !orgList.isEmpty()) {
         for(OrgNode orgNode :orgList) {
            nodeNos.add(orgNode.getNodeNo());
            List<OrgNode> tempOrgList = orgNode.getChildOrgList();
            this.getNodeNoFromTree(tempOrgList, nodeNos);
         }
      }
   }

   private List<ExportSysCatalogueTemplateDTO> constructExportSysCatalogueData(List<Long> orgIds, Integer intermediate) {
      log.info("===>orgIds={}", orgIds);
      FindCatalogueListRequest request = new FindCatalogueListRequest((List)null, intermediate, orgIds);
      List<ExportSysCatalogueTemplateDTO> list = this.catalogueTableFieldService.findCatalogueListBySysIds(request);
      int no = 1;

      for(Iterator var6 = list.iterator(); var6.hasNext(); ++no) {
         ExportSysCatalogueTemplateDTO exportSysCatalogueTemplateDTO = (ExportSysCatalogueTemplateDTO)var6.next();
         exportSysCatalogueTemplateDTO.setNo(no + "");
         ConvertDataFromEnumUtil.convert2NameFromCatalogueTemplate(exportSysCatalogueTemplateDTO);
      }

      return list;
   }

}
