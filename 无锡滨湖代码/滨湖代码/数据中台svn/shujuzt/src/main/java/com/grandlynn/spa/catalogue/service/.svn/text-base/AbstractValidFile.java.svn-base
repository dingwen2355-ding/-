package com.grandlynn.spa.catalogue.service;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueImportFileResponse;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractValidFile {
   public CatalogueImportFileResponse uploadAndValidFiles(MultipartFile multipartFile, BaseRequest request) {
      this.validFiles(multipartFile, request);
      this.validExistData(multipartFile);
      return this.uploadAndSaveFiles(multipartFile, request);
   }

   public abstract void validFiles(MultipartFile multipartFile, BaseRequest request);

   public abstract void validExistData(MultipartFile multipartFile);

   public abstract CatalogueImportFileResponse uploadAndSaveFiles(MultipartFile multipartFile, BaseRequest request);
}
