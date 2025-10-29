package com.ruoyi.binHuServer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.ServerUrlCon;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.MapUtils;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.customer.domain.CmApp;
import com.ruoyi.system.customer.domain.CmAppParam;
import com.ruoyi.system.customer.service.AbilityService;
import com.ruoyi.system.customer.service.HouseInfoService;
import com.ruoyi.system.customer.service.RequestService;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/dsf")
public class DsfController {

    private final RequestService requestService;
    private final AbilityService abilityService;
    private final HouseInfoService houseInfoService;
    private final Map<String, String> appTypeMap;
    private final Map<String, String> appModuleMap;
    private final Map<String, String> appSourceMap;
    @Autowired
    private ISysDictTypeService dictTypeService;

    //平台访问量
    @GetMapping("/getVisitorsTotal")
    public CommonRes getVisitorTotal() {
        CommonRes commonRes;
        try {
            commonRes = requestService.baseSupermarket();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("底座超市异常");
            return commonRes;
        }
        return commonRes;
    }

    //用户注册量
    @GetMapping("/getUserTotal")
    public CommonRes getUserTotal() {
        CommonRes commonRes;
        try {
            commonRes = abilityService.getUserTotal();
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("查询用户注册总数");
            return commonRes;
        }
        return commonRes;
    }

    //根据条件获取应用列表
    @GetMapping("/getAppList")
    public CommonRes getAppList(@RequestParam(required = false) String module, @RequestParam(required = false) String source, @RequestParam(required = false) String type) {

        try {
            module = module != null ? MapUtils.getValueOrThrow(appModuleMap,module): "";
            source = source != null ? MapUtils.getValueOrThrow(appSourceMap,source): "";
            type = type != null ? MapUtils.getValueOrThrow(appTypeMap,type): "";
        } catch (Exception e) {
            return new CommonRes() {{
                setCode(400);
                setMessage("条件不符合规范，请检查");
            }};
        }
        CmAppParam cmAppParam = new CmAppParam() {{
            setShangjiatime("0");
        }};
        cmAppParam.setAppModule(module);
        cmAppParam.setSource(source);
        cmAppParam.setAppType(type);
        List<CmApp> lists = houseInfoService.getAllAppList(cmAppParam);
        JSONObject jsonObject = new JSONObject() {{
            put("total", lists.size());
            put("appList", lists);
        }};
        return new CommonRes() {{
            setData(jsonObject);
            setCode(200);
            setMessage("根据条件查询上架应用");
        }};
    }

    /**
     * 获取应用访问量
     * token	是	string	token
     * type	是	number	类型，1-全部，2-月度
     * month	是	string	月度，比如2023-06，当查询全部时可不传
     *
     * @return
     */
    @GetMapping(value = "/applicationActivation")
    public CommonRes applicationActivation(@RequestParam(defaultValue = "1") Integer type, @RequestParam(required = false) String month) {

        CommonRes commonRes;
        try {
            commonRes = requestService.applicationActivation(type, month);
        } catch (Exception e) {
            commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取应用活跃度异常");
            return commonRes;
        }

        return commonRes;
    }

    /**
     * 应用权限申请数量
     *
     * @return
     */
    @GetMapping(value = "/getApplyForStatistics")
    public CommonRes getApplyForStatistics() {
        try {
            return requestService.getApplyForStatistics();
        } catch (Exception e) {
            CommonRes commonRes = new CommonRes();
            commonRes.setCode(500);
            commonRes.setMessage("获取应用权限申请量异常");
            return commonRes;
        }
    }


}
