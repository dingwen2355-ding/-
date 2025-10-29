package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.OpenTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.ShareStatusEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.UpdatePeriodEnum;
import com.grandlynn.spa.catalogue.common.utils.ConvertDataFromEnumUtil;
import com.grandlynn.spa.catalogue.common.utils.ConvertWordDataUtil;
import com.grandlynn.spa.catalogue.common.utils.DateUtil;
import com.grandlynn.spa.catalogue.common.utils.EasyExcel;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.common.utils.WordsUtils;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsAndVersionNo;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgVersionQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueVersionList;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.WordCoverModelData;
import com.grandlynn.spa.catalogue.dto.WordFirstModelData;
import com.grandlynn.spa.catalogue.dto.WordFirstModelOrgData;
import com.grandlynn.spa.catalogue.dto.WordModelData;
import com.grandlynn.spa.catalogue.dto.WordModelFieldData;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueVersionLogDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueFileOperationLogMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.spa.catalogue.service.FastFileService;
import com.grandlynn.spa.catalogue.service.FileOperateStrategy;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.SerializedLambda;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("versionFileOperateStrategy")
public class VersionFileOperateStrategyImpl implements FileOperateStrategy {
   private static final Logger log = LoggerFactory.getLogger(VersionFileOperateStrategyImpl.class);
   @Resource
   private CatalogueOrgVersionService catalogueOrgVersionService;
   @Resource
   private CatalogueOrgSysVersionService catalogueOrgSysVersionService;
   @Resource
   private CatalogueTableFieldVersionService catalogueTableFieldVersionService;
   @Resource
   private FastFileService fastFileService;
   @Resource
   private CatalogueFileOperationLogMapper fileOperationLogMapper;
   @Resource
   private CatalogueVersionLogService catalogueVersionLogService;
   @Resource
   private CatalogueSysTableVersionService catalogueSysTableVersionService;

   public void importExcel(ExcelFileIdMessageDTO messageDto) {
      log.error("VersionFileOperateStrategyImpl不实现importExcel方法");
   }

   public void exportExcel(HttpServletResponse response, CatalogueFileExportRequest request) {
      if (StringUtils.isBlank(request.getVersionNo())) {
         throw new ApplicationException("versionNo参数必传!");
      } else {
         try {
            byte[] bytes = this.fastFileService.downloadTemplate(FileTemplateEnum.CATALOGUE_MANAGE_EXPORT_TEMPLATE);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            log.info("===》从版本管理导出excel,downloadTemplate下载ok,开始导出无锡市滨湖区公共数据资源excel===》");
            String fileName = "无锡市滨湖区公共数据资源导出目录版本数据-" + System.currentTimeMillis() + ".xlsx";
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename=" + finalName);
            String absolutePath = System.getProperty("user.dir");
            String tempFileName = absolutePath + File.separator + "template/excel" + File.separator + fileName;
            log.info("从版本管理导出excel,临时文件路径,tempFilePath={}", tempFileName);
            File tempFile = FileUtil.createTempNewFile(tempFileName);
            log.info("从版本管理导出excel,准备创建临时文件===》tempFileName={}", tempFileName);
            List<Long> sysIds = new ArrayList();
            log.info("从版本管理导出excel,创建临时文件完成===》开始填充数据");
            ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(inputStream).build();
            WriteSheet writeSheet1 = EasyExcel.writerSheet(4).build();
            excelWriter.fill(new FillWrapper(this.constructExportSysCatalogueData(sysIds, request.getVersionNo())), writeSheet1);
            excelWriter.finish();
            log.info("临时文件写入ok!---开始写入response===>");
            FileUtil.readFile(response, tempFileName);
            log.info("写入response完毕===>");
         } catch (IOException var13) {
            log.error("导出文件异常{}", var13.getMessage());
            throw new ApplicationException("导出文件时异常错误！");
         }
      }
   }

