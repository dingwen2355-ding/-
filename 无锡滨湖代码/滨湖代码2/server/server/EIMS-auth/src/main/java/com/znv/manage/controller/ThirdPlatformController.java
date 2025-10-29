package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ThirdPlatformEntity;
import com.znv.manage.service.ThirdPlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * @author SunJiaHao
 * @date 2022-08-16
 * @Description 第三方系统平台Controller
 */
@Api(tags = "第三方管理")
@RestController
@RequestMapping("/third-platform")
public class ThirdPlatformController {

    @Autowired
    private ThirdPlatformService thirdPlatformService;

    /**
     * 新增第三方平台
     * @param thirdPlatformEntity ThirdPlatformEntity
     * @return Result
     */
    @ApiOperation("新增第三方平台")
    @PostMapping("/add")
    public Result add(@RequestBody ThirdPlatformEntity thirdPlatformEntity) {
        return thirdPlatformService.addThirdPlatform(thirdPlatformEntity);
    }

    /**
     * 删除第三方平台
     * @param ids 英文逗号分隔的多个id
     * @return Result
     */
    @ApiOperation("删除第三方平台")
    @DeleteMapping("/delete")
    public Result deleteById(@RequestParam("ids") String ids) {
        return thirdPlatformService.deleteThirdPlatform(ids);
    }

    /**
     * 更新第三方平台（必须含id）
     * @param thirdPlatformEntity ThirdPlatformEntity
     * @return Result
     */
    @ApiOperation("更新第三方平台")
    @PutMapping("/update")
    public Result updateById(@RequestBody ThirdPlatformEntity thirdPlatformEntity) {
        return thirdPlatformService.updateThirdPlatformById(thirdPlatformEntity);
    }

    /**
     * 分页查询第三方平台列表
     * @param pageNum 页码，不传该参数或不传 pageSize，查询全部
     * @param pageSize 页容量，不传该参数或不传 pageNum，查询全部
     * @return Result
     */
    @ApiOperation("分页查询第三方平台列表")
    @GetMapping("/query-by-page")
    public Result queryByPage(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        // 检索条件封装进 paramMap
        Map<String, Object> paramMap = Collections.emptyMap();
        return thirdPlatformService.queryThirdPlatformList(paramMap, pageNum, pageSize);
    }

}
