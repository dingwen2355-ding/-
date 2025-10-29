package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.ImportPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导入的新预案
 *
 * @author ：0049003788
 * @date ：2022/10/26 9:47
 */
@Api(tags = "导入预案")
@RestController
@RequestMapping("/importPlan")
public class ImportPlanController {
    @Autowired
    ImportPlanService importPlanService;

    @ApiOperation(value = "查询预案")
    @GetMapping("/query")
    public Result query(@RequestParam(value = "planId", required = false) Integer planId) {
        return importPlanService.queryPlan(planId);
    }
}
