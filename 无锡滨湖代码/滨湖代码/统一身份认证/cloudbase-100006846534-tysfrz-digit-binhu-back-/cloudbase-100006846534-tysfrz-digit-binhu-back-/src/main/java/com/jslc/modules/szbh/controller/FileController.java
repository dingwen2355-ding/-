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
package com.jslc.modules.szbh.controller;

import cn.hutool.core.lang.UUID;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.service.IBhDeptSyncService;
import com.jslc.modules.szbh.service.IFileService;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件传输 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/file")
@Api(value = "文件传输", tags = "文件传输")
public class FileController extends BladeController {

    private final IFileService fileService;

    @PostMapping("/upload")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public R uploadPic(MultipartFile multipartFile) throws Exception {
        return R.data(fileService.saveFile(multipartFile));
    }


    @PostMapping("/uploadImg")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "上传图片", notes = "上传图片")
    public R uploadImg(MultipartFile multipartFile) throws Exception {
        return R.data(fileService.uploadImg(multipartFile));
    }
}
