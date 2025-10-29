package com.znv.manage.controller;

import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.service.ModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "模块管理")
@Slf4j
@RestController
@RequestMapping("/module")
public class ModuleController {

    @Resource
    ModuleService moduleService;

    @ApiOperation("模块查询")
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<Map<String, Object>> queryModules(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "moduleName", required = false) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> maps = moduleService.queryModules(id, moduleName, moduleUrl, description, upModuleId);
            ret.addData(maps);
            ret.setResult(ResultBean.SUCESS);
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @ApiOperation("模块新增")
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<String> insertModule(
            @RequestParam(value = "moduleName", required = true) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            moduleService.insertModule(moduleName, moduleUrl, description, upModuleId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @ApiOperation("模块编辑")
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<String> updateModule(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "moduleName", required = true) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            moduleService.updateModule(id, moduleName, moduleUrl, description, upModuleId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @ApiOperation("模块删除")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResultBean<String> deleteModule(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "moduleName", required = false) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            moduleService.deleteModule(id, moduleName, moduleUrl, description, upModuleId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }
}
