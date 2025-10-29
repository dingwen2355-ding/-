package com.znv.manage.controller.event;

import com.znv.manage.common.bean.event.Assess;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.event.AssessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TCfgAssess表控制层
 *
 * @author 0049003788
 * @since 2022-12-12 10:28:58
 */
@RestController
@RequestMapping("assess")
@Api(tags = "应急能力评估")
public class AssessController {
    @Resource
    private AssessService assessService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody Assess assess) {
        return assessService.insert(assess);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody Assess assess) {
        return assessService.update(assess);
    }

    @GetMapping("/del")
    @ApiOperation("删除")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return assessService.deleteById(id);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return assessService.deleteBatch(ids);
    }

    @GetMapping("/query-condition")
    @ApiOperation("查询")
    public Result queryByCondition(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "eventId", required = false) Integer eventId,
                                   @RequestParam(value = "eventTitle", required = false) String eventTitle,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return assessService.queryByCondition(id, name, eventId, eventTitle, page, pageSize);
    }
}

