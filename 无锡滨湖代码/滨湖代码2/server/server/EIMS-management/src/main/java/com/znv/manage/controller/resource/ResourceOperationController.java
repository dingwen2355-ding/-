package com.znv.manage.controller.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.ResourceOperation;
import com.znv.manage.service.resource.ResourceOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TCfgResourceOperation表控制层
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:26
 */
@RestController
@RequestMapping("resourceOperation")
@Api(tags = "物资操作记录")
public class ResourceOperationController {
    @Resource
    private ResourceOperationService resourceOperationService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody ResourceOperation resourceOperation) {
        return resourceOperationService.insert(resourceOperation);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody ResourceOperation resourceOperation) {
        return resourceOperationService.update(resourceOperation);
    }


    @GetMapping("/query")
    @ApiOperation("查询")
    public Result query(@RequestParam(value = "id", required = false) Integer id,
                        @RequestParam(value = "stuffName", required = false) String stuffName,
                        @RequestParam(value = "type", required = false) String type,
                        @RequestParam(value = "info", required = false) String info,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return resourceOperationService.query(id, stuffName, type, info, page, pageSize);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return resourceOperationService.deleteBatch(ids);
    }

    @GetMapping("/export")
    @ApiOperation("导出excel")
    public Result export(@RequestParam(value = "ids", required = false) String ids) {
        return resourceOperationService.export(ids);
    }

}

