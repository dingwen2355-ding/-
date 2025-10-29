package com.znv.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.IAlarmInformService;
import com.znv.manage.task.AlarmTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = "告警通知接口")
@RequestMapping("alarmInform")
public class AlarmInformController {

    @Autowired
    private AlarmTask alarmTask;

    @Autowired
    private IAlarmInformService alarmInformService;

    /**
     * 消息通知查询
     * @return
     */
    @ApiOperation("消息通知查询")
    @GetMapping("/selAlarmInform")
    public Result selAlarmInform(@RequestParam(value = "alarmId",required = false) String alarmId,
                                 @RequestParam(value = "announcementType",required = false)String announcementType,
                                 @RequestParam(value = "content",required = false) String content,
                                 @RequestParam(value = "reportSource",required = false) String reportSource,
                                 @RequestParam(value = "isAccept",required = false) String isAccept,
                                 @RequestParam(value = "eventId",required = false) String eventId,
                                 @RequestParam(value = "startTime",required = false) String startTime,
                                 @RequestParam(value = "endTime",required = false) String endTime,
                                 @RequestParam(value = "userId",required = false) String userId,
                                 @RequestParam(value = "departmentId",required = false) String departmentId,
                                 @RequestParam(value = "pageStart",required = false) Integer pageStart,
                                 @RequestParam(value = "pageLen",required = false) Integer pageLen,
                                 @RequestParam(value = "isAll",defaultValue = "0")String isAll,
                                 HttpServletRequest request){
        return alarmInformService.selAlarmInform(alarmId,announcementType,content,isAccept,eventId,startTime,endTime,userId,departmentId,pageStart,pageLen,isAll,request,reportSource);
    }

    /**
     * 消息接收
     * @param alarmIds 告警编号，可批量用","间隔
     * @param isAccept 接受状态
     * @param remark 备注
     * @return
     */
    @ApiOperation("消息接收")
    @PostMapping(value = "update")
    public Result update(@RequestParam(value = "alarmIds")String alarmIds,
                         @RequestParam(value = "isAccept",required = false)String isAccept,
                         @RequestParam(value = "remark",required = false)String remark){
        return alarmInformService.update(alarmIds,isAccept,remark);
    }

    /**
     * 一键催办
     * @param jsonArray
    示例：
    [{
    "event_id": "WG202011251660211414571",
    "reportSource": "网格事件",
    "grid_name": "3702030250000400301",
    "deal_person_name": "张三",
    "infoSource": "上级派遣",
    "deadline_time": "2020-11-27 9:00:00",
    "deal_dept_id": "3254",
    "deal_person_id": "1575",
    "remark": "测试1"
    }, {
    "event_id": "WG202011251660211414577",
    "reportSource": "网格事件",
    "grid_name": "3702030250000400301",
    "deal_person_name": "王五",
    "infoSource": "上级派遣",
    "deadline_time": "2020-11-29 9:00:00",
    "deal_dept_id": "3254",
    "deal_person_id": "1575",
    "remark": "测试3"
    }]
     * @return
     */
    @ApiOperation("新增催办告警")
    @PostMapping("/installAlarmInform")
    public Result installAlarmInform(@RequestBody JSONArray jsonArray){
        return alarmInformService.installAlarmInform(jsonArray);
    }

    @ApiOperation("生成”即将超期“告警")
    @GetMapping("/generateAlarmData")
    public void generateAlarmData() {
        alarmTask.generateAlarmData();
    }

    @ApiOperation("生成”超期告警“告警")
    @GetMapping("/generateOverTimeAlarmData")
    public  void generateOverTimeAlarmData(){
        alarmTask.generateOverTimeAlarmData();
    }
}
