package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileExportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileImportRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileOperationLogGetCurrentStatusRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileOperationLogGetLogRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueFileTemplateDownloadRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueFileOperationLogGetCurrentStatusResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueFileOperationLogGetLogResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueImportFileResponse;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.util.pageHelper.PageBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CatalogueFileOperationLogService extends IService<CatalogueFileOperationLogDO> {
   void downloadTemplate(HttpServletResponse response, CatalogueFileTemplateDownloadRequest request) throws IOException;

   CatalogueImportFileResponse uploadAndValidFiles(MultipartFile multipartFile, CatalogueFileImportRequest request);

   PageBean<CatalogueFileOperationLogGetLogResponse> getFileOperationLog(CatalogueFileOperationLogGetLogRequest request);

   List<CatalogueFileOperationLogGetCurrentStatusResponse> getFileCurrentStatus(CatalogueFileOperationLogGetCurrentStatusRequest request);

   void exportFile(HttpServletResponse response, CatalogueFileExportRequest request);
}
