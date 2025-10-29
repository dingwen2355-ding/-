package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.SystemLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:
 * @Date:
 * @Description: 注：
 */
@Api(tags = "地图-系统链接")
@RestController
@RequestMapping("systemLink")
public class SystemLinkController {

    @Autowired
    SystemLinkService systemLinkService;

    @ApiOperation(value = "系统链接")
    @GetMapping("getSystemLink")
    public Result getSystemLink() {
        return systemLinkService.getSystemLink();
    }


}
