package com.znv.manage.controller.resource;


import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.RescueTeamInfo;
import com.znv.manage.service.LogService;
import com.znv.manage.service.impl.resource.TeamInfoServiceImpl;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "救援队伍")
@RequestMapping("/teamInfo")
public class TeamInfoController {

    @Resource
    LogService logService;

    @Autowired
    private TeamInfoServiceImpl teamInfoService;

    @ApiOperation(value = "救援队伍-级别-字典")
    @GetMapping(value = "/teamLevelDict")
    public Result teamLevelDict() {
        return new Result(RescueTeamInfo.LEVEL_DICT);
    }

    @ApiOperation(value = "救援队伍-事件-字典")
    @GetMapping(value = "/teamEventDict")
    public Result teamEventDict() {
        return new Result(RescueTeamInfo.EVENT_DICT);
    }

    @ApiOperation(value = "新增救援队伍信息")
    @PostMapping(value = "/insertTeam")
    public Result insertTeam(@RequestBody RescueTeamInfo rescueTeamInfo) {
        return teamInfoService.insertTeam(rescueTeamInfo);
    }

    @ApiOperation(value = "修改救援队伍信息")
    @PostMapping(value = "/updateTeam")
    public Result updateTeam(@RequestBody RescueTeamInfo rescueTeamInfo) {
        return teamInfoService.updateTeam(rescueTeamInfo);
    }

    @ApiOperation(value = "删除救援队伍信息")
    @GetMapping(value = "/deleteTeam")
    public Result deleteTeam(@RequestParam(value = "id", required = false) int id) {
        return teamInfoService.deleteTeam(id);
    }

    @ApiOperation(value = "删除救援队伍信息-批量")
    @GetMapping(value = "/batchDeleteTeam")
    public Result batchDeleteTeam(@RequestParam(value = "ids", required = false) String ids) {
        return teamInfoService.batchDeleteTeam(ids);
    }

    @ApiOperation("通过队伍id查询救援队伍")
    @GetMapping("queryRescueTeamInfoById")
    public Result queryRescueTeamInfoById(@RequestParam(value = "teamId", required = false) int teamId) {
        return teamInfoService.queryRescueTeamInfoById(teamId);
    }

    @ApiOperation("通过队伍id查询救援队伍-用户权限相关")
    @GetMapping("queryRescueTeamInfoByIdNew")
    public Result queryRescueTeamInfoByIdNew(@RequestParam(value = "teamId") int teamId) {
        return teamInfoService.queryRescueTeamInfoByIdNew(teamId);
    }

    @ApiOperation(value = "查询救援队伍信息")
    @GetMapping(value = "/queryRescueTeamInfo")
    public Result queryRescueTeamInfo(@RequestParam(value = "helpType", required = false)
                                              String helpType,
                                      @RequestParam(value = "helpTypeName", required = false)
                                              String helpTypeName,
                                      @RequestParam(value = "industry", required = false)
                                              String industry,
                                      @RequestParam(value = "area", required = false) String area,
                                      @RequestParam(value = "teamName", required = false) String teamName,
                                      @RequestParam(value = "teamType", required = false) String teamType,
                                      @RequestParam(value = "apType", required = false) String apType,
                                      @RequestParam(value = "storageLevel", required = false) String storageLevel,
                                      @RequestParam(value = "specialities", required = false) String specialities,
                                      @RequestParam(value = "userId", required = false) String userId,
                                      @RequestParam(value = "userLevel", required = false) String userLevel,
                                      @RequestParam(value = "planId", required = false) String planId,
                                      @RequestParam(value = "eventId", required = false) String eventId,
                                      @RequestParam(value = "isAll", required = false) String isAll,
                                      @RequestParam(value = "pageStart", required = false) Integer pageStart,
                                      @RequestParam(value = "pageLen", required = false) Integer pageLen,
                                      @RequestParam(value = "userName", required = false) String userName,
                                      HttpServletRequest request) {
        Result result = teamInfoService.queryRescueTeamInfo(null, helpType, helpTypeName, industry, area, teamName,
                teamType, apType, storageLevel, specialities, userId, userLevel, planId, eventId, isAll, pageStart, pageLen);
        logService.insertLog("事件节点查询", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(result.getCode()),result.getMessage());
        return result;
    }

