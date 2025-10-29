package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.domain.response.report.ReportMatterResponse;
import com.grandlynn.spa.catalogue.domain.response.report.TableMatterResponse;
import com.grandlynn.spa.catalogue.dto.TableMatterDTO;
import com.grandlynn.spa.catalogue.entity.TableMatterDO;
import com.grandlynn.spa.catalogue.mapper.TableMatterMapper;
import com.grandlynn.spa.catalogue.service.TableMatterService;
import com.grandlynn.spa.catalogue.utils.HttpUtils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TableMatterServiceImpl extends ServiceImpl<TableMatterMapper, TableMatterDO> implements TableMatterService {
   private static final Logger log = LoggerFactory.getLogger(TableMatterServiceImpl.class);

   public Boolean syncMatterByDate(String date) {
      log.info("同步事项日期：{}", date);

      try {
         String url = "http://2.20.101.126:9190/JSZWFW/Basic?day=" + date + "&pageNo=1";
         HttpUtils.initHttpClient(false, 60);
         Map<String, String> headers = new HashMap();
         String basicStr = "Basic " + Base64.encodeBase64String("bh_bjcx:bh_bjcx#123".getBytes(StandardCharsets.UTF_8)).replaceAll("\r|\n", "");
         headers.put("Authorization", basicStr);
         String response = HttpUtils.get(url, headers);
         if (null != response) {
            ReportMatterResponse baseResponse = (ReportMatterResponse)JSON.parseObject(response, ReportMatterResponse.class);
            if ("200".equals(baseResponse.getCode())) {
               List<TableMatterDTO> matterDOS = (List)baseResponse.getData().stream().map((item) -> {
                  TableMatterResponse categoryResponse = (TableMatterResponse)JSON.parseObject(item.toString(), TableMatterResponse.class);
                  TableMatterDTO matterDO = new TableMatterDTO();
                  BeanUtils.copyProperties(categoryResponse, matterDO);
                  return matterDO;
               }).collect(Collectors.toList());
               if (baseResponse.getTotalPage() > 1) {
                  for(int i = 2; i <= baseResponse.getTotalPage(); ++i) {
                     url = "http://2.20.101.126:9190/JSZWFW/Basic?day=" + date + "&pageNo=" + i;
                     HttpUtils.initHttpClient(false, 60);
                     String responseGet = HttpUtils.get(url, headers);
                     if (null != responseGet) {
                        ReportMatterResponse baseResponseObj = (ReportMatterResponse)JSON.parseObject(responseGet, ReportMatterResponse.class);
                        if ("200".equals(baseResponseObj.getCode())) {
                           log.info("当前页：{}===结果数:{}", i, baseResponseObj.getData().size());
                           List<TableMatterDTO> matterDOList = (List)baseResponseObj.getData().stream().map((item) -> {
                              TableMatterResponse categoryResponse = (TableMatterResponse)JSON.parseObject(item.toString(), TableMatterResponse.class);
                              TableMatterDTO matterDO = new TableMatterDTO();
                              BeanUtils.copyProperties(categoryResponse, matterDO);
                              return matterDO;
                           }).collect(Collectors.toList());
                           matterDOS.addAll(matterDOList);
                        }
                     }
                  }

                  List<TableMatterDO> matterDOList = (List)matterDOS.stream().map(this::mappingDO).collect(Collectors.toList());
                  this.saveBatch(matterDOList);
               }

               log.info("数据总数:{}", matterDOS.size());
            }
         }
      } catch (Exception var12) {
         log.error("同步事项出现异常：", var12);
      }

      return null;
   }

   private TableMatterDO mappingDO(TableMatterDTO dto) {
      TableMatterDO matterDO = new TableMatterDO();
      matterDO.setRowguid(dto.getROWGUID());
      matterDO.setTaskName(dto.getTASK_NAME());
      matterDO.setCatalogCode(dto.getCATALOG_CODE());
      matterDO.setYwcode(dto.getYWCODE());
      matterDO.setTaskCode(dto.getTASK_CODE());
      matterDO.setDeptName(dto.getDEPT_NAME());
      matterDO.setTaskState(dto.getTASK_STATE());
      matterDO.setIsHistory(dto.getIS_HISTORY());
      return matterDO;
   }
}
