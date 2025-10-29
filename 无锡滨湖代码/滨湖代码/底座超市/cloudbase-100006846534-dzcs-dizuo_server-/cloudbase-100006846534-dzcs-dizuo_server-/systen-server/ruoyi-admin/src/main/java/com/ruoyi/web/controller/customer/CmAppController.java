package com.ruoyi.web.controller.customer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.customer.domain.CmApp;
import com.ruoyi.system.customer.domain.CmAppExamine;
import com.ruoyi.system.customer.domain.CmAppParam;
import com.ruoyi.system.customer.domain.CmAppType;
import com.ruoyi.system.customer.service.HouseInfoService;
import com.ruoyi.system.customer.service.ICmAppService;
import com.ruoyi.system.customer.service.ICmAppTypeService;
import com.ruoyi.system.customer.service.RequestService;
import com.ruoyi.system.customer.vo.AddAuthApplicationVo;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ICmAppExamineService;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.system.service.ISysUserService;
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
@RequestMapping(value = "cmApp")
public class CmAppController extends CsBaseController<ICmAppService, CmApp, String> {

    private final ICmAppExamineService cmAppExamineService;
    private final ICmAppTypeService iCmAppTypeService;
    private final SysUserMapper sysUserMapper;
    private final RequestService requestService;

    @Autowired
    private HouseInfoService houseInfoService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysNoticeService sysNoticeService;
    public R addAuthApplication() {


        return null;
    }

    @GetMapping("/getDetail/{id}")
    public R<CmApp> list(@PathVariable(value = "id") String id) {
        CmApp cmApp = houseInfoService.getCmAppDetail(id);
        if (cmApp!=null){
            return R.ok(cmApp);
        }
        return R.fail("该数据已被删除或不存在");
    }

    @Override
    public void setQueryWrapperForList(QueryWrapper<CmApp> wrapper, CmApp cmApp) {

        Map<String, Object> params = cmApp.getParams();
        if (StringUtils.isNotBlank(cmApp.getAppName())) {
            wrapper.like("app_name", cmApp.getAppName());
        }

        if (StringUtils.isNotBlank(cmApp.getAppType())) {
            wrapper.eq("app_type", cmApp.getAppType());
        }
        if (StringUtils.isNotBlank(cmApp.getAppModule())) {
            wrapper.eq("app_module", cmApp.getAppModule());
        }
        if (cmApp.getRecommendFlag() != null) {
            wrapper.eq("recommend_flag", cmApp.getRecommendFlag());
        }
        if (cmApp.getStatus() != null) {
            if (cmApp.getStatus() == 0) {
                wrapper.eq("status", 0);
            } else if (cmApp.getStatus() == 3) {
                wrapper.eq("status", 3);
            } else {
                wrapper.eq("status", 1);
            }
        } else {
            wrapper.in("status", Stream.of(0, 3).collect(Collectors.toList()));
        }

        if (params != null) {
            // 0:上架 1:待审核 2:驳回 3:下架
            if (params.containsKey("replayStatus")) {
                wrapper.eq("status", params.get("replayStatus"));
            }

        }
        wrapper.orderByDesc("create_time");
    }