   private void constructExportSysIds(CatalogueFileExportRequest request, List<Long> sysIds) {
      List<String> nodeNos = new ArrayList();
      if (request.getOrgId() != null) {
         CatalogueOrgVersionQueryOrgTreeRequest treeRequest = (CatalogueOrgVersionQueryOrgTreeRequest)request.clone(CatalogueOrgVersionQueryOrgTreeRequest.class);
         treeRequest.setId(request.getOrgId());
         CatalogueOrgQueryOrgTreeResponse treeResult = this.catalogueOrgVersionService.queryOrgVersionTree(treeRequest, request.getVersionNo());
         OrgNode orgNode = this.catalogueOrgVersionService.getOrgNodeById(request, request.getOrgId(), request.getVersionNo());
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

      QueryWrapper<CatalogueOrgSysVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .in(!nodeNos.isEmpty(), CatalogueOrgSysVersionDO::getNodeNo, nodeNos);
      List<CatalogueOrgSysVersionDO> list = this.catalogueOrgSysVersionService.list(queryWrapper);
      Iterator var10 = list.iterator();

      while(var10.hasNext()) {
         CatalogueOrgSysVersionDO orgSysVersionDO = (CatalogueOrgSysVersionDO)var10.next();
         sysIds.add(orgSysVersionDO.getOldId());
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

   private List<ExportSysCatalogueTemplateDTO> constructExportSysCatalogueData(List<Long> sysIds, String versionNo) {
      List<Long> orgIds = null;
      if (SecurityUtil.currentUser().getOrgLimit()) {
         orgIds = Arrays.asList(SecurityUtil.currentUser().getOrgids());
      }

      FindCatalogueVersionList request = new FindCatalogueVersionList(sysIds, versionNo, orgIds);
      List<ExportSysCatalogueTemplateDTO> list = this.catalogueTableFieldVersionService.findCatalogueListBySysIds(request);
      Iterator var6 = list.iterator();

      while(var6.hasNext()) {
         ExportSysCatalogueTemplateDTO exportSysCatalogueTemplateDTO = (ExportSysCatalogueTemplateDTO)var6.next();
         ConvertDataFromEnumUtil.convert2NameFromCatalogueTemplate(exportSysCatalogueTemplateDTO);
      }

      return list;
   }

   public String exportAllWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      if (request.getVersionNo() == null) {
         log.error("=====exportAllWord>====版本号参数不能为空");
         throw new ApplicationException("版本号参数不能为空");
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
            byte[] bytes1 = FileUtil.readResourceFile(FileTemplateEnum.CATALOGUE_WORD_FIRST_TEMPLATE);
            InputStream inputStream1 = new ByteArrayInputStream(bytes1);
            String firstWordFilePath = FileUtil.getFileFullPathOnDisk(FileTemplateEnum.CATALOGUE_WORD_FIRST_TEMPLATE.getName());
            FileUtil.createTempNewFile(firstWordFilePath);
            FileOutputStream os1 = new FileOutputStream(firstWordFilePath);
            FileUtil.writeFile(inputStream1, os1);
            log.info("第一章节word模板文件写入到磁盘:{}", firstWordFilePath);
            File firstTemplatePathFile = new File(firstWordFilePath);
            byte[] coverBytes = FileUtil.readResourceFile(FileTemplateEnum.CATALOGUE_WORD_COVER_TEMPLATE);
            InputStream coverInputStream = new ByteArrayInputStream(coverBytes);
            String coverWordFilePath = FileUtil.getFileFullPathOnDisk(FileTemplateEnum.CATALOGUE_WORD_COVER_TEMPLATE.getName());
            FileUtil.createTempNewFile(coverWordFilePath);
            FileOutputStream coverOs = new FileOutputStream(coverWordFilePath);
            FileUtil.writeFile(coverInputStream, coverOs);
            log.info("封皮word模板文件写入到磁盘:{}", coverWordFilePath);
            File coverTemplatePathFile = new File(coverWordFilePath);
            String fileName = "无锡市滨湖区政务信息资源目录" + System.currentTimeMillis() + ".docx";
            if (response != null) {
               response.setContentType("application/vnd.ms-word");
               response.setCharacterEncoding("UTF-8");
               String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
               response.setHeader("Content-disposition", "attachment;filename=" + finalName);
            }

            File templatePathFile = new File(targetWordFilePath);
            String tempSavePath = FileUtil.getFileFullPathOnDisk(fileName);
            log.info("输出的word文件临时保存位置:{}", tempSavePath);
            FileUtil.createTempNewFile(tempSavePath);
            Document destDoc = new Document();
            DocumentBuilder destBuilder = new DocumentBuilder(destDoc);
            WordCoverModelData coverModelData = this.constructCoverModel(request);
            Document coverDoc = WordsUtils.tempDoc((File)coverTemplatePathFile, coverModelData);
            destBuilder.insertDocument(coverDoc, 1);
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            WordsUtils.insertTableOfContents(builder);
            builder.getParagraphFormat().setAlignment(0);
            this.queryAndFillWord(builder, firstTemplatePathFile, templatePathFile, request);
            doc.updateFields();
            WordsUtils.setHeaderAndFooter(builder);
            WordsUtils.removeLastBlankTitle(doc);
            doc.getFirstSection().getPageSetup().setSectionStart(2);
            doc.getFirstSection().getPageSetup().setRestartPageNumbering(false);
            destDoc.appendDocument(doc, 1);
            destDoc.updateFields();
            destDoc.save(tempSavePath);
            log.info("word文档加载完毕！");
            if (response != null) {
               log.info("---开始写入response===>");
               FileUtil.readFile(response, tempSavePath);
               log.info("写入response完毕===>");
               return "";
            } else {
               String fullPath = this.fastFileService.uploadFileToDfs(tempSavePath);
               int resultSave = this.saveFileLogInfo(request.getVersionNo(), fileName, fullPath, request);
               log.info("====>saveFileLogInfo===resultSave:{}", resultSave);
               return fullPath;
            }
         } catch (Exception var28) {
            log.error("导出word文件异常{}", var28.getMessage());
            throw new ApplicationException("导出word文件时异常");
         }
      }
   }

   public void exportZip(HttpServletResponse response, CatalogueFileExportRequest request) {
   }

   public void exportWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      if (request.getVersionNo() == null) {
         throw new ApplicationException("版本号参数不能为空");
      } else {
         log.info("导出word======exportWord====>入参:request:{}", request);
         this.downloadWord(response, request);
         log.info("======>downloadWord ok！");
      }
   }