    @ApiOperation(value = "救援队伍撒点")
    @GetMapping(value = "/queryTeamInfoList")
    public List<Map<String, Object>> queryTeamInfoList(@RequestParam(value = "helpType", required = false)
                                                               String helpType,
                                                       @RequestParam(value = "helpTypeName", required = false)
                                                               String helpTypeName,
                                                       @RequestParam(value = "industry", required = false)
                                                               String industry,
                                                       @RequestParam(value = "gpsx", required = false)
                                                               String gpsx,
                                                       @RequestParam(value = "gpsy", required = false)
                                                               String gpsy,
                                                       @RequestParam(value = "area", required = false) String area,
                                                       @RequestParam(value = "teamName", required = false) String teamName,
                                                       @RequestParam(value = "teamType", required = false) String teamType,
                                                       @RequestParam(value = "apType", required = false) String apType,
                                                       @RequestParam(value = "storageLevel", required = false) String storageLevel,
                                                       @RequestParam(value = "eventId", required = false) String eventId,
                                                       @RequestParam(value = "specialities", required = false) String specialities) {
//                                                      @RequestParam(value = "specialities",required = false) String specialities,
//                                                      @RequestParam(value = "pageStart", required = false) Integer pageStart,
//                                                      @RequestParam(value = "pageLen", required = false) Integer pageLen){
        return teamInfoService.queryTeamInfoList(null, helpType, helpTypeName, industry, gpsx, gpsy, area, teamName, teamType, apType, storageLevel, eventId, specialities);
//        return teamInfoService.queryTeamInfoList(null,helpType,helpTypeName,industry,gpsx,gpsy,area,teamName,teamType,apType,storageLevel,specialities,pageStart,pageLen);
    }

    @ApiOperation(value = "通过仓库查询对应物资")
    @GetMapping(value = "/queryStuffByStockId")
    public List<Map<String, Object>> queryTeamInfoStuff(@RequestParam(value = "id", required = false) String id,
                                                        @RequestParam(value = "companyId", required = false) String companyId,
                                                        @RequestParam(value = "model", required = false) String model,
                                                        @RequestParam(value = "brand", required = false) String brand,
                                                        @RequestParam(value = "stuffName", required = false) String stuffName,
                                                        @RequestParam(value = "stuffType", required = false) String stuffType,
                                                        @RequestParam(value = "helpType", required = false) String helpType) {
        return teamInfoService.queryTeamInfoStuff(id, companyId, model, brand, stuffName, stuffType, helpType);
    }

    @ApiOperation(value = "救援队伍分类统计")
    @GetMapping(value = "/queryTeamInfoCount")
    public Result queryTeamInfoCount() {
        return teamInfoService.queryTeamInfoCount();
    }

    @ApiOperation(value = "救援队伍擅长领域字典")
    @GetMapping(value = "/teamInfoMajorsDict")
    public Result teamInfoMajorsDict() {
        return teamInfoService.teamInfoMajorsDict();
    }

    @ApiOperation(value = "救援队伍专兼类型字典")
    @GetMapping(value = "/teamInfoFullPartTimeDict")
    public Result teamInfoFullPartTimeDict() {
        return teamInfoService.teamInfoFullPartTimeDict();
    }

    @ApiOperation(value = "避难场所")
    @GetMapping(value = "/shelterInfo")
    public List<Map<String, Object>> shelterInfo(
            @RequestParam(value = "gpsx", required = false)
                    String gpsx,
            @RequestParam(value = "gpsy", required = false)
                    String gpsy,
            @RequestParam(value = "area", required = false)
                    String area,
            @RequestParam(value = "name", required = false)
                    String name,
            @RequestParam(value = "shelterCategory", required = false)
                    String shelterCategory) {
        return teamInfoService.shelterInfo(gpsx, gpsy, area, name, shelterCategory);
    }

    @ApiOperation(value = "避难场所分类统计")
    @GetMapping(value = "/shelterInfoCount")
    public Result shelterInfoCount() {
        return teamInfoService.shelterInfoCount();
    }


