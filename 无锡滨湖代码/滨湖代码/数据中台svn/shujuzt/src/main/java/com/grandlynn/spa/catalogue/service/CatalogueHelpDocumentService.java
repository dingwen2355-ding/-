package com.grandlynn.spa.catalogue.service;

import com.grandlynn.spa.catalogue.entity.CatalogueHelpDocument;
import java.util.List;

public interface CatalogueHelpDocumentService {
   boolean insert(CatalogueHelpDocument catalogueHelpDocument);

   List<CatalogueHelpDocument> getList();
}
