package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.InstructionImplement;
import com.znv.manage.common.bean.LeaderInstruction;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.InstructionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 领导指示相关接口
 */
@Api(tags = "领导指示相关接口")
@RestController
@RequestMapping("leaderInstruction")
public class InstructionController {

    @Autowired
    InstructionService instructionService;

    @ApiOperation("领导指示内容落实情况")
    @GetMapping("instructionImplement")
    public Result getLeaderInstructionInplemention(@RequestParam("instructionId") String instructionId){

        return instructionService.getLeaderInstructionInplemention(instructionId);
    }

    @ApiOperation("领导指示内容")
    @GetMapping("instructionContent")
    public Result getLeaderInstruction(@RequestParam("eventId") String eventId,
                                       @RequestParam(value = "status", required = false) String status){
//        return instructionService.getLeaderInstruction(eventId);
        return instructionService.getLeaderInstructionAndImplemention(eventId, status);
    }

//    @ApiOperation("领导指示")
//    @GetMapping("instructionInfo")
//    public Result getLeaderInstructionDetail(@RequestParam("eventId") String eventId){
//
//        return instructionService.getLeaderInstructionDetail(eventId);
//    }

    @ApiOperation("领导指示内容落实情况")
    @GetMapping("instructionImplement2")
    public Result getLeaderInstructionInplemention2(@RequestParam("instructionId") String instructionId){

        return instructionService.getLeaderInstructionInplemention2(instructionId);
    }


    @ApiOperation("插入领导指示内容")
    @PostMapping("insertInstruction")
    public Result insertLeaderInstruction(@RequestBody LeaderInstruction leaderInstruction){

        return instructionService.insertLeaderInstruction(leaderInstruction);
    }

    @ApiOperation("更新领导指示内容")
    @PostMapping("updateInstruction")
    public Result updateLeaderInstruction(@RequestBody LeaderInstruction leaderInstruction){
        return instructionService.updateLeaderInstruction(leaderInstruction);
    }

    @ApiOperation("插入领导指示落实情况")
    @PostMapping("insertInstructionImplement")
    public Result insertInstructionImplemention(@RequestBody InstructionImplement instructionImplement){
        return instructionService.insertInstructionImplemention(instructionImplement);
    }

    @ApiOperation("更新领导指示落实情况")
    @PostMapping("updateInstructionImplement")
    public Result updateInstructionImplemention(@RequestBody InstructionImplement instructionImplement){
        return instructionService.updateInstructionImplemention(instructionImplement);
    }


    @ApiOperation("更新/插入领导指示和落实情况")
    @PostMapping("instructionAndImplement")
    public Result updateInstructionImplement(@RequestBody JSONObject jsonObject){

        return instructionService.updateInstructionImplement(jsonObject);
    }

    @ApiOperation("删除领导落实情况")
    @PostMapping("deleteInstructionImplement")
    public void deleteInstructionImplement(@RequestParam("id") String id){
        instructionService.deleteInstructionImplement(id);
    }


    @ApiOperation("提交领导落实内容")
    @PostMapping("updateLeaderInstructionImplementNew")
    public Result updateLeaderInstructionImplementNew(@RequestBody JSONObject json){
        return instructionService.updateLeaderInstructionImplementNew(json);
    }

    @ApiOperation("提交领导指示内容")
    @PostMapping("updateLeaderInstructionNew")
    public Result updateLeaderInstructionNew(@RequestBody JSONObject json){
        return instructionService.updateLeaderInstructionNew(json);
    }

}
