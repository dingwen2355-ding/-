package com.znv.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.bean.*;
import com.znv.manage.service.DutyManageService;
import com.znv.manage.service.LogService;
import com.znv.manage.utils.CusAccessUtil;
import com.znv.manage.utils.DateUtil;
import com.znv.manage.utils.ExcelUtil;
import com.znv.manage.utils.MultipartUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author ：ygr
 * @date ：Created in 2020-5-19
 */

@RestController
@Slf4j
public class DutyManageController {

    private static final String DUTY_TYPES = "工作日,双休日,国定假日";

    private static final String MODULE_TYPES_2 = "白班,夜班";
    private static final String MODULE_TYPES_3 = "早班,中班,晚班";
    private static final String MODULE_TYPES_1 = "全日班";

    @Autowired
    DutyManageService dutyManageService;

    @Resource
    LogService logService;

    @ApiOperation("查询值班人员")
    @GetMapping("/queryDutyPerson")
    public Result queryDutyPerson(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "phone",required = false) String phone,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService.queryDutyPerson(name,null,phone,precinctId);
            log.debug("查询值班人员成功");
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("新增值班人员")
    @PostMapping("/insertDutyPerson")
    public Result insertDutyPerson(
            @RequestBody DutyPerson dutyPerson,
            @RequestParam(value = "precinctId",required = false)String precinctId
            ){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService.queryDutyPerson(null,dutyPerson.getName(),null,precinctId);
            if (list.size()>0){
                result.setCode(1);
                result.setMessage("该名称已存在数据库中");
            }else {
                dutyPerson.setPrecinctId(precinctId);
                int i = dutyManageService.insertDutyPerson(dutyPerson);
            }

        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("删除值班人员")
    @PostMapping("/deleteDutyPerson")
    public Result deleteDutyPerson(
            @RequestParam("ids") String ids
    ){
        Result result = new Result();
        try{
            int i = dutyManageService.deleteDutyPerson(ids);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("修改值班人员")
    @PostMapping("/updateDutyPerson")
    public Result updateDutyPerson(
            @RequestBody DutyPerson dutyPerson,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            dutyPerson.setPrecinctId(precinctId);
            int i = dutyManageService.updateDutyPerson(dutyPerson);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("批量导入值班人员")
    @PostMapping("/insertDutyPersonByExcel")
    public Result insertDutyPersonByExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "precinctId",required = false)String precinctId

    ){
        Result result = new Result();
        if (!MultipartUtil.fileType(file)) {
            result.setCode(500);
            result.setData("上传文件格式错误");
            return result;
        }
        try{
            List<Map<String, Object>> excelList = ExcelUtil.resolveExcel(file,0,4);
            Map<String,Object> personMap = dutyManageService.queryDutyPersonFroMap(precinctId);
            List<DutyPerson> addList = new ArrayList<>();
            StringBuilder retError = new StringBuilder();
            for (int i = 0; i< excelList.size(); i++){
                StringBuilder error = new StringBuilder();
                DutyPerson dutyPerson = new DutyPerson();
                Map<String, Object> map = excelList.get(i);
                if ("".equals(map.get("姓名"))){
                    error.append("姓名不可能为空。");
                }
                if ("".equals(map.get("电话"))){
                    error.append("电话不可能为空。");
                }
                if ("".equals(map.get("单位"))){
                    error.append("单位不可能为空。");
                }
                if ("".equals(map.get("职务"))){
                    error.append("职务不可能为空。");
                }
                dutyPerson.setName(map.get("姓名")+"");
                dutyPerson.setPhone(map.get("电话")+"");
                dutyPerson.setDept(map.get("单位")+"");
                dutyPerson.setOccupation(map.get("职务")+"");
                dutyPerson.setPrecinctId(precinctId);

                if (personMap.get(dutyPerson.getName()) != null ){
                    error.append("该姓名数据库已存在。");
                }
                if ( error.length() > 0){
                    retError.append(i+1).append("行，").append(error);
                }else {
                    addList.add(dutyPerson);
                }
            }
            if (addList.size()>0){
                int i = dutyManageService.insertDutyPersonByExcel(addList);
            }
            if (retError.length()>0){
                result.setCode(1);
                result.setMessage(retError.toString());
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }



    @ApiOperation("查询值班模板")
    @GetMapping("/queryDutyModule")
    public Result queryDutyModule(
            @RequestParam(value = "dutyType",required = false) String dutyType,
            @RequestParam(value = "moduleName",required = false) String moduleName,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService.queryDutyModule(dutyType,moduleName,precinctId);
            log.debug("查询值班模板成功");
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("新增值班模板")
    @PostMapping("/insertDutyModule")
    public Result insertDutyModule(
            @RequestBody DutyModule dutyModule,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            dutyModule.setPrecinctId(precinctId);
            List<Map<String,Object>> list = dutyManageService
                    .queryDutyModule(dutyModule.getDutyType(),dutyModule.getModuleName(),precinctId);
            /**
             * 判断模板时间范围是否符合
             */
            String moduleTypes = "";
            if (MODULE_TYPES_2.contains(dutyModule.getModuleName())){
                moduleTypes = MODULE_TYPES_2;
            }
            if (MODULE_TYPES_3.contains(dutyModule.getModuleName())){
                moduleTypes = MODULE_TYPES_3;
            }
            List<Map<String,Object>> timeList = dutyManageService.queryDutyModuleByTime(
                    0,
                    moduleTypes,
                    dutyModule.getDutyType(),
                    dutyModule.getStartTime(),
                    dutyModule.getEndTime(),
                    dutyModule.getIsEndToday(),
                    precinctId
            );
            if (timeList.size()>0){
                result.setCode(1);
                result.setMessage("模板之间存在时间冲突");
                return result;
            }
            if (list.size()>0){
                result.setCode(1);
                result.setMessage("该类型下已存在该模板名称");
            }else {
                int i = dutyManageService.insertDutyModule(dutyModule);
            }

        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("删除值班模板")
    @PostMapping("/deleteDutyModule")
    public Result deleteDutyModule(
            @RequestParam("ids") String ids
    ){
        Result result = new Result();
        try{
            int i = dutyManageService.deleteDutyModule(ids);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("修改值班模板")
    @PostMapping("/updateDutyModule")
    public Result updateDutyModule(
            @RequestBody DutyModule dutyModule,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{

            /**
             * 判断模板时间范围是否符合
             */
            String moduleTypes = "";
            if (MODULE_TYPES_2.contains(dutyModule.getModuleName())){
                moduleTypes = MODULE_TYPES_2;
            }
            if (MODULE_TYPES_3.contains(dutyModule.getModuleName())){
                moduleTypes = MODULE_TYPES_3;
            }
            List<Map<String,Object>> timeList = dutyManageService.queryDutyModuleByTime(
                    dutyModule.getModuleId(),
                    moduleTypes,
                    dutyModule.getDutyType(),
                    dutyModule.getStartTime(),
                    dutyModule.getEndTime(),
                    dutyModule.getIsEndToday(),
                    precinctId
            );
            if (timeList.size()>0){
                result.setCode(1);
                result.setMessage("模板之间存在时间冲突");
                return result;
            }

            dutyModule.setPrecinctId(precinctId);
            int i = dutyManageService.updateDutyModule(dutyModule);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("查询值班角色")
    @GetMapping("/queryDutyRole")
    public Result queryDutyRole(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService.queryDutyRole(name,precinctId);
            log.debug("查询值班角色成功");
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("新增值班角色")
    @PostMapping("/insertDutyRole")
    public Result insertDutyRole(
            @RequestParam("roleName") String roleName,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService.queryDutyRole(roleName,precinctId);
            if (list.size()>0){
                result.setCode(1);
                result.setMessage("已存在该角色");
            }else {
                int i = dutyManageService.insertDutyRole(roleName,precinctId);
            }

        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("删除值班角色")
    @PostMapping("/deleteDutyRole")
    public Result deleteDutyRole(
            @RequestParam("ids") String ids
    ){
        Result result = new Result();
        try{
            int i = dutyManageService.deleteDutyRole(ids);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("修改值班角色")
    @PostMapping("/updateDutyRole")
    public Result updateDutyRole(
            @RequestParam("id") String id,
            @RequestParam("roleName") String roleName
    ){
        Result result = new Result();
        try{
            int i = dutyManageService.updateDutyRole(id,roleName);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }



    @ApiOperation("查询值班信息")
    @GetMapping("/queryDutyInfo")
    public Result queryDutyInfo(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestParam(value = "dutyType",required = false) String dutyType,
            @RequestParam(value = "moduleId",required = false) String moduleId,
            @RequestParam(value = "precinctId",required = false)String precinctId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "userName", required = false) String userName,
            HttpServletRequest request){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService
                    .queryDutyInfo(name,startDate,endDate,dutyType,moduleId,precinctId);
            log.debug("查询值班信息成功");
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        logService.insertLog("值班信息查询", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(result.getCode()), result.getMessage());
        return result;
    }

    @ApiOperation("根据日期，排班模板，拍班类型-查询值班人员")
    @GetMapping("/queryDutyPersonByDate")
    public Result queryDutyPersonByDate(
            @RequestParam(value = "dutyDate",required = true) String dutyDate,
            @RequestParam(value = "dutyType",required = true) String dutyType,
            @RequestParam(value = "moduleId",required = true) String moduleId,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = dutyManageService
                    .queryDutyPersonByDate(dutyDate,dutyType,moduleId,precinctId);
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation("修改值班信息")
    @PostMapping("/updateDutyInfo")
    public Result updateDutyInfo(
            @RequestBody DutyTablePro dutyTablePro,
            @RequestParam(value = "precinctId",required = false)String precinctId
            ){
        Result result = new Result();
        try{
            String moduleName = dutyManageService.queryModulName(dutyTablePro.getDutyModule());
            List<PersonRole> list = dutyTablePro.getPersonList();
            for(PersonRole p : list){
                List<Map<String,Object>> temp = dutyManageService.queryData(
                        precinctId,
                        p.getPersonId(),
                        dutyTablePro.getDutyDate()
                        );
                if (temp.size()>0){
                    for (Map<String,Object> t : temp){
                        String moduleTypes = "";
                        if (MODULE_TYPES_1.contains(t.get("moduleName")+"")){
                            moduleTypes = MODULE_TYPES_1;
                        }
                        if (MODULE_TYPES_2.contains(t.get("moduleName")+"")){
                            moduleTypes = MODULE_TYPES_2;
                        }
                        if (MODULE_TYPES_3.contains(t.get("moduleName")+"")){
                            moduleTypes = MODULE_TYPES_3;
                        }
                        if ("其他".equals(t.get("moduleName")+"")){
                            moduleTypes = "其他";
                        }
                        if (!t.get("moduleId").equals(dutyTablePro.getDutyModule())){
                            if (!moduleTypes.contains(moduleName)){
                                result.setCode(1);
                                result.setMessage("该日期" + t.get("personName") + "已经有排班信息");
                                return result;
                            }
                        }
                    }

                }
            }
            dutyTablePro.setPrecinctId(precinctId);
            int i = dutyManageService.updateDutyInfo(dutyTablePro);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 值班日期、排班类型、值班模板、姓名、值班角色。
     * 导入时需要进行校验：
     * 包括值班人员未找到、值班日期不正确、排班类型未找到、值班模板未找到、值班角色未找到、排班类型中无该值班模板
     * @param file
     * @return
     */
    @ApiOperation("批量导入值班人员")
    @PostMapping("/insertDutyInfoByExcel")
    public Result insertDutyInfoByExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "precinctId",required = false)String precinctId
    ){
        Result result = new Result();
        if (!MultipartUtil.fileType(file)) {
            result.setCode(500);
            result.setData("上传文件格式错误");
            return result;
        }
        try{
            List<Map<String, Object>> excelList = ExcelUtil.resolveExcel(file,0,5);
            Map<String, String> personMap = dutyManageService.queryPersonFroMap(precinctId);

            Map<String,String> roleMap = dutyManageService.queryRoleFroMap(precinctId);

            List<DutyTable> addList = new ArrayList<>();
            List<String> errorList  = new ArrayList<>();
            for (int i = 0; i< excelList.size(); i++){
                DutyTable dutyTable = new DutyTable();
                String error = "";
                Map<String, Object> map = excelList.get(i);
                dutyTable.setDutyDate(DateUtil.changeDate(map.get("值班日期")+""));
                dutyTable.setDutyType(map.get("排班类型")+"");
                dutyTable.setDutyModule(map.get("值班模板")+"");
                dutyTable.setDutyPerson(map.get("姓名")+"");
                dutyTable.setDutyRole(map.get("值班角色")+"");
                dutyTable.setPrecinctId(precinctId);


                Map<String,String> moduleMap = dutyManageService.queryModuleFroMap(dutyTable.getDutyType(),precinctId);

                if (!DUTY_TYPES.contains(dutyTable.getDutyType())){
                    error = error + "排班类型未找到。";
                }
                if (dutyTable.getDutyDate() == null){
                    error = error + "值班日期未找到。";
                }
                if (personMap.get(dutyTable.getDutyPerson()) == null){
                    error = error + "值班人员未找到。";
                }else {
                    String a = JSONObject.toJSONString(personMap.get(dutyTable.getDutyPerson()));
                    JSONObject jsonObject = JSON.parseObject(a);
                    dutyTable.setDutyPerson(jsonObject.getString("id"));
                }

                if (roleMap.get(dutyTable.getDutyRole()) == null){
                    error = error + "值班角色未找到。";
                }else {
                    String a = JSONObject.toJSONString(roleMap.get(dutyTable.getDutyRole()));
                    JSONObject jsonObject = JSON.parseObject(a);
                    dutyTable.setDutyRole(jsonObject.getString("id"));
                }

                if (moduleMap.get(dutyTable.getDutyModule()) == null){
                    error = error + "排班类型中无该值班模板。";
                }else {
                    String a = JSONObject.toJSONString(moduleMap.get(dutyTable.getDutyModule()));
                    JSONObject jsonObject = JSON.parseObject(a);
                    dutyTable.setDutyModule(jsonObject.getString("id"));
                }

                //去重
                List<Map<String,Object>> mapList = dutyManageService.queryDutyInfoById(
                        precinctId,dutyTable.getDutyDate(),
                        dutyTable.getDutyModule(),dutyTable.getDutyPerson());
                if (mapList.size()>0){
                    error = error + "该行数据已存在。";
                }

                if (!StringUtils.isEmpty(error)){
                    errorList.add("第"+ (i+1) + "行，数据有问题："+error);
                }else {
                    addList.add(dutyTable);
                }
            }
            if (addList.size()>0){
                int i = dutyManageService.insertDutyInfo(addList);
            }
            if (errorList.size()>0){
                result.setCode(1);
                result.setMessage(errorList.toString());
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

}
