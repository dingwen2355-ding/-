/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.jslc.modules.szbh.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用管理视图实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
public class BhUserApplicationGroupRestVO {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String account;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    /**
     * 职位
     */
    @ApiModelProperty(value = "职位")
    private String position;
    /**
     * 应用
     */
    @ApiModelProperty(value = "应用")
    private IPage<BhUserApplicationManagementRestVO> appList;
    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private String lastLoginTime;
}
