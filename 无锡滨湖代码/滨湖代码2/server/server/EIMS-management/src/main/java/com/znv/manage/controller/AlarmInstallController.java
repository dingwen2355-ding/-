package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.IAlarmInstallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
@Slf4j
@RestController
@Api(tags = "告警规则接口")
@RequestMapping("alarmInstall")
public class AlarmInstallController {

    @Autowired
    private IAlarmInstallService alarmInstallService;

    /**
     * 告警设置查询
     * @return
     */
    @ApiOperation("告警设置查询")
    @GetMapping("/selAlarmInstall")
    public Result selAlarmInstall(){
        return alarmInstallService.selAlarmInstall();
    }

    /**
     * 修改告警设置
     * @return
     */
    @ApiOperation("修改告警设置")
    @PostMapping("/updateAlarmInstall")
    public Result updateAlarmInstall(@RequestParam(value = "id")Integer id,
                                     @RequestParam(value = "threshold",required = false)String threshold,
                                     @RequestParam(value = "rulesState",required = false)String rulesState,
                                     @RequestParam(value = "user",required = false)String user){
        return alarmInstallService.updateAlarmInstall(id,threshold, rulesState, user);
    }
}
