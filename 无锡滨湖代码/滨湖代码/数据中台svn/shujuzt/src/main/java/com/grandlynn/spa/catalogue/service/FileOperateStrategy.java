package com.grandlynn.spa.catalogue.service;

import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.dto.ExcelFileIdMessageDTO;

import javax.servlet.http.HttpServletResponse;

public interface FileOperateStrategy {
   void importExcel(ExcelFileIdMessageDTO messageDto);

   void exportExcel(HttpServletResponse response, CatalogueFileExportRequest request);

   void exportWord(HttpServletResponse response, CatalogueFileExportRequest request);

   String exportAllWord(HttpServletResponse response, CatalogueFileExportRequest request);

   void exportZip(HttpServletResponse response, CatalogueFileExportRequest request);
}
