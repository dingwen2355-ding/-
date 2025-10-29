package com.grandlynn.spa.catalogue.service.impl;

import com.grandlynn.spa.catalogue.job.ExportWordDataJob;
import com.grandlynn.spa.catalogue.service.TriggersJobService;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TriggersJobServiceImpl implements TriggersJobService {
   @Resource
   private ExportWordDataJob exportWordDataJob;

   @Async
   public void exportWord() {
      this.exportWordDataJob.exportWord();
   }
}
