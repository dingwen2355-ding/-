package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grandlynn.spa.catalogue.entity.CatalogueHelpDocument;
import com.grandlynn.spa.catalogue.mapper.CatalogueHelpDocumentMapper;
import com.grandlynn.spa.catalogue.service.CatalogueHelpDocumentService;
import java.lang.invoke.SerializedLambda;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogueHelpDocumentServiceImpl implements CatalogueHelpDocumentService {
   @Autowired
   private CatalogueHelpDocumentMapper catalogueHelpDocumentMapper;

   public boolean insert(CatalogueHelpDocument catalogueHelpDocument) {
      return this.catalogueHelpDocumentMapper.insert(catalogueHelpDocument) == 1;
   }

   public List<CatalogueHelpDocument> getList() {
      LambdaQueryWrapper<CatalogueHelpDocument> lambdaQueryWrapper = new LambdaQueryWrapper();
      lambdaQueryWrapper.orderByDesc(CatalogueHelpDocument::getUploadTime);
      return this.catalogueHelpDocumentMapper.selectList(lambdaQueryWrapper);
   }

}
