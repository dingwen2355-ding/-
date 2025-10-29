package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.TotalCompareFieldLogDO;
import com.grandlynn.spa.catalogue.mapper.TotalCompareFieldLogMapper;
import com.grandlynn.spa.catalogue.service.TotalCompareFieldLogService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("totalFieldCompareLogService")
public class TotalCompareFieldLogServiceImpl extends ServiceImpl<TotalCompareFieldLogMapper, TotalCompareFieldLogDO> implements TotalCompareFieldLogService {
   private static final Logger log = LoggerFactory.getLogger(TotalCompareFieldLogServiceImpl.class);

   @Async
   public void saveBatchByAsync(List<TotalCompareFieldLogDO> fields) {
      super.saveBatch(fields, 500);
   }
}
