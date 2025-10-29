package com.znv.manage.controller.resource;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Law;
import com.znv.manage.service.LogService;
import com.znv.manage.service.resource.LawService;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(tags = "法律法规")
@RestController
@RequestMapping("Law")
public class LawController {

    @Autowired
    private LawService lawService;

    @Resource
    LogService logService;

    @GetMapping("/queryLawList")
    @ApiOperation("查询法律法规列表")
    public PageInfo<Law> queryLawList(@RequestParam(value = "lawName", required = false) String lawName,
                                      @RequestParam(value = "accidentType", required = false) String accidentType,
                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return lawService.queryLawList(lawName, accidentType, pageNum, pageSize);
    }

    @PostMapping("/deleteLaw")
    @ApiOperation("删除法律法规")
    public Result deleteLaw(@RequestParam(value = "ids", required = false) String ids,
                            @RequestParam(value = "userId", required = false) String userId,
                            @RequestParam(value = "userName", required = false) String userName,
                            HttpServletRequest request) {
        // Map<String, String> map = LogUtilsRequest.getLogingUserInfo(request);  // 未使用token，无法获取用户id，改为接口上传
        Result result = lawService.deleteLaw(ids);
        logService.insertLog("删除法律法规", userId, userName, CusAccessUtil.getIpAddress(request), new Date(),
                null, null, Integer.toString(result.getCode()), result.getMessage());
        return result;
    }

    @PostMapping("/insertSelective")
    @ApiOperation("新增法律法规")
    public Result insertSelective(@RequestBody Law law) {
        return lawService.insertSelective(law);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ApiOperation("修改法律法规")
    public Result updateByPrimaryKeySelective(@RequestBody Law law) {
        return lawService.updateByPrimaryKeySelective(law);
    }
}