   private synchronized void downloadWord(HttpServletResponse response, CatalogueFileExportRequest request) {
      String versionNo = request.getVersionNo();
      CatalogueVersionLogDO versionLogDO = this.catalogueVersionLogService.findVersionLogByVersionNo(request, versionNo);
      log.info("=====》查询当前版本信息:{}", versionLogDO);
      if (versionLogDO == null) {
         throw new ApplicationException("查不到版本:{}的信息", request.getVersionNo());
      } else {
         String versionUrl = versionLogDO.getVersionUrl();
         if (StringUtils.isBlank(versionUrl)) {
            throw new ApplicationException(ResultCodeEnum.WITHOUT_WORD_DATA);
         } else {
            byte[] bytes = this.fastFileService.downloadFileFromDfs(versionUrl);
            OutputStream outputStream = null;

            try {
               response.setCharacterEncoding("UTF-8");
               response.setContentType("application/vnd.ms-word");
               String fileName = "无锡市滨湖区大数据资源目录体系" + request.getVersionNo() + ".docx";
               String finalName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
               response.setHeader("Content-Disposition", "attachment;filename=" + finalName);
               outputStream = response.getOutputStream();
               outputStream.write(bytes);
               outputStream.flush();
               log.info("====>downloadWord==>ok");
            } catch (IOException var18) {
               log.error("字节流写入response.getOutputStream()出错：{}", var18.getMessage());
            } finally {
               try {
                  if (outputStream != null) {
                     outputStream.close();
                  }
               } catch (IOException var17) {
                  log.error("outputStream关闭异常：{}", var17.getMessage());
               }

            }

         }
      }
   }

