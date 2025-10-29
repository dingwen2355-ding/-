package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.CatalogueTableApproveRecordDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableApproveRecordMapper;
import com.grandlynn.spa.catalogue.service.CatalogueTableApproveRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("catalogueTableApproveRecordService")
public class CatalogueTableApproveRecordServiceImpl extends ServiceImpl<CatalogueTableApproveRecordMapper, CatalogueTableApproveRecordDO> implements CatalogueTableApproveRecordService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueTableApproveRecordServiceImpl.class);
}
