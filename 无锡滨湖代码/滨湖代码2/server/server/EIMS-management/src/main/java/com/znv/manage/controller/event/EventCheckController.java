package com.znv.manage.controller.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventCheck;
import com.znv.manage.common.bean.event.EventCheckFeedback;
import com.znv.manage.service.LogService;
import com.znv.manage.service.event.EventCheckService;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * TCfgEventCheck表控制层
 *
 * @author 0049003788
 * @since 2023-05-18 15:14:35
 */
@RestController
@RequestMapping("eventCheck")
@Api(tags = "事件核查下发与反馈")
public class EventCheckController {
    @Resource
    private EventCheckService eventCheckService;

    @Resource
    LogService logService;

    @PostMapping("/add-check")
    @ApiOperation("新增核查下发")
    public Result add(@RequestBody EventCheck eventCheck) {
        return eventCheckService.insertCheck(eventCheck);
    }

    @PostMapping("/edit-check")
    @ApiOperation("修改核查下发")
    public Result update(@RequestBody EventCheck eventCheck,
                         @RequestParam(value = "userId", required = false) String userId,
                         @RequestParam(value = "userName", required = false) String userName,
                         HttpServletRequest request) {
        // Map<String, String> map = LogUtilsRequest.getLogingUserInfo(request);  // 未使用token，无法获取用户id，改为接口上传
        Result result = eventCheckService.updateCheck(eventCheck);
        logService.insertLog("修改核查下发", userId, userName, CusAccessUtil.getIpAddress(request), new Date(),
                null, null, Integer.toString(result.getCode()), result.getMessage());
        return result;
    }

    @GetMapping("/del-check")
    @ApiOperation("删除核查下发")
    public Result deleteCheckById(@RequestParam(value = "id") Integer id,
                                  @RequestParam(value = "userId", required = false) String userId,
                                  @RequestParam(value = "userName", required = false) String userName,
                                  HttpServletRequest request) {
        // Map<String, String> map = LogUtilsRequest.getLogingUserInfo(request);  // 未使用token，无法获取用户id，改为接口上传
        Result result = eventCheckService.deleteCheckById(id);
        logService.insertLog("删除核查下发", userId, userName, CusAccessUtil.getIpAddress(request), new Date(),
                null, null, Integer.toString(result.getCode()), result.getMessage());
        return result;
    }

    @GetMapping("/query-check")
    @ApiOperation("查询核查下发")
    public Result queryCheckByCondition(@RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "eventId", required = false) Integer eventId,
                                        @RequestParam(value = "checkPersonName", required = false) String checkPersonName,
                                        @RequestParam(value = "checkUserId", required = false) Integer checkUserId,
                                        @RequestParam(value = "requirement", required = false) String requirement,
                                        @RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return eventCheckService.queryCheckByCondition(id, eventId, checkPersonName, checkUserId, requirement, page, pageSize);
    }

    @PostMapping("/add-feedback")
    @ApiOperation("新增核查反馈")
    public Result add(@RequestBody EventCheckFeedback eventCheckFeedback) {
        return eventCheckService.insertFeedback(eventCheckFeedback);
    }

    @PostMapping("/edit-feedback")
    @ApiOperation("修改核查反馈")
    public Result update(@RequestBody EventCheckFeedback eventCheckFeedback) {
        return eventCheckService.updateFeedback(eventCheckFeedback);
    }

    @GetMapping("/del-feedback")
    @ApiOperation("删除核查反馈")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return eventCheckService.deleteFeedbackById(id);
    }

    @GetMapping("/query-feedback")
    @ApiOperation("查询核查反馈")
    public Result queryByCondition(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "checkId", required = false) Integer checkId,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return eventCheckService.queryFeedbackByCondition(id, checkId, page, pageSize);
    }

}

