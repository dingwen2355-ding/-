package com.znv.manage.controller.resource;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Specification;
import com.znv.manage.service.resource.SpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "标准规范")
@RestController
@RequestMapping("Specification")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;




    @GetMapping("/querySpecificationList")
    @ApiOperation("查询标准规范列表")
    public PageInfo<Specification> querySpecificationList(@RequestParam(value = "fileName", required = false) String fileName,
                                                          @RequestParam(value = "preparationUnit", required = false) String preparationUnit,
                                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return specificationService.querySpecificationList(fileName, preparationUnit, pageNum, pageSize);
    }

    @PostMapping("/deleteByPrimaryKey")
    @ApiOperation("删除标准规范")
    public Result deleteByPrimaryKey(@RequestParam(value = "ids", required = false) String ids) {
        return specificationService.deleteByPrimaryKey(ids);
    }

    @PostMapping("/insertSelective")
    @ApiOperation("新增标准规范")
    public Result insertSelective(@RequestParam(value = "specification") String data,
                                  @RequestParam(value = "files", required = false) MultipartFile files) {
        Specification specification = JSONObject.parseObject(data,Specification.class);
        return specificationService.insertSelective(specification,files);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ApiOperation("修改标准规范")
    public Result updateByPrimaryKeySelective(@RequestParam(value = "specification") String data,
                                              @RequestParam(value = "files", required = false) MultipartFile files) {
        Specification specification = JSONObject.parseObject(data,Specification.class);
        return specificationService.updateByPrimaryKeySelective(specification,files);
    }
}
