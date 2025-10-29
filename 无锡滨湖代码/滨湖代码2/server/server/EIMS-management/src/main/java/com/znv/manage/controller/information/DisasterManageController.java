package com.znv.manage.controller.information;


import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.DisasterBean;
import com.znv.manage.common.bean.information.GetDisasterBean;
import com.znv.manage.service.information.DisasterManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 灾情信息报送管理
 *
 * @author chd
 * @date 2022/6/7
 */
@RestController
@Api(tags = "灾情信息报送管理")
@Slf4j
public class DisasterManageController {

    @Resource
    DisasterManageService disasterManageService;

    /**
     * 新增灾情信息报送
     *
     * @param disasterBean
     * @return
     */
    @ApiOperation(value = "新增灾情信息报送")
    @PostMapping(value = "/add-disaster")
    public Result addDisaster(@RequestBody DisasterBean disasterBean) {
        return disasterManageService.addDisaster(disasterBean);
    }

    /**
     * 编辑灾情信息报送
     *
     * @param disasterBean
     * @return
     */
    @ApiOperation(value = "编辑灾情信息报送")
    @PostMapping(value = "/edit-disaster")
    public Result editDisaster(@RequestBody DisasterBean disasterBean) {
        return disasterManageService.editDisaster(disasterBean);
    }

    /**
     * 删除灾情信息报送
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除灾情信息报送")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "灾情信息报送id", dataType = "String", required = true)
    })
    @GetMapping(value = "/del-disaster")
    public Result delDisaster(@RequestParam String id) {
        return disasterManageService.delDisaster(id);
    }

    /**
     * 灾情信息报送
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "灾情信息报送")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "灾情信息报送id", dataType = "String", required = true)
    })
    @GetMapping(value = "/rep-disaster")
    public Result repDisaster(@RequestParam String id) {
        return disasterManageService.repDisaster(id);
    }

    /**
     * 查询灾情信息报送
     *
     * @param getDisasterBean
     * @return
     */
    @ApiOperation(value = "查询灾情信息报送")
    @PostMapping(value = "/get-disaster")
    public PageInfo<List<DisasterBean>> getDisaster(@RequestBody GetDisasterBean getDisasterBean) {
        return disasterManageService.getDisaster(getDisasterBean);
    }

    /**
     * 查询灾情信息报送详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询灾情信息报送详情")
    @GetMapping(value = "/get-disaster-data")
    public DisasterBean getDisasterData(@RequestParam String id) {
        return disasterManageService.getDisasterData(id);
    }
}
