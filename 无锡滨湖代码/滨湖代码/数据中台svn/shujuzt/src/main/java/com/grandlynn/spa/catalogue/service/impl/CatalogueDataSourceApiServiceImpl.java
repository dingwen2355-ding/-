package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceApiDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueDataSourceApiMapper;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CatalogueDataSourceApiServiceImpl extends ServiceImpl<CatalogueDataSourceApiMapper, CatalogueDataSourceApiDO> implements CatalogueDataSourceApiService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueDataSourceApiServiceImpl.class);
}
