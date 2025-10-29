package com.znv.manage.controller.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.WarningRelease;
import com.znv.manage.service.information.CommandWarningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 指挥调度-预警发布
 *
 * @author ：0049003788
 */
@RestController
@Api(tags = "指挥调度-预警发布")
@RequestMapping("/commandWarning")
public class CommandWarningController {
    @Autowired
    CommandWarningService commandWarningService;

    @ApiOperation("查询预警")
    @GetMapping("getWarning")
    public Result getWarning(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "deal", required = false) String deal,
                             @RequestParam(value = "type1", required = false) String type1,
                             @RequestParam(value = "type2", required = false) String type2,
                             @RequestParam(value = "level", required = false) String level,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "area", required = false) String area,
                             @RequestParam(value = "flag", required = false) String flag) {
        return commandWarningService.getWarning(id, title, deal, type1, type2, level, startDate, endDate, area,flag);
    }

    @ApiOperation("查询预警-大屏")
    @GetMapping("getWarningScreen")
    public Result getWarningScreen(@RequestParam(value = "id", required = false) Long id,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "deal", required = false) String deal,
                                   @RequestParam(value = "type1", required = false) String type1,
                                   @RequestParam(value = "type2", required = false) String type2,
                                   @RequestParam(value = "level", required = false) String level,
                                   @RequestParam(value = "startDate", required = false) String startDate,
                                   @RequestParam(value = "endDate", required = false) String endDate,
                                   @RequestParam(value = "area", required = false) String area) {
        return commandWarningService.getWarningScreen(id, title, deal, type1, type2, level, startDate, endDate, area);
    }

    @ApiOperation("修改预警")
    @PostMapping("updateWarning")
    public Result updateWarning(@RequestBody WarningRelease warningRelease) {
        return commandWarningService.updateWarning(warningRelease);
    }

    @ApiOperation("新增预警")
    @PostMapping("insertWarning")
    public Result insertWarning(@RequestBody WarningRelease warningRelease) {
        return commandWarningService.insertWarning(warningRelease);
    }

    @ApiOperation("删除预警")
    @GetMapping("deleteWarning")
    public Result deleteWarning(@RequestParam(value = "id") Long id) {
        return commandWarningService.deleteWarning(id);
    }

    @ApiOperation("处理预警")
    @GetMapping("dealWarning")
    public Result dealWarning(@RequestParam(value = "id") Long id,
                              @RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "deal", required = false) String deal,
                              @RequestParam(value = "userId", required = false) String userId,
                              @RequestParam(value = "title", required = false) String title) {
        return commandWarningService.dealWarning(id, content, deal, userId, title);
    }

    @ApiOperation("预警统计")
    @GetMapping("warningCount")
    public Result warningCount(@RequestParam(value = "area", required = false) String area,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate
    ) {
        return commandWarningService.warningCount(area, type, startDate, endDate);
    }
}
