package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
@Data
@TableName("catalogue_version_log")
public class CatalogueVersionLogDO extends BaseEntity {
   @TableField("version_no")
   private String versionNo;
   @TableField("version_sort")
   private Long versionSort;
   @TableField("org_data")
   @Transient
   private Object orgData;
   @TableField("version_content_data")
   @Transient
   private Object versionContentData;
   @TableField("version_state")
   private Integer versionState;
   @TableField("version_url")
   private String versionUrl;

}
