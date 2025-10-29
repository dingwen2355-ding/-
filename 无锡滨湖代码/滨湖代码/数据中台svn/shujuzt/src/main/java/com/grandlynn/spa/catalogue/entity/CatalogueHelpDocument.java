package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CatalogueHelpDocument {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Integer id;
   private String fileName;
   private String filePath;
   private String fileGroup;
   private Long uploadTime;

   public CatalogueHelpDocument() {
   }

   public CatalogueHelpDocument(String fileName, String filePath, String fileGroup, Long uploadTime) {
      this.fileName = fileName;
      this.filePath = filePath;
      this.fileGroup = fileGroup;
      this.uploadTime = uploadTime;
   }

}