   private int saveFileLogInfo(String versionNo, String fileName, String fullPath, BaseRequest request) {
      CatalogueFileOperationLogDO logDO = (CatalogueFileOperationLogDO)request.clone(CatalogueFileOperationLogDO.class);
      logDO.setFileUrl(fullPath);
      logDO.setOperationType(1);
      logDO.setOperationFrom(10);
      logDO.setFileName(fileName);
      logDO.setFileAlias(fileName);
      logDO.setFileSuffix(".docx");
      logDO.setFileStatus(4);
      logDO.setOperationStatus(1);
      logDO.setRemark(versionNo);
      log.info("===>准备==》saveFileLogInfo:{}", logDO);
      return this.fileOperationLogMapper.insert(logDO);
   }

   private void queryAndFillWord(DocumentBuilder builder, File firstTemplatePathFile, File templatePathFile, CatalogueFileExportRequest request) throws IOException {
      CatalogueGetSystemsByOrgIdsRequest systemsByOrgIdsRequest = (CatalogueGetSystemsByOrgIdsRequest)request.clone(CatalogueGetSystemsByOrgIdsRequest.class);
      log.info("=====queryAndFillWord==》入参systemsByOrgIdsRequest:{}", systemsByOrgIdsRequest);
      List<SystemsDTO> systemsDTOS = this.catalogueOrgSysVersionService.getSystemsByOrgIds(systemsByOrgIdsRequest);
      log.info("===queryAndFillWord=>systemsDTOS:{}", systemsDTOS);
      Map<Long, String> orgMap = new HashMap();
      Map<Long, String> sysMap = new HashMap();
      Iterator var9 = systemsDTOS.iterator();

      while(var9.hasNext()) {
         SystemsDTO systemsDTO = (SystemsDTO)var9.next();
         Long orgId = systemsDTO.getOrgId();
         String nodeName = systemsDTO.getNodeName();
         Long sysId = systemsDTO.getSysId();
         String sysName = systemsDTO.getPlatformName();
         if (orgMap.get(orgId) == null && orgId != null) {
            orgMap.put(orgId, nodeName);
         }

         if (sysMap.get(sysId) == null && sysId != null) {
            sysMap.put(sysId, sysName);
         }
      }

      log.info("填充第一章内容");
      WordsUtils.fillTitle(builder, "第一章 资源目录汇总表");
      WordFirstModelData firstModelData = this.fillFirstSection(builder, firstTemplatePathFile, systemsDTOS, request, orgMap);
      log.info("填充第二章内容");
      WordsUtils.fillTitle(builder, "第二章 结构化数据资源目录");
      if (firstModelData != null) {
         List<WordFirstModelOrgData> orgList = firstModelData.getOrgList();
         Map<Integer, Long> orgIndexMap = new HashMap();
         Iterator var24 = orgList.iterator();

         while(var24.hasNext()) {
            WordFirstModelOrgData orgData = (WordFirstModelOrgData)var24.next();
            Integer index = orgData.getIndex();
            Long orgId = orgData.getOrgId();
            log.info("按照index排序--拿到orgId===》index:{};orgId", index, orgId);
            if (orgIndexMap.get(index) == null) {
               orgIndexMap.put(index, orgId);
            }
         }

         int orgIndex = 0;
         Map<Long, List<SystemsDTO>> map = (Map)systemsDTOS.stream().collect(Collectors.groupingBy(SystemsDTO::getOrgId));
         Iterator var29 = orgIndexMap.entrySet().iterator();

         while(var29.hasNext()) {
            Map.Entry<Integer, Long> entry = (Map.Entry)var29.next();
            ++orgIndex;
            Integer tempKey = (Integer)entry.getKey();
            Long tempOrgId = (Long)entry.getValue();
            List<SystemsDTO> tempSystems = (List)map.get(tempOrgId);
            String nodeName = (String)orgMap.get(tempOrgId);
            log.info("=====>tempKey:{};tempOrgId:{};===tempSystems:{}", new Object[]{tempKey, tempOrgId, tempSystems});
            String orgPre = WordsUtils.fillOrg(builder, orgMap, tempOrgId, orgIndex, nodeName);
            this.queryTableAndFields(builder, templatePathFile, tempSystems, request, orgIndex, tempOrgId, orgMap, sysMap, orgPre, nodeName);
         }
      }

   }

