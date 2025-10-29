package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.system.po.SysOperLog;
import cn.wxgis.jc.system.select.SysOperLogSelect;
import cn.wxgis.jc.system.web.service.SysOperlogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/operLog")
@AllArgsConstructor
@Api(value = "SysOperLogController", tags = "登陆日志相关APi")
public class SysOperLogController {

    private final SysOperlogService operlogService;

    @ApiOperation(value ="操作日志分页列表", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody SysOperLogSelect select) {
        IPage<SysOperLog> page = operlogService.page(select);
        return JsonResult.ok(page);
    }

    @ApiOperation(value ="操作日志详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "字典类型id", required = true)  @PathVariable String id){
        return JsonResult.ok(operlogService.selectById(id));
    }
}
