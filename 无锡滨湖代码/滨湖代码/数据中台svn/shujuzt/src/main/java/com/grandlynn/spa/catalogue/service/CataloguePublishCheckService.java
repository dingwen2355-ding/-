package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.domain.request.ApprovePassAllRequest;
import com.grandlynn.spa.catalogue.domain.request.ApproveRequest;
import com.grandlynn.spa.catalogue.entity.CataloguePublishCheckDO;

public interface CataloguePublishCheckService extends IService<CataloguePublishCheckDO> {
   void approve(ApproveRequest request, ApproveStateEnum state);

   Boolean passAll(ApprovePassAllRequest request);

   void publish(BaseRequest request);
}
