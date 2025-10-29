package com.ruoyi.web.controller.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.customer.domain.*;
import com.ruoyi.system.customer.service.*;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ICmAppExamineService;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.web.controller.customer.common.CsBaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

import static com.ruoyi.system.domain.SysNotice.getLongFromString;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "cmAbilitymoban")
public class CmAbliltymobanController extends CsBaseController<ICmAbilityService, CmAbility, String> {

    private final ICmAppExamineService cmAppExamineService;
    private final ICmAppTypeService iCmAppTypeService;
    private final SysUserMapper sysUserMapper;
    private final RequestService requestService;


    @Autowired
    private HouseInfoService houseInfoService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    public R addAuthApplication() {


        return null;
    }

    @Override
    protected void afterUpdate(CmAbility t) {
        afterSave(t);
    }


    @Override
    public void afterSave(CmAbility cmApp) {
//        if (adminFlag()) {
//            getService().updateById(cmApp);
//            CmAbilityExamine cmAppExamine = new CmAbilityExamine();
//            cmAppExamine.setCmAbilityId(cmApp.getId());
//            cmAppExamine.setExamineType(cmApp.getStatus().toString());
//            cmAppExamine.setRemark(cmApp.getStatus() == 0 ? "管理员上架" : "管理员下架");
//            iCmAbilityExamineService.save(cmAppExamine);
//        }

        SysUser sysUser = null;
        if (adminFlag()) {
            cmApp.setCreateBy("1");
            sysUser = SecurityUtils.getLoginUser().getUser();
        } else {
            sysUser = sysUserMapper.selectUserById(getLongFromString(cmApp.getCreateBy()));
        }
        log.info("|cmApp:........");
        log.info("{}", cmApp);
        log.info("{}", sysUser);


        if (cmApp.getStatus()==null){
            sysNoticeService.insertNotice(
                    SysNotice.getCmAbilityMessageDetail(SendMessageEnum.cmAbilityCreate_new, cmApp, sysUser, cmApp.getCreateTime())
            );

            cmApp.setCreateBy("1");
            SysNotice cmAbilityMessageDetail = SysNotice.getCmAbilityMessageDetail(SendMessageEnum.cmAbilityCreate_new, cmApp, sysUser, cmApp.getCreateTime());
            cmAbilityMessageDetail.setNoticeTitle(cmAbilityMessageDetail.getNoticeTitle().replace("申请","审批"));
            sysNoticeService.insertNotice(cmAbilityMessageDetail);
            log.info("{}", sysUser);
            return;
        }

        switch (cmApp.getStatus()) {
            case 0: {
                sysNoticeService.insertNotice(
                        SysNotice.getCmAbilityMessageDetail(SendMessageEnum.cmAbilitySuccess_new, cmApp, sysUser, cmApp.getCreateTime())
                );
                break;
            }
            case 2 :{
                sysNoticeService.insertNotice(
                        SysNotice.getCmAbilityMessageDetail(SendMessageEnum.cmAbilityFail, cmApp, sysUser, cmApp.getCreateTime())
                );
                break;
            }
            case 4 :{
                sysNoticeService.insertNotice(
                        SysNotice.getCmAbilityMessageDetail(SendMessageEnum.cmAbilityWithdraw_new, cmApp, sysUser, cmApp.getCreateTime())
                );

                cmApp.setCreateBy("1");
                SysNotice cmAbilityMessageDetail = SysNotice.getCmAbilityMessageDetail(SendMessageEnum.cmAbilityWithdraw_new, cmApp, sysUser, cmApp.getCreateTime());
                cmAbilityMessageDetail.setNoticeTitle(cmAbilityMessageDetail.getNoticeTitle().replace("申请","审批"));
                sysNoticeService.insertNotice(
                        cmAbilityMessageDetail
                );
            }
            break;
        }

    }

    @Override
    public void beforeSave(CmAbility cmAbility) {

    }

    @RequestMapping("/centerAbility")
    public R centerAbility(@RequestBody CmAbility t, Page<CmAbility> page) throws ParseException {
        QueryWrapper<CmAbility> wrapper = new QueryWrapper<>();

        if (!adminFlag()) {
            wrapper.eq("create_by", SecurityUtils.getUserId().toString());
        }
        wrapper.eq("del_flag", "0");

        wrapper.orderByDesc("create_time");

        PageDomain pageDomain = TableSupport.buildPageRequest();
        page.setSize(pageDomain.getPageSize());
        page.setCurrent(pageDomain.getPageNum());
        page = getService().page(page, wrapper);
        List<CmAbility> records = page.getRecords();
        return R.ok(page);
    }

    @RequestMapping("/getAllAbility")
    public R getAllAbility(@RequestBody CmAbility t, Page<CmAbility> page) throws ParseException {
        QueryWrapper<CmAbility> wrapper = new QueryWrapper<>();


        wrapper.eq("del_flag", "0");
        if (t.getStatus() != null) {
            wrapper.eq("status", t.getStatus());
        }
        if (StringUtils.isNotEmpty(t.getPrincipalName())) {
            wrapper.eq("principal_name", t.getPrincipalName());
        }
        if (StringUtils.isNotEmpty(t.getDanweiType())) {
            wrapper.eq("danwei_type", t.getDanweiType());
        }
        wrapper.orderByDesc("create_time");

        PageDomain pageDomain = TableSupport.buildPageRequest();
        page.setSize(pageDomain.getPageSize());
        page.setCurrent(pageDomain.getPageNum());
        page = getService().page(page, wrapper);
        List<CmAbility> records = page.getRecords();
        return R.ok(page);
    }


    @Override
    public void setQueryWrapperForList(QueryWrapper<CmAbility> wrapper, CmAbility cmAbility) {

    }
}
