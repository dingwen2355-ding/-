package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
public class SysAdminUserDTO implements Serializable {
   private static final long serialVersionUID = -78869918849574860L;
   @ApiModelProperty("用户ID")
   private Long id;
   @ApiModelProperty("登录名")
   private String login;
   @ApiModelProperty("用户名")
   private String name;
   @ApiModelProperty("电话号码")
   private String tel;
   @ApiModelProperty("用户状态 0：正常 1：冻结")
   private Integer status;
   @ApiModelProperty("角色id")
   private String roleId;
   @ApiModelProperty("角色名称")
   private String roleName;
   @ApiModelProperty("角色集合")
   private List<SysRole> roleList;
   @ApiModelProperty("组织集合")
   private List<CatalogueOrgDO> orgList;
   @ApiModelProperty("头像(文件路径)")
   private String portrait;
   @ApiModelProperty("邮箱")
   private String email;
   @ApiModelProperty("类型 1：管理员  2：普通用户")
   private Integer type;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("创建时间")
   private Date createdTime;
   @ApiModelProperty("创建人")
   private String createdBy;
   @ApiModelProperty("更新时间")
   private Date updatedTime;
   @ApiModelProperty("更新人")
   private String updatedBy;
}
