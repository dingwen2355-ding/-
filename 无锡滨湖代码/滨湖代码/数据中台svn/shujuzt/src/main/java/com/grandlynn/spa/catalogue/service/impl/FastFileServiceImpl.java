package com.grandlynn.spa.catalogue.service.impl;

import com.grandlynn.spa.catalogue.common.enums.FileTemplateEnum;
import com.grandlynn.spa.catalogue.common.utils.FileUtil;
import com.grandlynn.spa.catalogue.service.FastFileService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.io.File;
import java.io.FileInputStream;
import java.util.Set;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FastFileServiceImpl implements FastFileService {
   private static final Logger log = LoggerFactory.getLogger(FastFileServiceImpl.class);
   @Resource
   private FastFileStorageClient fastFileStorageClient;

   public byte[] downloadTemplate(FileTemplateEnum fileTemplateEnum) {
      return FileUtil.readResourceFile(fileTemplateEnum);
   }

   public byte[] downloadFileFromDfs(String fullPath) {
      log.info("downloadFileFromDfs====>fullPath:{}", fullPath);
      String group = fullPath.substring(0, fullPath.indexOf("/"));
      String path = fullPath.substring(fullPath.indexOf("/") + 1);
      DownloadByteArray downloadByteArray = new DownloadByteArray();
      return (byte[])this.fastFileStorageClient.downloadFile(group, path, downloadByteArray);
   }

   public String uploadFileToDfs(String filePath) {
      String fullPath1 = null;
      FileInputStream ins = null;

      try {
         File tempReadFile = new File(filePath);
         ins = new FileInputStream(tempReadFile);
         StorePath storePath = this.fastFileStorageClient.uploadFile(ins, tempReadFile.length(), "docx", (Set)null);
         fullPath1 = storePath.getFullPath();
         log.info("==>uploadFileToDfs===>文件上传文件服务器返回文件路径:fullPath1={}", fullPath1);
         String tempPath = storePath.getPath();
         String group = storePath.getGroup();
         log.info("fullPath=={};path_1=={}；group=={}", new Object[]{fullPath1, tempPath, group});
      } catch (Exception var11) {
         log.error("===》uploadFileToDfs===文件上传===》异常信息：{}", var11.getMessage());
      } finally {
         FileUtil.closeInputStream(ins);
      }

      return fullPath1;
   }
}
