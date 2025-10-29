package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Arrays;
@Data
@TableName("public.sys_template")
@ApiModel(
   value = "SysTemplate",
   description = "模板"
)
public class SysTemplate extends BaseEntity {
   @TableField("code")
   private String code;
   @TableField("file")
   private byte[] file;
   @TableField("file_name")
   private String fileName;

   public String getCode() {
      return this.code;
   }

   public byte[] getFile() {
      return this.file;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setCode(final String code) {
      this.code = code;
   }

   public void setFile(final byte[] file) {
      this.file = file;
   }

   public void setFileName(final String fileName) {
      this.fileName = fileName;
   }

   public SysTemplate() {
   }

   public SysTemplate(final String code, final byte[] file, final String fileName) {
      this.code = code;
      this.file = file;
      this.fileName = fileName;
   }
}
