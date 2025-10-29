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
import com.ruoyi.system.customer.domain.CmAbility;
import com.ruoyi.system.customer.domain.CmAbilityExamine;
import com.ruoyi.system.customer.service.*;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ICmAbilityExamineService;
import com.ruoyi.system.service.ICmAppExamineService;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.controller.customer.common.CsBaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "cmAbilityExamine")
public class CmAbliltyExamineController extends CsBaseController<ICmAbilityExamine2Service, CmAbilityExamine, String> {


    @Autowired
    private HouseInfoService houseInfoService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private ISysNoticeService sysNoticeService;
    @Autowired
    private ISysUserService userService;

    public R addAuthApplication() {


        return null;
    }


    @Override
    public void afterSave(CmAbilityExamine abilityExamine) {
        CmAbility cmAbility = abilityService.getAbilityById(abilityExamine.getCmAbilityId());
        SysUser sysUser = userService.selectUserById(getLongFromString(cmAbility.getCreateBy()));
        log.info("|abilityExamine:,,....");
        log.info("{}", abilityExamine);
        SendMessageEnum sendMessageEnum = null;
        switch (abilityExamine.getExamineType()) {
            case "0": {
                sendMessageEnum = SendMessageEnum.cmAbilitySuccess_new;
                break;
            }
            case "2": {
                sendMessageEnum = SendMessageEnum.cmAbilityFail;
                break;
            }
            case "4": {
                sendMessageEnum = SendMessageEnum.cmAbilityWithdraw_new;
                break;
            }
        }
        sysNoticeService.insertNotice(
                SysNotice.getCmAbilityMessageDetail(sendMessageEnum, cmAbility, sysUser, abilityExamine.getUpdateTime())
        );
    }

    @Override
    public void beforeSave(CmAbilityExamine abilityExamine) {
        if (StringUtils.isNotEmpty(abilityExamine.getCmAbilityId())) {
            CmAbility cmAbility = new CmAbility();
            cmAbility.setId(abilityExamine.getCmAbilityId());
            cmAbility.setStatus(Integer.parseInt(abilityExamine.getExamineType()));
            abilityService.updateStatusById(cmAbility);
        }

    }

    private Long getLongFromString(String num) {
        return StringUtils.isNumeric(num) ? Long.parseLong(num) : 1L;
    }


    @Override
    public void setQueryWrapperForList(QueryWrapper<CmAbilityExamine> wrapper, CmAbilityExamine cmAbility) {

    }
}
