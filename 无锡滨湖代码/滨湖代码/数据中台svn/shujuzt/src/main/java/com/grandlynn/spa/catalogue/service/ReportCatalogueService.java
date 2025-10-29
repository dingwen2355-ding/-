package com.grandlynn.spa.catalogue.service;

import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogueCategoryRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportPresSystemRequest;

public interface ReportCatalogueService {
   String catalogueCategoryRegister(ReportCatalogueCategoryRequest request);

   Boolean catalogueCategoryModify(ReportCatalogueCategoryRequest request);

   Boolean catalogueCategoryCancel(ReportCatalogueCategoryRequest request);

   String presSystemRegister(ReportPresSystemRequest request);

   Boolean presSystemModify(ReportPresSystemRequest request);

   String catalogueRegister(ReportCatalogueRequest request);

   Boolean catalogueModify(ReportCatalogueRequest request);

   Boolean catalogueCancel(ReportCatalogueRequest request);
}
