package com.ruoyi.web.controller.CmAbility;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.customer.domain.*;
import com.ruoyi.system.customer.service.*;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ICmAbilityExamineService;
import com.ruoyi.system.service.ICmAppExamineService;
import com.ruoyi.system.service.ISysNoticeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/cmAbility")
@AllArgsConstructor
public class CmAbilityController
{
    private static final Logger log = LoggerFactory.getLogger(CmAbilityController.class);

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private  RequestService requestService;

    @Autowired
    private HouseInfoService houseInfoService;

    @Autowired
    private ICmAbilityService cmAbilityService;

    @Autowired
    private ICmAbilityExamine2Service cmAbilityExamineService;

    @Autowired
    private  SysUserMapper sysUserMapper;

    /**
     * 插入
     */
    @PostMapping("/insert")
    public R add(@RequestBody CmAbility cmAbility)
    {
        int a=abilityService.insert(cmAbility);
        if(a>0){
            CmAbilityExamine cmAbilityExamine = new CmAbilityExamine();
            cmAbilityExamine.setId(ObjectUtil.isNotEmpty(cmAbility.getId())?cmAbility.getId():UUID.randomUUID().toString());
            cmAbilityExamine.setExamineType("1");
            abilityService.save(cmAbilityExamine);
            return R.ok("新增成功");
        }
        return R.fail("新增失败");
    }
    /**
     * 更新
     */
    @PostMapping("/update")
    public R update(@RequestBody CmAbility cmAbility)
    {
        if(StringUtils.isNotEmpty(cmAbility.getId())){
            CmAbility info=abilityService.getAbilityById(cmAbility.getId());
            if(info!=null){
                int a =abilityService.update(cmAbility);
                if(a>0){
                    return R.ok("修改成功");
                }
            }
        }
        return R.fail("修改成功");
    }

    /**
     * 个人中心·能力申请
     */
    @PostMapping("/centerAbility")
    public R centerAbility(Page<CmAbility> page)
    {
        List<CmAbility> info=abilityService.centerAbility(page);
        List<CmAbility> infototal=abilityService.centerAbilitytotal();
        page.setRecords(info);

        if (!CollectionUtils.isEmpty(info)) {
            List<Long> createByUidList = info.stream().map(item -> Long.valueOf(item.getCreateBy())).collect(Collectors.toList());
            QueryWrapper<SysUser> wrapper_ = new QueryWrapper<>();
            wrapper_.select("user_id", "user_name","nick_name");
            wrapper_.in("user_id", createByUidList);
            // 获取用户
            List<SysUser> sysUsers = sysUserMapper.selectList(wrapper_);
            Map<Long, String> mapUidUname = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getNickName));
            List<CmAbilityExamine> cmAbilityExamines = cmAbilityExamineService.list(new QueryWrapper<CmAbilityExamine>().in("cm_ability_id", info.stream().map(CmAbility::getId).collect(Collectors.toList())).orderByDesc("create_time"));

