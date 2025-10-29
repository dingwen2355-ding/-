package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetVersionsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueUpdateVersionUrlRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetVersionsResponse;
import com.grandlynn.spa.catalogue.dto.CatalogueVersionLogTableDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueVersionLogDO;
import com.grandlynn.util.pageHelper.PageBean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface CatalogueVersionLogService extends IService<CatalogueVersionLogDO> {
   PageBean<CatalogueGetVersionsResponse> getVersions(CatalogueGetVersionsRequest request);

   String getMaxVersionNo();

   String getMaxVersionNo(BaseRequest request);

   CatalogueVersionLogDO getMaxVersionInfo(BaseRequest request);

   CatalogueGetVersionsResponse getVersionInfoByVersionNo(String versionNo);

   List<CatalogueVersionLogDO> getVersionNoWord();

   String generateVersionInfo(BaseRequest request);

   List<CatalogueVersionLogDO> findVersionLogs(BaseRequest request, Date startDate, Date endDate);

   CatalogueVersionLogDO findVersionLogByVersionNo(BaseRequest request, String versionNo);

   boolean updateVersionUrl(CatalogueUpdateVersionUrlRequest request);

   List<CatalogueVersionLogDO> findAllVersionLogs(BaseRequest request);

   List<CatalogueVersionLogTableDTO> findAllOrgPublish(Timestamp beginTime, Timestamp endTime);
}
