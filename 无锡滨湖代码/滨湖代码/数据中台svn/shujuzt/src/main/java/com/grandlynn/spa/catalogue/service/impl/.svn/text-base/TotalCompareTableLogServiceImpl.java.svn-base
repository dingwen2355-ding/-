package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.grandlynn.spa.catalogue.common.utils.MatchedResult;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.common.utils.TotalCompareHelper;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysAndOrgNameBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.TotalCompareRequest;
import com.grandlynn.spa.catalogue.domain.response.TotalCompareTableLogBySysResponse;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TotalCompareUnmatchDto;
import com.grandlynn.spa.catalogue.entity.*;
import com.grandlynn.spa.catalogue.mapper.TotalCompareTableLogMapper;
import com.grandlynn.spa.catalogue.service.*;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("totalTableCompareLogService")
public class TotalCompareTableLogServiceImpl extends ServiceImpl<TotalCompareTableLogMapper, TotalCompareTableLogDO> implements TotalCompareTableLogService {
   private static final Logger log = LoggerFactory.getLogger(TotalCompareTableLogServiceImpl.class);
   @Resource
   private ForeignTotalListService foreignTotalListService;
   @Resource
   private TotalCompareFieldLogService totalCompareFieldLogService;
   @Resource
   private CatalogueOrgSysService catalogueOrgSysService;
   @Resource
   private CatalogueSysTableService catalogueSysTableService;
   @Resource
   private CatalogueTableFieldService catalogueTableFieldService;

   public PageBean<TotalCompareTableLogBySysResponse> getResult(TotalCompareRequest request) {
      LambdaQueryWrapper<CatalogueSysTableDO> queryWp = new LambdaQueryWrapper();
      queryWp.eq(CatalogueSysTableDO::getOrgSysId, request.getSysId());
      queryWp.in(null != request.getTableState() && request.getTableState().length > 0, CatalogueSysTableDO::getTableState, request.getTableState());
      queryWp.like(StrUtil.isNotBlank(request.getTableName()), CatalogueSysTableDO::getDataResourceName, request.getTableName());
      Page page = PageUtil.getPage(request.getPage(), request.getSize());
      PageBean<CatalogueSysTableDO> tables = new PageBean(this.catalogueSysTableService.list(queryWp));
      List<Long> tableIds = (List)tables.getContent().stream().map((t) -> {
         return t.getId();
      }).collect(Collectors.toList());
      if (CollUtil.isEmpty(tableIds)) {
         return new PageBean(page);
      } else {
         LambdaQueryWrapper<TotalCompareTableLogDO> wp = new LambdaQueryWrapper();
         wp.in(TotalCompareTableLogDO::getCataTableId, tableIds);
         wp.orderByDesc(TotalCompareTableLogDO::getRoute);
         List<TotalCompareTableLogDO> logs = this.list(wp);
         PageBean<TotalCompareTableLogBySysResponse> rstPage = new PageBean();
         BeanUtil.copyProperties(tables, rstPage, new String[0]);
         rstPage.setContent(this.gourpInsertExample(tables, logs));
         return rstPage;
      }
   }

   public List<TotalCompareFieldLogDO> getResultByTableLogId(TotalCompareRequest request) {
      List<TotalCompareFieldLogDO> result;
      if (null == request.getTableLogId()) {
         return this.catalogueTableFieldService.list((new LambdaQueryWrapper<CatalogueTableFieldDO>()).eq(CatalogueTableFieldDO::getSysTableId, request.getTableId()))
            .stream().map(field -> new TotalCompareFieldLogDO(request.getTableLogId(), field)).collect(Collectors.toList());
      } else {
         result = this.totalCompareFieldLogService.list((new LambdaQueryWrapper<TotalCompareFieldLogDO>()).eq(TotalCompareFieldLogDO::getTableLogId, request.getTableLogId()));
         result.forEach((r) -> {
            if (BigDecimal.ZERO.compareTo(r.getRoute()) == 0) {
               r.setTotalField("—");
            }
         });
         return result;
      }
   }

   @Async("asyncExecutorService")
   public void calculate(Long tableId) {
      CatalogueSysTableDO table = (CatalogueSysTableDO)this.catalogueSysTableService.getById(tableId);
      String nodeNo = this.getOrgNoBySysId(table.getOrgSysId());
      log.info("全量比对开始===========>[{}]-[{}]", nodeNo, table.getDataResourceName());
      List<TotalCompareTableLogDO> tableLogs = this.matchingTotalTable(nodeNo, table);
      this.saveBatch(tableLogs);
      tableLogs.stream().forEach((tableLog) -> {
         List<TotalCompareFieldLogDO> fieldLogs = this.matchingTotalField(tableLog, table);
         this.totalCompareFieldLogService.saveBatchByAsync(fieldLogs);
      });
   }

