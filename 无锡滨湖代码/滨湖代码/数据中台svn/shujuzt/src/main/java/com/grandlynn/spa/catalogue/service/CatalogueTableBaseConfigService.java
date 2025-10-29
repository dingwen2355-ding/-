package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueBaseConfigQueryRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueTableBaseConfigDO;
import com.grandlynn.util.pageHelper.PageBean;

public interface CatalogueTableBaseConfigService extends IService<CatalogueTableBaseConfigDO> {
   PageBean<CatalogueTableBaseConfigDO> query(CatalogueBaseConfigQueryRequest request);
}