    @RequestMapping("/replayList")
    public R replayList(@RequestBody CmApp t, Page<CmApp> page) throws ParseException {
        QueryWrapper<CmApp> wrapper = new QueryWrapper<>();

        if (!adminFlag()) {
            wrapper.eq("create_by", SecurityUtils.getUserId().toString());
        }
        if (t.getStatus() != null) {
            log.error("112233");
            wrapper.eq("status", t.getStatus());
        }
        if (StringUtils.isNotEmpty(t.getAppName())) {
            wrapper.eq("app_name", t.getAppName());
        }
        if (StringUtils.isNotBlank(t.getAppType())) {
            log.error("112244");
            wrapper.eq("app_type", t.getAppType());
        }
        wrapper.orderByDesc("create_time");
        Map<String, Object> params = t.getParams();
        log.error("1122555params;" + params);


        String startDate = null;
        String endDate = null;

        if (!CollectionUtils.isEmpty(params)) {
            if (params.containsKey("startDate") && StringUtils.isNotBlank((startDate = params.get("startDate").toString()))) {
                wrapper.ge("create_time", DateUtils.parseDate(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            if (params.containsKey("endDate") && StringUtils.isNotBlank((endDate = params.get("endDate").toString()))) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(DateUtils.parseDate(endDate, "yyyy-MM-dd"));
                instance.add(Calendar.DAY_OF_YEAR, 1);
                wrapper.lt("create_time", instance.getTime());
            }
            if (params.containsKey("own") && StringUtils.equals(params.get("own").toString(), "1")) {
               // logger.error("own: {}", SecurityUtils.getUserId());
                wrapper.eq("create_by", SecurityUtils.getUserId().toString());
            }

        }
        log.error("112266params;" + params);

        PageDomain pageDomain = TableSupport.buildPageRequest();
        page.setSize(pageDomain.getPageSize());
        page.setCurrent(pageDomain.getPageNum());
        log.error("112266page;" + page);
        page = getService().page(page, wrapper);
        List<CmApp> records = page.getRecords();
        log.error("112277records;" + records);

        if (!CollectionUtils.isEmpty(records)) {

            List<Long> createByUidList = records.stream().map(item -> Long.valueOf(item.getCreateBy())).collect(Collectors.toList());
            QueryWrapper<SysUser> wrapper_ = new QueryWrapper<>();
            wrapper_.select("user_id", "user_name","nick_name");
            wrapper_.in("user_id", createByUidList);
            // 获取用户
            List<SysUser> sysUsers = sysUserMapper.selectList(wrapper_);
            Map<Long, String> mapUidUname = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getNickName));
            List<CmAppExamine> cmAppExamines = cmAppExamineService.list(new QueryWrapper<CmAppExamine>().in("cm_app_id", records.stream().map(CmApp::getId).collect(Collectors.toList())));
            log.error("1122788cmAppExamines;" + cmAppExamines);
            Map<String, CmAppExamine> maoAppIdAndObj = new HashMap<>();
            if (!CollectionUtils.isEmpty(cmAppExamines)) {
                for (CmAppExamine cmAppExamine : cmAppExamines) {
                    if (StringUtils.isNotEmpty(cmAppExamine.getCmAppId())) {
                        maoAppIdAndObj.put(cmAppExamine.getCmAppId(), cmAppExamine);
                    }
                }
                //maoAppIdAndObj = cmAppExamines.stream().collect(Collectors.toMap(CmAppExamine::getCmAppId, item -> item));
            } else {
                maoAppIdAndObj = new HashMap<>();
            }
            Map<String, CmAppExamine> finalMaoAppIdAndObj = maoAppIdAndObj;
            records.forEach(item -> {
                Map<String, Object> obj = new HashMap<>();
                if (mapUidUname.containsKey(Long.valueOf(item.getCreateBy()))) {
                    obj.put("createByUname", mapUidUname.get(Long.valueOf(item.getCreateBy())));
                }
                if (finalMaoAppIdAndObj.containsKey(item.getId())) {
                    obj.put("examine", finalMaoAppIdAndObj.get(item.getId()));
                }
                item.setParams(obj);
            });
        }
        return R.ok(page);
    }