   public void testUnmatch() {
      List<TotalCompareUnmatchDto> rst = ((TotalCompareTableLogMapper)this.baseMapper).unmatch();
      Long[] ids = (Long[])rst.stream().filter((r) -> {
         log.info("未比对成功全量清单===>{}", r.toString());
         return r.getCount() > 0;
      }).map(TotalCompareUnmatchDto::getTableId).toArray((x$0) -> {
         return new Long[x$0];
      });
      Long[] var3 = ids;
      int var4 = ids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Long id = var3[var5];
         this.calculate(id);
      }

   }

   private List<TotalCompareTableLogBySysResponse> gourpInsertExample(PageBean<CatalogueSysTableDO> tables, List<TotalCompareTableLogDO> logs) {
      new TotalCompareTableLogBySysResponse();
      Map<Long, List<TotalCompareTableLogDO>> group = (Map)logs.stream().collect(Collectors.groupingBy(TotalCompareTableLogDO::getCataTableId));
      return (List)tables.getContent().stream().map((t) -> {
         List<TotalCompareTableLogDO> child = group.get(t.getId()) == null ? new ArrayList() : (List)group.get(t.getId());
         this.suppleNo((List)child, t);
         String nm = String.format("%s(%s)", t.getDataResourceName(), ((List)child).size());
         return new TotalCompareTableLogBySysResponse(nm, nm, new BigDecimal(100.0), (List)child);
      }).collect(Collectors.toList());
   }

   private void suppleNo(List<TotalCompareTableLogDO> child, CatalogueSysTableDO t) {
      child.add(0, new TotalCompareTableLogDO(t));

      for(int i = 0; i < child.size(); ++i) {
         TotalCompareTableLogDO c = (TotalCompareTableLogDO)child.get(i);
         if (BigDecimal.ZERO.compareTo(c.getRoute()) == 0) {
            c.setTotalTableName("暂无匹配目录");
         }

         c.setCataTableName(String.format("%s、%s", i + 1, c.getCataTableName()));
      }

   }

   private void loseOtherLog(Long sysId, Long totalSysId) {
   }

   private String getOrgNoBySysId(Long sysId) {
      CatalogueGetSysAndOrgNameBySysIdsRequest request = new CatalogueGetSysAndOrgNameBySysIdsRequest();
      request.setSysIds(new Long[]{sysId});
      List<SystemsDTO> sysDTOs = this.catalogueOrgSysService.getSysAndOrgNameBySysIds(request);
      if (CollUtil.isEmpty(sysDTOs)) {
         throw new ApplicationException("该系统未找到相应组织机构代码");
      } else {
         return ((SystemsDTO)sysDTOs.get(0)).getNodeNo();
      }
   }

   private List<TotalCompareTableLogDO> matchingTotalTable(String nodeNo, CatalogueSysTableDO table) {
      List<ForeignTotalListDO> totalList = this.foreignTotalListService.list((new LambdaQueryWrapper<ForeignTotalListDO>()).eq(ForeignTotalListDO::getNodeNo, nodeNo));
      String tName = table.getDataResourceName();
      List<MatchedResult<ForeignTotalListDO>> result = TotalCompareHelper.matchingTableName(tName, totalList, 4);
      log.info("全量比对,目录匹配结束===========>目录[{}]", tName);
      return (List)result.stream().map((i) -> {
         ForeignTotalListDO total = (ForeignTotalListDO)i.getData();
         TotalCompareTableLogDO log = new TotalCompareTableLogDO(table, nodeNo, (ForeignTotalListDO)i.getData(), i.getRoute());
         return log;
      }).collect(Collectors.toList());
   }

   private List<TotalCompareFieldLogDO> matchingTotalField(TotalCompareTableLogDO tableLogs, CatalogueSysTableDO table) {
      List<CatalogueTableFieldDO> fields = this.catalogueTableFieldService.list((new LambdaQueryWrapper<CatalogueTableFieldDO>()).eq(CatalogueTableFieldDO::getSysTableId, table.getId()));
      List<TotalCompareFieldLogDO> logs = (List)fields.stream().map((field) -> {
         String fieldNm = field.getFieldNameCh();
         List<MatchedResult<String>> result = TotalCompareHelper.matchingFieldName(fieldNm, tableLogs.getTotalFields(), 1);
         if (CollUtil.isEmpty(result)) {
            return null;
         } else {
            MatchedResult<String> rItem = (MatchedResult)result.get(0);
            log.info("全量比对,匹配字段===========>[{}]---[{}]", fieldNm, rItem.getData());
            return new TotalCompareFieldLogDO(tableLogs.getId(), fieldNm, (String)rItem.getData(), rItem.getRoute());
         }
      }).collect(Collectors.toList());
      return logs;
   }
}
