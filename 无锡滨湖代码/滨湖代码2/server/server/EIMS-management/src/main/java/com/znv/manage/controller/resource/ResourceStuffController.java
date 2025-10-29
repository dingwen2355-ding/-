package com.znv.manage.controller.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.ResourceOperation;
import com.znv.manage.common.bean.resource.ResourceStuff;
import com.znv.manage.service.LogService;
import com.znv.manage.service.resource.ResourceStuffService;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * TCfgResourceStuff表控制层
 *
 * @author 0049003788
 * @since 2023-05-19 15:13:41
 */
@RestController
@RequestMapping("resourceStuff")
@Api(tags = "物资")
public class ResourceStuffController {
    @Resource
    private ResourceStuffService resourceStuffService;

    @Resource
    LogService logService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody ResourceStuff resourceStuff) {
        return resourceStuffService.insert(resourceStuff);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody ResourceStuff resourceStuff) {
        return resourceStuffService.update(resourceStuff);
    }

    @GetMapping("/del")
    @ApiOperation("删除")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return resourceStuffService.deleteById(id);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return resourceStuffService.deleteBatch(ids);
    }

    @GetMapping("/query")
    @ApiOperation("查询")
    public Result queryByCondition(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "stuffName", required = false) String stuffName,
                                   @RequestParam(value = "stuffType", required = false) String stuffType,
                                   @RequestParam(value = "helpType", required = false) String helpType,
                                   @RequestParam(value = "helpTypeName", required = false) String helpTypeName,
                                   @RequestParam(value = "dutyPerson", required = false) String dutyPerson,
                                   @RequestParam(value = "dutyPhone", required = false) String dutyPhone,
                                   @RequestParam(value = "region", required = false) String region,
                                   @RequestParam(value = "planId", required = false) String planId,
                                   @RequestParam(value = "eventId", required = false) String eventId,
                                   @RequestParam(value = "isAll", required = false) String isAll,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(value = "userId", required = false) String userId,
                                   @RequestParam(value = "userName", required = false) String userName,
                                   HttpServletRequest request) {
        Result result = resourceStuffService.queryByCondition(id, stuffName, stuffType, helpType, helpTypeName, dutyPerson, dutyPhone, region, planId, eventId, isAll, page, pageSize);
        logService.insertLog("物资查询", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(result.getCode()), result.getMessage());
        return result;
    }

    @PostMapping("/operate")
    @ApiOperation("出入库")
    public Result operate(@RequestBody ResourceOperation resourceOperation) {
        return resourceStuffService.operate(resourceOperation);
    }

    @GetMapping("/query-stock")
    @ApiOperation("物资仓库(地址)查询")
    public Result queryStock(@RequestParam(value = "stuffName", required = false) String stockName,
                             @RequestParam(value = "eventId", required = false) String eventId,
                             @RequestParam(value = "planId", required = false) String planId,
                             @RequestParam(value = "isAll", required = false) String isAll,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return resourceStuffService.queryStock(stockName, eventId, planId, isAll, page, pageSize);
    }
}

