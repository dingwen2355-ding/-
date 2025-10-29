package com.znv.manage.controller;


import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.People;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Unit;
import com.znv.manage.common.bean.UnitTree;
import com.znv.manage.service.CommunicationService;
import com.znv.manage.service.impl.CommunicationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/communication")
@Api(tags = "通讯录")
public class CommunicationController {
    @Autowired
    private CommunicationServiceImpl communicationService;

    @ApiOperation(value = "查询通讯录所有信息")
    @GetMapping(value = "/baseInfo")
    public Result baseInfo(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "eventId", required = false) String eventId,
            @RequestParam(value = "unitId", required = false) String unitId,
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "pageStart", defaultValue = "1") Integer pageStart,
            @RequestParam(value = "pageLen", defaultValue = "20") Integer pageLen) {
        return communicationService.baseInfo(text, type, eventId, unitId, precinctId, pageStart, pageLen);
    }

    @ApiOperation(value = "查询区信息")
    @GetMapping(value = "/areaInfo")
    public List<Map<String, String>> areaInfo(@RequestParam(value = "precinctId", required = false) String precinctId,
                                              @RequestParam(value = "level", required = false) String level,
                                              @RequestParam(value = "topId", required = false) String topId
    ) {
        return communicationService.areaInfo(precinctId, level, topId);
    }

    @ApiOperation(value = "查询企业/部门信息")
    @GetMapping(value = "/unitInfo")
    public List<Map<String, String>> unitInfo(@RequestParam(value = "text", required = false) String text,
                                              @RequestParam(value = "type") String type,
                                              @RequestParam(value = "eventId", required = false) String eventId,
                                              @RequestParam(value = "precinctId", required = false) String precinctId) {
        return communicationService.unitInfo(text, type, eventId, precinctId);
    }

    @ApiOperation(value = "查询企业/部门信息")
    @GetMapping(value = "/unitInfoTree")
    public Result unitInfoTree(@RequestParam(value = "text", required = false) String text,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "precinctId", required = false) String precinctId) {
        return communicationService.unitInfo2(text, type, precinctId);
    }

    @ApiOperation(value = "修改通讯录排序")
    @PostMapping(value = "/communicationOrder")
    public void communicationOrder(@RequestBody JSONArray jsonArray) {
        communicationService.communicationOrder(jsonArray);
    }

    @ApiOperation(value = "执法人员撒点")
    @GetMapping(value = "/queryZFPeopleList")
    public List<Map<String, Object>> queryZFPeopleList(@RequestParam(value = "phone", required = false) String phone,
                                                       @RequestParam(value = "precinctName", required = false) String precinctName) {
        return communicationService.queryZFPeopleList(phone, precinctName);
    }

    @ApiOperation(value = "强制观摩token(弃用)")
    @GetMapping(value = "/queryTokenByPhone")
    public Map<String, String> queryTokenByPhone(@RequestParam(value = "phone", required = true) String phone) {
        return communicationService.queryTokenByPhone(phone);
    }

    @ApiOperation(value = "强制观摩token")
    @GetMapping(value = "/queryToken")
    public Map<String, String> queryToken() {
        return communicationService.queryToken();
    }

    @ApiOperation(value = "同步更新执法人员信息")
    @PostMapping(value = "/saveZFPeople")
    public void saveZFPeople() {
        communicationService.saveZFPeople();
    }

    @ApiOperation(value = "xxx")
    @GetMapping(value = "/xxxx")
    public void xxxx() {
        communicationService.aesPhone();
    }


    @ApiOperation(value = "查询部门列表")
    @GetMapping(value = "/queryUnitList")
    public List<Unit> queryUnitList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "precinctId", required = false) String precinctId) {
        if ("510100".equals(precinctId)) {
            precinctId = "";
        }
        return communicationService.queryUnitList(name, precinctId);
    }

    @ApiOperation(value = "删除部门")
    @PostMapping(value = "/deleteUnitById")
    public Result deleteUnitById(
            @RequestParam(value = "ids") String ids) {

        return communicationService.deleteUnitById(ids);
    }

    @ApiOperation(value = "新增/修改部门")
    @PostMapping(value = "/saveUnit")
    public Result saveUnit(@RequestBody Unit unit, @RequestParam(value = "precinctId", defaultValue = "510100") String precinctId) {
        return communicationService.saveUnit(unit, precinctId);
    }

    @ApiOperation(value = "批量录入部门")
    @PostMapping(value = "/uploadUnitExcel")
    public Result uploadUnitExcel(@RequestParam("file") MultipartFile file, @RequestParam(value = "precinctId", defaultValue = "510100") String precinctId) {
        return communicationService.uploadUnitExcel(file, precinctId);
    }


    @ApiOperation(value = "查询通讯录列表")
    @GetMapping(value = "/queryPeopleList")
    public List<People> queryPeopleList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "unitName", required = false) String unitName,
            @RequestParam(value = "precinctId", required = false) String precinctId) {
        if ("510100".equals(precinctId)) {
            precinctId = "";
        }
        return communicationService.queryPeopleList(name, unitName, precinctId);
    }

    @ApiOperation(value = "删除通讯录人员")
    @PostMapping(value = "/deletePeopleById")
    public Result deletePeopleById(
            @RequestParam(value = "ids") String ids) {

        return communicationService.deletePeopleById(ids);
    }

    @ApiOperation(value = "新增/修改通讯录人员")
    @PostMapping(value = "/savePeople")
    public Result savePeople(@RequestBody People people) {
        return communicationService.savePeople(people);
    }

    @ApiOperation(value = "批量录入通讯录人员")
    @PostMapping(value = "/uploadPeopleExcel")
    public Result uploadPeopleExcel(@RequestParam("file") MultipartFile file, @RequestParam(value = "precinctId", defaultValue = "510100") String precinctId) {
        return communicationService.uploadPeopleExcel(file, precinctId);
    }

    @ApiOperation(value = "事件相关属地单位")
    @GetMapping(value = "/queryPrecinctList")
    public List<Map<String, Object>> queryPrecinctList(
            @RequestParam(value = "precinctKind", required = false) String precinctKind,
            @RequestParam(value = "upPrecinctId", required = false) String upPrecinctId,
            @RequestParam(value = "precinctId", required = false) String precinctId) {
        return communicationService.queryPrecinctList(precinctKind, upPrecinctId, precinctId);
    }

}
