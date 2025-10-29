package com.znv.manage.controller;


import com.znv.manage.service.impl.AmbulanceServiceImpl;
import com.znv.manage.service.impl.MaterialServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "医疗物资")
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialServiceImpl materialService;

    @ApiOperation("医疗物资-单位撒点")
    @GetMapping("/queryMaterialUnitList")
    public List<Map<String, Object>> queryMaterialUnitList(){
        return materialService.queryMaterialUnitList();
    }

    @ApiOperation("医疗物资-物资信息")
    @GetMapping("/queryMaterialList")
    public List<Map<String, Object>> queryMaterialList(@RequestParam(value = "unitId")String unitId){
        return materialService.queryMaterialList(unitId);
    }

    @GetMapping("/syncMaterialUnit")
    public void syncMaterialUnit(){
        materialService.syncMaterialUnit();
    }

}
