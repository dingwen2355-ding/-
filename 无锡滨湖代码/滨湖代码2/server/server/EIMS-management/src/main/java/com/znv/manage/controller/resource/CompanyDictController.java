package com.znv.manage.controller.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.resource.CompanyDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监管企业基本信息
 */
@Api(tags = "监管企业字典信息")
@RestController
@RequestMapping("companyDict")
public class CompanyDictController {

    @Autowired
    CompanyDictService companyDictService;

    @ApiOperation("查询所在辖区")
    @GetMapping("queryRegion")
    public Result queryRegion() {
        Result result = companyDictService.queryRegion();
        return result;
    }

    @ApiOperation("查询企业类型")
    @GetMapping("queryCompanyType")
    public Result queryCompanyType() {
        Result result = companyDictService.queryCompanyType();
        return result;
    }


    @ApiOperation("查询企业状态")
    @GetMapping("queryCompanyState")
    public Result queryCompanyState() {
        return companyDictService.queryCompanyState();
    }

    @ApiOperation("查询标准化级别")
    @GetMapping("queryStandLevel")
    public Result queryStandLevel() {
        return companyDictService.queryStandLevel();
    }

    @ApiOperation("查询所属行业")
    @GetMapping("queryIndustry")
    public Result queryIndustry() {
        return companyDictService.queryIndustry();
    }


}
