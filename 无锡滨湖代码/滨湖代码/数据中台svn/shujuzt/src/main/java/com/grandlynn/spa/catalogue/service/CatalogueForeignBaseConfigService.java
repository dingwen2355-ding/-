package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueForeignConfigQueryRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueForeignBaseConfigDO;
import com.grandlynn.util.pageHelper.PageBean;

public interface CatalogueForeignBaseConfigService extends IService<CatalogueForeignBaseConfigDO> {
   PageBean<CatalogueForeignBaseConfigDO> query(CatalogueForeignConfigQueryRequest request);
}
