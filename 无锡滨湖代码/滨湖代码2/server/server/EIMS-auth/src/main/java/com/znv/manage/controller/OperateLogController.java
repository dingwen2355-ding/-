package com.znv.manage.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.OperateLog;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.CusAccessUtil;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.service.OperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 操作日志类
 *
 * @author ：Yang GuanRong
 * @date ：Created in 2019/11/15
 */
@Api(tags = "操作日志类")
@RestController
@Slf4j
public class OperateLogController {

    @Autowired
    OperateLogService operateLogService;

//    private static String precinctId = null;

    @ApiOperation("新增日志")
    @PostMapping("/insertOperateLog")
    public Result insertOperateLog(
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestBody OperateLog operateLog,
            HttpServletRequest request
    ){
        Result result = new Result();
        if (precinctId == null){
            precinctId = operateLogService.queryPrecinctId();
        }
        try{
            String clientIp = CusAccessUtil.getIpAddress(request);
            String operateTime = DateUtils.getStringDate();
            operateLog.setClientIp(clientIp);
            operateLog.setOperateTime(operateTime);
            operateLog.setPrecinctId(precinctId);
            operateLogService.insertOperateLog(operateLog);

        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @ApiOperation("查询日志")
    @GetMapping("/queryOperateLog")
    public Result queryOperateLog(
            @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(value = "userId",required = false) String userId,
            @RequestParam(value = "username",required = false) String username,
            @RequestParam(value ="beginTime",required = false) String beginTime,
            @RequestParam(value ="endTime",required = false) String endTime,
            @RequestParam(value ="operateModule",required = false) String operateModule,
            @RequestParam(value ="operateDetail",required = false) String operateDetail
    ){
        Result result = new Result();
        List<Map<String,Object>> list = new ArrayList<>();
        try{
            Page page = PageHelper.startPage(pageNum, pageSize);
            list = operateLogService
                    .queryOperateLog(userId,username,beginTime,endTime,operateModule,operateDetail);
            PageInfo pageInfo = new PageInfo(page);
            log.debug("查询日志数据成功");
            result.setData(pageInfo);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }
}
