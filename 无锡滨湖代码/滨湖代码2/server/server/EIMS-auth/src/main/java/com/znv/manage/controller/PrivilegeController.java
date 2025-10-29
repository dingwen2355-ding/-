package com.znv.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.PrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lilingling
 * @ClassName: PrivilegeController
 * @Description: 权限配置
 * @date 20200303
 */
@RestController
@Slf4j
@Api(tags = "资源树管理")
public class PrivilegeController {
    @Resource
    PrivilegeService privilegeService;

    /**
     * 查询居委小区楼号资源树
     * @param
     * @return
     */
    @ApiOperation(value = "查询居委小区楼号资源树")
    @GetMapping("/getCommitteeVilageTree")
    public Object getCommitteeVilageTree(
            @RequestParam(value = "userId",required = true) String userId
    ) {
        Result result = new Result();
        try{
            JSONArray list = privilegeService.getCommitteeVilageTree(userId);
            result.setMessage("查询居委小区楼号资源树成功");
            result.setData(list);
        }catch (Exception e){
            log.error(e.getMessage());
            result.setMessage("查询居委小区楼号资源树异常");
            result.setCode(1);
        }
        return result;
    }

    /**
     * 查询用户表资源树
     * @param
     * @return
     */
    @ApiOperation(value = "查询用户资源树信息")
    @GetMapping("/getUserPricinctTree")
    public Object getUserPricinctTree(
            @RequestParam(value = "userId",required = true) String userId) {
        Result result = new Result();
        try{

            List<Map<String, Object>>  list = privilegeService.getUserPricinctTree(userId);
            List<Map<String, Object>> listPrecinct=new ArrayList<>();
            List<Map<String, Object>> ret=new ArrayList<>();
            if(list.size()!=0){
                for(int i=0;i<list.size();i++){
                    Map<String, Object> map = new HashMap<String, Object>();
                    String precinctId=list.get(i).get("precinctTreeId").toString();
                    if(!"".equals(precinctId)){
                        listPrecinct=privilegeService.getPrecinctInfo(precinctId);
                    }
                    map.put("userId", list.get(i).get("userId"));
                    map.put("userName", list.get(i).get("userName"));
                    map.put("listPrecinct", listPrecinct);
                    ret.add(map);
                }
            }
            result.setMessage("查询用户表资源树成功");
            result.setData(ret);
        }catch (Exception e){
            log.error(e.getMessage());
            result.setMessage("查询用户表资源树异常");
            result.setCode(1);
        }

        return result;
    }


    /**
     * 查询居委小区楼号资源树
     * @param
     * @return
     */
    @ApiOperation(value = "查询用户所属街镇")
    @GetMapping("/getUserStreetId")
    public Object getUserStreetId(
            @RequestParam(value = "userId",required = true) String userId
    ) {
        Result result = new Result();
        try{
            String streetId = privilegeService.getUserStreetId(userId);
            result.setMessage("查询用户所属街镇成功");
            result.setData(streetId);
        }catch (Exception e){
            log.error(e.getMessage());
            result.setMessage("查询用户所属街镇异常");
            result.setCode(1);
        }
        return result;
    }

}