    @RequestMapping("/replayListAll")
    public R replayListAll(@RequestBody CmApp t, Page<CmApp> page) throws ParseException {
        QueryWrapper<CmApp> wrapper = new QueryWrapper<>();

//        if (!adminFlag()) {
//            wrapper.eq("create_by", SecurityUtils.getUserId().toString());
//        }
        if (t.getStatus() != null) {
            wrapper.eq("status", t.getStatus());
        }
        if (StringUtils.isNotEmpty(t.getAppName())) {
            wrapper.eq("app_name", t.getAppName());
        }
        if (StringUtils.isNotBlank(t.getAppType())) {
            wrapper.eq("app_type", t.getAppType());
        }
        wrapper.orderByDesc("create_time");
        Map<String, Object> params = t.getParams();


        String startDate = null;
        String endDate = null;

        if (!CollectionUtils.isEmpty(params)) {
            if (params.containsKey("startDate") && StringUtils.isNotBlank((startDate = params.get("startDate").toString()))) {
                wrapper.ge("create_time", DateUtils.parseDate(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            if (params.containsKey("endDate") && StringUtils.isNotBlank((endDate = params.get("endDate").toString()))) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(DateUtils.parseDate(endDate, "yyyy-MM-dd"));
                instance.add(Calendar.DAY_OF_YEAR, 1);
                wrapper.lt("create_time", instance);
            }
//            if (params.containsKey("own") && StringUtils.equals(params.get("own").toString(), "1")) {
//                logger.error("own: {}", SecurityUtils.getUserId());
//                wrapper.eq("create_by", SecurityUtils.getUserId().toString());
//            }

        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        page.setSize(pageDomain.getPageSize());
        page.setCurrent(pageDomain.getPageNum());
//        log.error("112266page;" + page);
        page = getService().page(page, wrapper);
        List<CmApp> records = page.getRecords();
//        log.error("112277records;" + records);
        if (!CollectionUtils.isEmpty(records)) {

            List<Long> createByUidList = records.stream().map(item -> Long.valueOf(item.getCreateBy())).collect(Collectors.toList());
            QueryWrapper<SysUser> wrapper_ = new QueryWrapper<>();
            wrapper_.select("user_id", "user_name");
            wrapper_.in("user_id", createByUidList);
            // 获取用户
            List<SysUser> sysUsers = sysUserMapper.selectList(wrapper_);
            Map<Long, String> mapUidUname = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName));
            List<CmAppExamine> cmAppExamines = cmAppExamineService.list(new QueryWrapper<CmAppExamine>().in("cm_app_id", records.stream().map(CmApp::getId).collect(Collectors.toList())));
//            log.error("1122788cmAppExamines;" + cmAppExamines);
             Map<String, CmAppExamine> maoAppIdAndObj = new HashMap<>();
            if (!CollectionUtils.isEmpty(cmAppExamines)) {
                for (CmAppExamine cmAppExamine : cmAppExamines) {
                    if (StringUtils.isNotEmpty(cmAppExamine.getCmAppId())) {
                        maoAppIdAndObj.put(cmAppExamine.getCmAppId(), cmAppExamine);
                    }
                }
                //maoAppIdAndObj = cmAppExamines.stream().collect(Collectors.toMap(CmAppExamine::getCmAppId, item -> item));
            } else {
                maoAppIdAndObj = new HashMap<>();
            }
            Map<String, CmAppExamine> finalMaoAppIdAndObj = maoAppIdAndObj;
            records.forEach(item -> {
                Map<String, Object> obj = new HashMap<>();
                if (mapUidUname.containsKey(Long.valueOf(item.getCreateBy()))) {
                    obj.put("createByUname", mapUidUname.get(Long.valueOf(item.getCreateBy())));
                }
                if (finalMaoAppIdAndObj.containsKey(item.getId())) {
                    obj.put("examine", finalMaoAppIdAndObj.get(item.getId()));
                }
                item.setParams(obj);
            });
        }
        return R.ok(page);
    }


    @Override
    public void afterSave(CmApp cmApp) {
        SysUser sysUser=null;
        if (adminFlag()) {
//            cmApp.setStatus(0);
            getService().updateById(cmApp);
            CmAppExamine cmAppExamine = new CmAppExamine();
            cmAppExamine.setCmAppId(cmApp.getId());
            cmAppExamine.setExamineType(cmApp.getStatus().toString());
            cmAppExamine.setRemark(cmApp.getStatus() == 0 ? "管理员上架" : "管理员下架");
            cmAppExamineService.save(cmAppExamine);
        }else {
             sysUser = userService.selectUserById(getLongFromString(cmApp.getCreateBy()));
            sysNoticeService.insertNotice(SysNotice.getCmAppMessageDetail(SendMessageEnum.cmAppCreate_new,cmApp,sysUser, cmApp.getCreateTime()));
            cmApp.setCreateBy("1");
            SysNotice cmAppMessageDetail = SysNotice.getCmAppMessageDetail(SendMessageEnum.cmAppCreate_new, cmApp, sysUser, cmApp.getCreateTime());
            cmAppMessageDetail.setNoticeTitle(cmAppMessageDetail.getNoticeTitle().replace("申请","审批"));
            sysNoticeService.insertNotice(cmAppMessageDetail);
        }


    }
    @Override
    public void beforeSave(CmApp cmApp) {
        String now = DateUtils.parseDateToStr("YYYYMMdd", new Date());
        QueryWrapper<CmApp> wrapper = new QueryWrapper<>();
        wrapper.eq("to_char(create_time,' ')", now);
        wrapper.last(" limit 1");
        wrapper.orderByDesc("app_serial_no");
        CmApp one = getService().getOne(wrapper);
        String no = null;
        if (one == null) {
            no = now + "0001";
        } else {
            no = (Long.parseLong(one.getAppSerialNo()) + 1) + "";
        }
        cmApp.setAppSerialNo(no);
        if (adminFlag()) {
            if (cmApp.getStatus() == null) {
                cmApp.setStatus(0);
            }
            cmApp.setApplicationReason("管理员添加");
        } else {
            cmApp.setStatus(1);
        }
    }

    @PostMapping("/updateStatus")
    public R<?> updateStatus(@RequestBody CmApp tmp) {
        String id = tmp.getId();
        String remark = tmp.getRemark();
        Integer status = tmp.getStatus();
        ICmAppService service = getService();
        CmApp cmApp = service.getById(id);
        if (!Objects.equals(cmApp.getStatus(), status)) {
            if ((status == 0 || status == 2)) {
                CmAppExamine cmAppExamine = new CmAppExamine();
                cmAppExamine.setCmAppId(id);
                cmAppExamine.setExamineType(status.toString());
                cmAppExamine.setRemark(remark);
                cmAppExamineService.save(cmAppExamine);
            }
            cmApp.setUpdateTime(new Date());
            cmApp.setStatus(status);
            service.updateById(cmApp);
            SysUser sysUser = userService.selectUserById(getLongFromString(cmApp.getCreateBy()));
            switch (status){
                case 0 :  {
                    sysNoticeService.insertNotice(SysNotice.getCmAppMessageDetail(SendMessageEnum.cmAppSuccess,cmApp,sysUser,cmApp.getUpdateTime()));
                    break;
                }
                case 2 : {
                    sysNoticeService.insertNotice(SysNotice.getCmAppMessageDetail(SendMessageEnum.cmAppFail, cmApp, sysUser,cmApp.getUpdateTime()));
                    break;
                }
                case 4 :{
                    sysNoticeService.insertNotice(
                            SysNotice.getCmAppMessageDetail(SendMessageEnum.cmAppWithdraw_new, cmApp,sysUser,cmApp.getUpdateTime())
                    );

                    cmApp.setCreateBy("1");
                    SysNotice cmAppMessageDetail = SysNotice.getCmAppMessageDetail(SendMessageEnum.cmAppWithdraw_new, cmApp, sysUser, cmApp.getUpdateTime());
                    cmAppMessageDetail.setNoticeTitle(cmAppMessageDetail.getNoticeTitle().replace("申请","审批"));
                    sysNoticeService.insertNotice(
                            cmAppMessageDetail
                    );
                }

            }
        } else {
            return R.fail(false, "该应用已有同样操作");
        }
        return R.ok(true);
    }

    @GetMapping("/certification")
    public R<?> certification(@RequestParam(value = "id") String id) {
       /* if (!adminFlag()) {
            return R.fail("该用户没有权限操作,请联系管理员");
        }*/
        CmApp cmApp = getService().getById(id);
        AddAuthApplicationVo addAuthApplicationVo = new AddAuthApplicationVo();

        try {
            CmAppTransToAddAuthApplicationVo(cmApp, addAuthApplicationVo);
            CommonRes commonRes = requestService.addAuthApplication(addAuthApplicationVo);
            if (commonRes.getCode() == 200) {
                Object data = commonRes.getData();
                log.error("data:{}", data);
                getService().updateCasAppidById(id, data);
            } else {
                return R.fail("认证失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return R.ok(true);
    }


    private void CmAppTransToAddAuthApplicationVo(CmApp cmApp, AddAuthApplicationVo addAuthApplicationVo) throws Exception {
        addAuthApplicationVo.setId(cmApp.getCasAppId());
        /*CmAppType cmAppType = iCmAppTypeService.getById(cmApp.getAppType());
        if (cmAppType == null) {
            throw new Exception("该类型已被删除了!");
        }
        if (StringUtils.isBlank(cmAppType.getCasAppType())) {
            throw new Exception("该类型未于同意身份认证应用类型绑定!");
        }*/
        addAuthApplicationVo.setName(cmApp.getAppName());
//        addAuthApplicationVo.setCategoryId(Long.valueOf(cmAppType.getCasAppType()));
        setFile(cmApp, addAuthApplicationVo);
        addAuthApplicationVo.setDescription(cmApp.getRemark());
        addAuthApplicationVo.setDeptId(Long.valueOf(cmApp.getDept()));
        addAuthApplicationVo.setUrl(cmApp.getAppLink());
        addAuthApplicationVo.setIsGround(cmApp.getStatus() == 0 ? 1 : 0);
        addAuthApplicationVo.setIsRecommend(cmApp.getRecommendFlag() == 0 ? 1 : 0);
        addAuthApplicationVo.setIsSendSms(0);
        SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(cmApp.getCreateBy()));
        if (sysUser != null) {
            addAuthApplicationVo.setApplicant(sysUser.getUserName());
        }
        addAuthApplicationVo.setReason(cmApp.getApplicationReason());
    }

    // 设置文件
    public void setFile(CmApp cmApp, AddAuthApplicationVo addAuthApplicationVo) {
        String avatarPath = RuoYiConfig.getAvatarPath();
        {
            // 图标
            String appPic = cmApp.getAppPic();
            // 文件
            String substring = appPic.substring(appPic.indexOf("/avatar") + 7);
            log.error("fanhuidizhisubstring:" + substring);
            JSONObject jsonObject = uploadCas(avatarPath.concat(substring));
            log.error("fanhuidizhi:" + jsonObject);
            if (jsonObject != null) {
                addAuthApplicationVo.setIconId(jsonObject.getString("id"));
                addAuthApplicationVo.setIcon(jsonObject.getString("fileUrl"));
                log.error("fanhuidizhifile:" + jsonObject.getString("fileUrl"));
            }

        }
        {
            // 应用截图
            String appScreenshot = cmApp.getAppScreenshot();
            List<String> appScreenshotList = JSONArray.parseArray(appScreenshot, String.class);
            List<String> logoIds = new ArrayList<>();
            List<String> logs = new ArrayList<>();
            appScreenshotList.forEach(item -> {
                JSONObject jsonObject = uploadCas(avatarPath.concat(item.substring(item.indexOf("/avatar") + 7)));
                if (jsonObject != null) {
                    logoIds.add(jsonObject.getString("id"));
                    logs.add(jsonObject.getString("fileUrl"));
                }
            });
            if (!CollectionUtils.isEmpty(logoIds)) {
                addAuthApplicationVo.setLogoId(String.join(",", logoIds));
            }
            if (!CollectionUtils.isEmpty(logs)) {
                addAuthApplicationVo.setLogo(String.join(",", logs));
            }
        }
    }

    public JSONObject uploadCas(String filePath) {

        JSONObject jsonObject = null;
        // 应用图标
        try {
            CommonRes commonRes = requestService.uploadFile(filePath);
            if (commonRes.getCode() != 200) {
                logger.error("蓝创文件上传接口异常..appPic:图标上传异常 {}", commonRes);
            }
            Object data = commonRes.getData();
            jsonObject = JSONObject.parseObject(JSONObject.toJSONString(data));
        } catch (IOException e) {
            logger.error("appPic:图标上传异常 {}", e);
            return null;
        } catch (Exception e) {
            logger.error("蓝创文件上传接口异常..appPic:图标上传异常 {}", e);
            return null;
        }
        return jsonObject;
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
    public JSONObject allAppList(@RequestBody CmAppParam param) throws Exception  {
        List<CmApp> lists=houseInfoService.getAllAppList(param);
        //申请量
        CommonRes shenqing = requestService.getApplyForStatistics();
        if(shenqing.getData()!=null){
            JSONArray arrays = JSONArray.from(shenqing.getData());
            for (Object array : arrays) {
                JSONObject data = JSONObject.from(array);
                String appSecret = data.getString("id");
                if(StringUtils.isNotEmpty(appSecret)){
                    for (CmApp list : lists) {
                        if(appSecret.equals(list.getCasAppId())){
                            list.setShengqingliang(data.getString("applyForNum"));
                            break;
                        }
                    }
                }
            }
        }
        //访问量
        CommonRes fangwen = requestService.getAccessStatistics();
        if(fangwen.getData()!=null){
            JSONArray arrays = JSONArray.from(fangwen.getData());
            for (Object array : arrays) {
                JSONObject data = JSONObject.from(array);
                String appSecret = data.getString("id");
                if(StringUtils.isNotEmpty(appSecret)){
                    for (CmApp list : lists) {
                        if(appSecret.equals(list.getCasAppId())){
                            list.setFangwenliang(data.getString("viewNum"));
                            break;
                        }
                    }
                }
            }
        }
        R<List<CmApp>> ok = R.ok(lists);
        JSONObject parse = JSONObject.parseObject(JSONObject.toJSONString(ok), JSONObject.class);
        parse.put("shenqing",shenqing);
        parse.put("fangwen",fangwen);
        return parse;
    }

    /**
     * 后台管理应用列表
     * @param
     */
    @PostMapping("/getAllAppListForAdmin")
    public R getAllAppListForAdmin(@RequestBody CmAppParam param) throws Exception  {
        List<CmApp> lists=houseInfoService.getAllAppListForAdmin(param);
        CommonRes commonRes = requestService.applicationList();
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


    public static void main(String[] args) {
        String str = "[\"http://2.21.137.206:11511/profile/avatar/2023/09/21/xitongguanli-xitongrizhiguanli@2x_20230825A017_20230921165104A001.png\"]";

        List<String> objects = JSONArray.parseArray(str, String.class);
        System.out.println(objects.size());
    }

    private   Long getLongFromString(String num){
        return   StringUtils.isNumeric(num)?Long.parseLong(num):1L;
    }
}