    @ApiOperation(value = "应急力量分类列表")
    @GetMapping(value = "/emergencyPowerDictList")
    public List<Map> emergencyPowerDictList() {
        return teamInfoService.emergencyPowerDictList();
    }

    @ApiOperation(value = "应急力量")
    @GetMapping(value = "/emergencyPower")
    public Map<String, Object> emergencyPower(@RequestParam(value = "type", required = false) String type) {
        return teamInfoService.emergencyPower(type);
    }

    @ApiOperation(value = "企业")
    @GetMapping(value = "/company")
    public Map<String, Map<String, List<Map<String, Object>>>> company() {
        return teamInfoService.company();
    }

    @ApiOperation(value = "车队")
    @GetMapping(value = "/carTeam")
    public List<Map> carTeam(@RequestParam(value = "area", required = false)
                                     String area,
                             @RequestParam(value = "type", required = false)
                                     String type) {
        return teamInfoService.carTeam(area, type);
    }

    @ApiOperation(value = "车队分类统计")
    @GetMapping(value = "/carTeamCount")
    public Result carTeamCount() {
        return teamInfoService.carTeamCount();
    }

    @ApiOperation(value = "公路抢修物资")
    @GetMapping(value = "/emergencyFloodStuff")
    public List<Map> emergencyFloodStuff(@RequestParam(value = "area", required = false)
                                                 String area,
                                         @RequestParam(value = "name", required = false)
                                                 String name) {
        return teamInfoService.emergencyFloodStuff(area, name);
    }

    @ApiOperation(value = "公路抢修物资分类统计")
    @GetMapping(value = "/emergencyFloodStuffCount")
    public Result emergencyFloodStuffCount() {
        return teamInfoService.emergencyFloodStuffCount();
    }

    @ApiOperation(value = "防汛准备")
    @GetMapping(value = "/floodPerpare")
    public Map floodPerpare() {
        return teamInfoService.floodPerpare();
    }


    @ApiOperation(value = "指挥调度-应急队伍保障-查询救援队伍")
    @GetMapping(value = "/commandSelectTeam")
    public Result commandSelectTeam(@RequestParam(value = "commandId") String commandId,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return teamInfoService.commandSelectTeam(commandId, page, pageSize);
    }


    @ApiOperation(value = "导入Excel")
    @PostMapping(value = "/teamExcel")
    public Result teamExcel(@RequestBody MultipartFile file,
                            @RequestParam(value = "userId", required = false) String userId,
                            @RequestParam(value = "userName", required = false) String userName,
                            HttpServletRequest request) {
        logService.insertLog("导入救援队伍", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(teamInfoService.teamExcel(file).getCode()), teamInfoService.teamExcel(file).getMessage());
        return teamInfoService.teamExcel(file);
    }

    @ApiOperation(value = "导出Excel")
    @PostMapping(value = "/exportTeamExcel")
    public Result exportTeamExcel(@RequestParam(value = "ids", required = false) String ids,
                                  @RequestParam(value = "helpType", required = false)
                                          String helpType,
                                  @RequestParam(value = "helpTypeName", required = false)
                                          String helpTypeName,
                                  @RequestParam(value = "industry", required = false)
                                          String industry,
                                  @RequestParam(value = "area", required = false) String area,
                                  @RequestParam(value = "teamName", required = false) String teamName,
                                  @RequestParam(value = "teamType", required = false) String teamType,
                                  @RequestParam(value = "apType", required = false) String apType,
                                  @RequestParam(value = "storageLevel", required = false) String storageLevel,
                                  @RequestParam(value = "specialities", required = false) String specialities,
                                  @RequestParam(value = "userId", required = false) String userId,
                                  @RequestParam(value = "userLevel", required = false) String userLevel,
                                  @RequestParam(value = "pageStart", required = false) Integer pageStart,
                                  @RequestParam(value = "pageLen", required = false) Integer pageLen) {
        return teamInfoService.exportTeamExcel(ids, null, helpType, helpTypeName, industry, area, teamName, teamType, apType, storageLevel, specialities, userId, userLevel, pageStart, pageLen);
    }
}