   private WordCoverModelData constructCoverModel(CatalogueFileExportRequest request) {
      WordCoverModelData coverModelData = new WordCoverModelData();
      CatalogueVersionLogDO versionLogDO = this.catalogueVersionLogService.findVersionLogByVersionNo(request, request.getVersionNo());
      if (versionLogDO == null) {
         log.info("=====>初始化封皮数据");
         coverModelData.setYear("");
         coverModelData.setMonth("");
         coverModelData.setDate("");
         coverModelData.setAuthor("");
         coverModelData.setVersion("");
      } else {
         Date createTime = versionLogDO.getCreatedTime();
         LocalDateTime localDateTime = DateUtil.date2LocalDateTime(createTime);
         int year = localDateTime.getYear();
         int month = localDateTime.getMonth().getValue();
         log.info("===>year:{};month:{}", year, month);
         coverModelData.setYear(year + "");
         coverModelData.setMonth(month + "");
         String date = DateUtil.getStrFromDate(createTime);
         coverModelData.setDate(date);
         coverModelData.setAuthor(versionLogDO.getCreatedBy());
         coverModelData.setVersion(versionLogDO.getVersionNo());
      }

      return coverModelData;
   }

   private WordFirstModelData fillFirstSection(DocumentBuilder builder, File firstTemplatePathFile, List<SystemsDTO> systemsDTOS, CatalogueFileExportRequest request, Map<Long, String> orgMap) {
      Long[] sysIdArray = new Long[systemsDTOS.size()];
      Map<Long, Long> sysOrgMap = new HashMap();
      int i = 0;
      Iterator var9 = systemsDTOS.iterator();

      while(var9.hasNext()) {
         SystemsDTO systemsDTO = (SystemsDTO)var9.next();
         Long sysId = systemsDTO.getSysId();
         Long orgId = systemsDTO.getOrgId();
         log.info("===>第一章组装数据，sysId:{};orgId:{}", sysId, orgId);
         if (sysId != null) {
            sysOrgMap.put(sysId, orgId);
            sysIdArray[i] = sysId;
            ++i;
         }
      }

      List<Integer> cellList = new ArrayList();
      CatalogueGetTablesBySysIdsAndVersionNo getTablesBySysIdsAndVersionNo = (CatalogueGetTablesBySysIdsAndVersionNo)request.clone(CatalogueGetTablesBySysIdsAndVersionNo.class);
      getTablesBySysIdsAndVersionNo.setSysIds(sysIdArray);
      WordFirstModelData firstModelData = this.catalogueSysTableVersionService.getTablesBySysIdsAndVersionNo(getTablesBySysIdsAndVersionNo, sysOrgMap, orgMap, cellList);
      log.info("===>cellList:{}", cellList);
      Document firstTempDoc = WordsUtils.tempDoc((File)firstTemplatePathFile, firstModelData);
      DocumentBuilder firstBuilder = new DocumentBuilder(firstTempDoc);
      firstBuilder.getCellFormat().setVerticalMerge(0);
      List<WordFirstModelOrgData> orgList = firstModelData.getOrgList();
      int length = orgList.size();

      for(int k = 1; k < length + 1; ++k) {
         for(int l = 0; l < 2; ++l) {
            firstBuilder.moveToCell(0, k, l, 0);
            if (cellList.contains(k)) {
               firstBuilder.getCellFormat().setVerticalMerge(1);
               firstBuilder.getCellFormat().setVerticalAlignment(1);
               firstBuilder.getParagraphFormat().setAlignment(1);
            } else {
               firstBuilder.getCellFormat().setVerticalMerge(2);
            }
         }
      }

      builder.insertDocument(firstTempDoc, 1);
      log.info("===>fillFirstSection==>结束");
      return firstModelData;
   }

