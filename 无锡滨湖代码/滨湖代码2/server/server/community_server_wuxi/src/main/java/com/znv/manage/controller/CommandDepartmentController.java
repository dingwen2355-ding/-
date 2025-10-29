package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.CommandDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TCfgCommandDepartment表控制层
 *
 * @author 0049003788
 * @since 2022-11-28 11:18:20
 */
@RestController
@RequestMapping("commandDepartment")
@Api(tags = "专项指挥部")
public class CommandDepartmentController {
    @Resource
    private CommandDepartmentService commandDepartmentService;

    @GetMapping("/query-command")
    @ApiOperation("查询指挥部")
    public Result queryCommand() {
        return commandDepartmentService.queryCommand();
    }

    @GetMapping("/query-org")
    @ApiOperation("查询指挥部下的部门信息")
    public Result queryOrg(@RequestParam(value = "commandName") String commandName) {
        return commandDepartmentService.queryOrg(commandName);
    }
}

