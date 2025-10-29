package com.znv.manage.controller;

import com.znv.manage.bean.user.Department;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 部门管理
 * @author ：lilingling
 * @date ： 2020/03/04
 */

@Api(tags = "部门管理（废弃）")
@Controller
@Slf4j
public class DepartmentController {
    @Resource
    DepartmentService departmentService;

    @ApiOperation("查询部门信息")
    @RequestMapping(value = "/queryDepartmentInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result queryDepartment(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "precinctId", required = false) String precinctId
    ) {
        Result result = new Result();
        try {
            List<Map<String,String>> list = departmentService
                    .queryDepartment(id,department,type,address,precinctId);
            log.info("test");
            result.setData(list);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.toString());
        }
        return result;
    }

    @ApiOperation("新增部门信息")
    @RequestMapping(value = "/insertDepartment",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertDepartment(@RequestBody Department department,
                                               @RequestParam(value = "precinctId", required = false) String precinctId
                                               ) {
        ResultBean<String> ret = new ResultBean<>();
        if(department.getDepartment()==null||
                department.getEorder()==null){
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("department is null or eorder is null");
            log.error("department is null or eorder is null");

            return ret;
        }

        try {
            List<Map<String,String>> list = departmentService
                    .queryDepartmentByName(department.getDepartment(),null);
            if (list.size()>0){
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("部门名称已存在");
                return ret;
            }

            department.setPrecinctId(precinctId);
            departmentService.insertDepartment(department);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("编辑部门信息")
    @RequestMapping(value = "/updateDepartment",method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateDepartment(@RequestBody Department department,@RequestParam(value = "precinctId", required = false) String precinctId) {
        ResultBean<String> ret = new ResultBean<>();
        try {

            List<Map<String,String>> list = departmentService
                    .queryDepartmentByName(department.getDepartment(),null);
            if (list.size()>0 && !department.getId().equals(String.valueOf(list.get(0).get("id")))){
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("部门名称已存在");
                return ret;
            }

            department.setPrecinctId(precinctId);
            departmentService.updateDepartment(department);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("删除部门信息")
    @RequestMapping(value = "/deleteDepartment",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteDepartment(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "department", required = false) String department
    ) {
        ResultBean<String> ret = new ResultBean<>();

        try {
            departmentService.deleteDepartment(id, department);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }
}