   private void queryTableAndFields(DocumentBuilder builder, File templatePathFile, List<SystemsDTO> tempSystems, CatalogueFileExportRequest request, int orgIndex, Long tempOrgId, Map<Long, String> orgMap, Map<Long, String> sysMap, String orgPre, String nodeName) throws IOException {
      int sysIndex = 0;
      Iterator var12 = tempSystems.iterator();

      while(var12.hasNext()) {
         SystemsDTO tempSystem = (SystemsDTO)var12.next();
         Long sysId = tempSystem.getSysId();
         log.info("======>queryTableAndFields==>处理org标题==orgIndex:{};tempOrgId:{};sysId:{};", new Object[]{orgIndex, tempOrgId, sysId});
         ++sysIndex;
         String sysPre = null;
         if (sysId != null) {
            sysPre = WordsUtils.fillSys(builder, sysMap, sysId, sysIndex, orgPre);
         }

         CatalogueGetTablesAndFieldsBySysIdRequest tableBySysIdsRequest = (CatalogueGetTablesAndFieldsBySysIdRequest)request.clone(CatalogueGetTablesAndFieldsBySysIdRequest.class);
         tableBySysIdsRequest.setSysId(sysId);
         log.info("=====queryTableAndFields==>tableBySysIdsRequest:{}", tableBySysIdsRequest);
         List<TablesAndFieldsDTO> tablesAndFieldsDTOS = this.catalogueOrgSysVersionService.getTablesAndFieldsBySysId(tableBySysIdsRequest);
         if (!tablesAndFieldsDTOS.isEmpty()) {
            log.info("===>准备填充目录表标题及内容，sysId:{};nodeName:{}", sysId, nodeName);
            List<WordModelData> tempWordModelDataList = this.parseWordDataFromMap(tablesAndFieldsDTOS, tempOrgId, orgMap, sysMap, sysId, nodeName);
            WordsUtils.fillAndInsertDoc(builder, templatePathFile, tempWordModelDataList, sysPre);
         }
      }

   }

   private List<WordModelData> parseWordDataFromMap(List<TablesAndFieldsDTO> result, Long tempOrgId, Map<Long, String> orgMap, Map<Long, String> sysMap, Long sysId, String nodeName) {
      Map<Long, List<TablesAndFieldsDTO>> tableIdGroupMap = (Map)result.stream().collect(Collectors.groupingBy(TablesAndFieldsDTO::getTableId));
      if (tableIdGroupMap != null) {
         List<WordModelData> wordList = new ArrayList();
         Iterator var9 = tableIdGroupMap.entrySet().iterator();

         while(var9.hasNext()) {
            Map.Entry<Long, List<TablesAndFieldsDTO>> tableEntry = (Map.Entry)var9.next();
            WordModelData wordModelData = new WordModelData();
            log.info("=====parseWordDataFromMap=>tempOrgId:{};orgMap.get(tempOrgId):{}", tempOrgId, orgMap.get(tempOrgId));
            wordModelData.setOrgId(tempOrgId);
            wordModelData.setOrgName((String)orgMap.get(tempOrgId));
            wordModelData.setSysName((String)sysMap.get(sysId));
            Long tempTableId = (Long)tableEntry.getKey();
            List<TablesAndFieldsDTO> fieldList = (List)tableEntry.getValue();
            if (fieldList != null) {
               log.info("开始构造wordModelData===》");
               wordModelData = this.constructWordModelData(wordModelData, fieldList);
            } else {
               log.info("开始初始化initWordModelData===》");
               ConvertWordDataUtil.initWordModelData(wordModelData);
               wordModelData.setFieldList(ConvertWordDataUtil.initWordFieldData());
            }

            wordModelData.setAdministrationName(nodeName);
            wordList.add(wordModelData);
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
         if (tempFieldDto.getTableRemark() == null) {
            wordModelData.setTableRemark("");
         }

         if (tempFieldDto.getTableShareDesc() == null) {
            wordModelData.setTableShareDesc("");
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

}
