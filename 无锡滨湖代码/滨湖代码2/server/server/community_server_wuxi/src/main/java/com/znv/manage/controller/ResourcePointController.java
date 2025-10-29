package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.ResourcePointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 场所、专家、学校、仓库地图撒点
 *
 * @author ：0049003788
 * @date ：2023/1/12 14:10
 */
@Api(tags = "场所、专家、学校、仓库地图撒点")
@RestController
@RequestMapping("rescue")
public class ResourcePointController {
    @Resource
    ResourcePointService resourcePointService;

    @ApiOperation("查询撒点信息")
    @GetMapping("queryPoint")
    public Result queryPoint(@RequestParam(value = "type") String type) {
        return resourcePointService.queryPoint(type);
    }
}