            Map<String, CmAbilityExamine> maoAppIdAndObj = new HashMap<>();
            if (!CollectionUtils.isEmpty(cmAbilityExamines)) {
                for (CmAbilityExamine cmAbilityExamine : cmAbilityExamines) {
                    if (StringUtils.isNotEmpty(cmAbilityExamine.getCmAbilityId())) {
                        maoAppIdAndObj.put(cmAbilityExamine.getCmAbilityId(), cmAbilityExamine);
                    }
                }
                //maoAppIdAndObj = cmAppExamines.stream().collect(Collectors.toMap(CmAppExamine::getCmAppId, item -> item));
            } else {
                maoAppIdAndObj = new HashMap<>();
            }
            Map<String, CmAbilityExamine> finalMaoAppIdAndObj = maoAppIdAndObj;
            info.forEach(item -> {
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

        if(info!=null){
            page.setTotal(infototal.size());

        }else{
            page.setTotal(0);
        }

        return R.ok(page);
    }

    /**
     * 后台管理·能力列表
     */
    @PostMapping("/getAllAbility")
    public R getAllAbility(@RequestBody CmAbility cmAbility,Page<CmAbility> page)
    {
        List<CmAbility> infototal=abilityService.getAllAbility(cmAbility);
        List<CmAbility> info=abilityService.getAllAbilityForPage(cmAbility,page);
        page.setRecords(info);
        if(infototal!=null){
            page.setTotal(infototal.size());
        }
        return R.ok(page);
    }

    /**
     * 后台首页审批总是
     */
    @GetMapping("/getShouYeShenpiCount")
    public R getShouYeShenpiCount() throws Exception
    {
        List<Map<String, Integer>> lists=abilityService.getShouYeShenpiCount();
        CommonRes commonRes = requestService.getHomePageAnalysis();
        if(commonRes.getData()!=null){
            JSONObject jsonObject = JSONObject.from(commonRes.getData());
            JSONArray echarts = JSONArray.parseArray(jsonObject.getString("echarts"));
          //  JSONObject o = JSONObject.fromObject(object);

            int total=0;
            for (Object echart : echarts) {
                JSONObject o = JSONObject.from(echart);
                total=total+o.getIntValue("num");
            }
            lists.get(0).put("shenqingshiyong",total);
        }
        return R.ok(lists);
    }

    /**
     * 入驻申请占比
     */
    @GetMapping("/getRuZhufenleiZhanBi")
    public R getRuZhuShenQingZhanBi()
    {
        List<Map<String, String>> lists=abilityService.getAppfenleiCount();
        return R.ok(lists);
    }

    /**
     * 入驻申请占比
     */
    @GetMapping("/getRuZhulaiyuanZhanBi")
    public R getRuZhulaiyuanZhanBi()
    {
        List<Map<String, String>> lists=abilityService.getRuZhulaiyuanZhanBi();
        return R.ok(lists);
    }

    /**
     * 入驻部门占比
     * @return
     */
    @GetMapping("/getRuZhuDeptZhanBi")
    public R getRuZhuDeptZhanBi() {
        List<Map<String,String>> lists = null;
        try {
           lists = abilityService.getRuZhuDeptZhanBi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(lists);
    }


    /**
     * 得到用户注册数量
     */
    @GetMapping("/getUserTotal")
    public R getUserTotal() {
        CommonRes commonRes = null;
        try {
            commonRes = abilityService.getUserTotal();
            return R.ok(commonRes.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }




    /**
     * 申请量统计
     */
    @GetMapping("/getShenqingliangtongji")
    public R getShenqingliangtongji() throws Exception {
        List<CmApp> lists=houseInfoService.getAllAppListForAdmin(null);
        Map<String,Integer> map=new HashMap<>();
        int zujian=0;
        int pingtai=0;
        int gongju=0;
        CommonRes commonRes = requestService.getApplyForStatistics();
        if(commonRes.getData()!=null){
            JSONArray arrays = JSONArray.from(commonRes.getData());
            for (Object array : arrays) {
                JSONObject data = JSONObject.from(array);
                String appSecret = data.getString("id");
                if(StringUtils.isNotEmpty(appSecret)){
                    for (CmApp list : lists) {
                        if(appSecret.equals(list.getCasAppId())){
                            if(StringUtils.isNotEmpty(list.getAppModule())){
                                if("1669301825683091458".equals(list.getAppModule())){
                                    gongju=gongju+data.getIntValue("applyForNum");
                                }else if("1669301899511230465".equals(list.getAppModule())){
                                    zujian=zujian+data.getIntValue("applyForNum");
                                }else if("1669301968486559746".equals(list.getAppModule())){
                                    pingtai=pingtai+data.getIntValue("applyForNum");
                                }
                            }
                            break;
                        }
                    }
                }
            }
            map.put("组件",zujian);
            map.put("工具",gongju);
            map.put("平台",pingtai);
            return R.ok(map);
        }
        map.put("组件",0);
        map.put("工具",0);
        map.put("平台",0);
        return R.ok(map);

    }

    /**
     * 访问量统计
     */
    @GetMapping("/getfangwenliangtongji")
    public R getfangwenliangtongji() throws Exception {
        List<CmApp> lists=houseInfoService.getAllAppListForAdmin(null);
        Map<String,Integer> map=new HashMap<>();
        int zujian=0;
        int pingtai=0;
        int gongju=0;
        CommonRes commonRes = requestService.getAccessStatistics();
        if(commonRes.getData()!=null){
            JSONArray arrays = JSONArray.from(commonRes.getData());
            for (Object array : arrays) {
                JSONObject data = JSONObject.from(array);
                String appSecret = data.getString("id");
                if(StringUtils.isNotEmpty(appSecret)){
                    for (CmApp list : lists) {
                        if(appSecret.equals(list.getCasAppId())){
                            if(StringUtils.isNotEmpty(list.getAppModule())){
                                if("1669301825683091458".equals(list.getAppModule())){
                                    gongju=gongju+data.getIntValue("viewNum");
                                }else if("1669301899511230465".equals(list.getAppModule())){
                                    zujian=zujian+data.getIntValue("viewNum");
                                }else if("1669301968486559746".equals(list.getAppModule())){
                                    pingtai=pingtai+data.getIntValue("viewNum");
                                }
                            }
                            break;
                        }
                    }
                }
            }
            map.put("组件",zujian);
            map.put("工具",gongju);
            map.put("平台",pingtai);
            return R.ok(map);
        }
        map.put("组件",0);
        map.put("工具",0);
        map.put("平台",0);
        return R.ok(map);

    }

    /**
     * 申请量统计-场景分类占比
     */
    @PostMapping("/getshengqingChangjingZhanbi")
    public R getshengqinChangjingZhanbi(@RequestBody CmAppParam param) throws Exception {
        List<CmApp> lists=houseInfoService.getAllAppListForAdmin(param);
        Map<String,Integer> map=new HashMap<>();
        int zhengwu=0;
        int jingji=0;
        int shengtai=0;
        int zhili=0;
        int shenghuo=0;
        int jijian=0;
        CommonRes commonRes = requestService.getApplyForStatistics();
        if(commonRes.getData()!=null){
            JSONArray arrays = JSONArray.from(commonRes.getData());
            for (Object array : arrays) {
                JSONObject data = JSONObject.from(array);
                String appSecret = data.getString("id");
                if(StringUtils.isNotEmpty(appSecret)){
                    for (CmApp list : lists) {
                        if(appSecret.equals(list.getCasAppId())){
                            if(StringUtils.isNotEmpty(list.getAppType())){
                                if("001".equals(list.getAppType())){
                                    zhengwu=zhengwu+data.getIntValue("applyForNum");
                                }else if("002".equals(list.getAppType())){
                                    shenghuo=shenghuo+data.getIntValue("applyForNum");
                                }else if("003".equals(list.getAppType())){
                                    zhili=zhili+data.getIntValue("applyForNum");
                                }else if("004".equals(list.getAppType())){
                                    shengtai=shengtai+data.getIntValue("applyForNum");
                                }else if("005".equals(list.getAppType())){
                                    jijian=jijian+data.getIntValue("applyForNum");
                                }else if("006".equals(list.getAppType())){
                                    jingji=jingji+data.getIntValue("applyForNum");
                                }
                            }
                            break;
                        }
                    }
                }
            }
            map.put("数字经济",jingji);
            map.put("数字基建",jijian);
            map.put("数字生态",shengtai);
            map.put("数字治理",zhili);
            map.put("数字生活",shenghuo);
            map.put("数字政务",zhengwu);

            return R.ok(map);
        }
        map.put("数字经济",0);
        map.put("数字基建",0);
        map.put("数字生态",0);
        map.put("数字治理",0);
        map.put("数字生活",0);
        map.put("数字政务",0);
        return R.ok(map);

    }


    /**
     * 访问量统计-场景分类占比
     */
    @PostMapping("/getfangwenChangjingZhanbi")
    public R getfangwenChangjingZhanbi(@RequestBody CmAppParam param) throws Exception {
        List<CmApp> lists=houseInfoService.getAllAppListForAdmin(param);
        Map<String,Integer> map=new HashMap<>();
        int zhengwu=0;
        int jingji=0;
        int shengtai=0;
        int zhili=0;
        int shenghuo=0;
        int jijian=0;
        CommonRes commonRes = requestService.getAccessStatistics();
        if(commonRes.getData()!=null){
            JSONArray arrays = JSONArray.from(commonRes.getData());
            for (Object array : arrays) {
                JSONObject data = JSONObject.from(array);
                String appSecret = data.getString("id");
                Assert.notNull(appSecret,"appSecret is null");
                    for (CmApp list : lists) {
                        if(appSecret.equals(list.getCasAppId())){
                            if(StringUtils.isNotEmpty(list.getAppType())){
                                if("001".equals(list.getAppType())){
                                    zhengwu=zhengwu+data.getIntValue("viewNum");
                                }else if("002".equals(list.getAppType())){
                                    shenghuo=shenghuo+data.getIntValue("viewNum");
                                }else if("003".equals(list.getAppType())){
                                    zhili=zhili+data.getIntValue("viewNum");
                                }else if("004".equals(list.getAppType())){
                                    shengtai=shengtai+data.getIntValue("viewNum");
                                }else if("005".equals(list.getAppType())){
                                    jijian=jijian+data.getIntValue("viewNum");
                                }else if("006".equals(list.getAppType())){
                                    jingji=jingji+data.getIntValue("viewNum");
                                }
                            }
                            break;
                        }
                }
            }
            map.put("数字经济",jingji);
            map.put("数字基建",jijian);
            map.put("数字生态",shengtai);
            map.put("数字治理",zhili);
            map.put("数字生活",shenghuo);
            map.put("数字政务",zhengwu);

            return R.ok(map);
        }
        map.put("数字经济",0);
        map.put("数字基建",0);
        map.put("数字生态",0);
        map.put("数字治理",0);
        map.put("数字生活",0);
        map.put("数字政务",0);
        return R.ok(map);

    }

    /**
     * 获取后台首页审批状态分析
     *
     * @return
     */
    @GetMapping(value = "/getHomePageAnalysis")
    public CommonRes getHomePageAnalysis() throws Exception {
        CommonRes commonRes = requestService.getHomePageAnalysis();
        return commonRes;
    }


}
