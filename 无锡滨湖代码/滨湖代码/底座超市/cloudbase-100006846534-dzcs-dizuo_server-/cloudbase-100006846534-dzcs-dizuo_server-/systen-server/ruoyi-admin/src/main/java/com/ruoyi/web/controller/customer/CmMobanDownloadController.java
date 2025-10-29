package com.ruoyi.web.controller.customer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.customer.domain.*;
import com.ruoyi.system.customer.service.*;
import com.ruoyi.system.customer.vo.AddAuthApplicationVo;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ICmAppExamineService;
import com.ruoyi.web.controller.customer.common.CsBaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "cmMobanDownload")
public class CmMobanDownloadController extends CsBaseController<IMobanDownloadService, CmMobanDownload, String> {

    private final ICmAppExamineService cmAppExamineService;
    private final ICmAppTypeService iCmAppTypeService;
    private final SysUserMapper sysUserMapper;
    private final RequestService requestService;

    @Autowired
    private HouseInfoService houseInfoService;
    @Autowired
    private IMobanDownloadService mobanDownloadService;


    public R addAuthApplication() {


        return null;
    }


    @Override
    public void setQueryWrapperForList(QueryWrapper<CmMobanDownload> wrapper, CmMobanDownload cmApp) {


    }





    @Override
    public void afterSave(CmMobanDownload cmMobanDownload) {

    }

    @Override
    public void beforeSave(CmMobanDownload cmMobanDownload) {

    }



    /**
     * 获取前十下载量
     * @param
     */
    @GetMapping("/gettop10Download")
    public R top10Download()   {
        List<Map<String, String>> counts=mobanDownloadService.top10Download();
        return R.ok(counts);
    }

    /**
     * 统计模块数量
     * @param
     */
    @GetMapping("/moduleCouts")
    public R moduleCouts()   {
        List<Map<String, String>> counts=houseInfoService.getModuleCouts();
        return R.ok(counts);
    }

    /**
     * 获取所有已上架应用
     * @param
     */
    @PostMapping("/allAppList")
    public R allAppList(@RequestBody CmAppParam param) throws Exception  {
        List<CmApp> lists=houseInfoService.getAllAppList(param);
        //CommonRes commonRes = requestService.applicationList();
        return R.ok(lists);
    }

    /**
     * 后台管理应用列表
     * @param
     */
    @PostMapping("/getAllAppListForAdmin")
    public R getAllAppListForAdmin(@RequestBody CmAppParam param) throws Exception  {
        List<CmApp> lists=houseInfoService.getAllAppListForAdmin(param);
       // CommonRes commonRes = requestService.applicationList();
        return R.ok(lists);
    }

    /**
     * 获取所有已上架应用
     * @param
     */
    @GetMapping("/getTuiJian")
    public R getTuiJian()   {
        List<CmApp> lists=houseInfoService.getTuiJian();
        return R.ok(lists);
    }

    /**
     * 获取一周内上架的应用
     * @param
     */
    @GetMapping("/getLastApp")
    public R getLastApp()   {
        List<CmApp> lists=houseInfoService.getLastApp();
        return R.ok(lists);
    }
}
