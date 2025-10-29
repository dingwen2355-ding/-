package com.znv.manage.controller.event;

import com.znv.manage.common.bean.event.AssessProject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.event.AssessProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TCfgAssessProject表控制层
 *
 * @author 0049003788
 * @since 2022-12-09 13:39:56
 */
@RestController
@RequestMapping("assessProject")
@Api(tags = "应急能力评估项目")
public class AssessProjectController {
    @Resource
    private AssessProjectService assessProjectService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody AssessProject assessProject) {
        return assessProjectService.insert(assessProject);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody AssessProject assessProject) {
        return assessProjectService.update(assessProject);
    }

    @GetMapping("/del")
    @ApiOperation("删除")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return assessProjectService.deleteById(id);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return assessProjectService.deleteBatch(ids);
    }

    @GetMapping("/query-condition")
    @ApiOperation("查询")
    public Result queryByCondition(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "projectName", required = false) String projectName,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return assessProjectService.queryByCondition(id, projectName, page, pageSize);
    }
}

