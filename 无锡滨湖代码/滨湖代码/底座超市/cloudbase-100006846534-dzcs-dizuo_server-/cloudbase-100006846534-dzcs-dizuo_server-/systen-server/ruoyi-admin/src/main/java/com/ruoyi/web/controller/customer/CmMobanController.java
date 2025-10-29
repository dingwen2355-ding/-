package com.ruoyi.web.controller.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.customer.domain.*;
import com.ruoyi.system.customer.service.*;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ICmAppExamineService;
import com.ruoyi.web.controller.customer.common.CsBaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "cmMoban")
public class CmMobanController extends CsBaseController<ICmMobanService, CmMoban, String> {

    private final ICmAppExamineService cmAppExamineService;
    private final ICmMobanService mobanService;
    private final SysUserMapper sysUserMapper;
    private final RequestService requestService;



    @Autowired
    private HouseInfoService houseInfoService;
    @Autowired
    private AbilityService abilityService;


    public R addAuthApplication() {


        return null;
    }


    @Override
    public void afterSave(CmMoban moban) {

    }

    @Override
    public void beforeSave(CmMoban moban) {
        if(StringUtils.isNotEmpty(moban.getIsUse())&&"1".equals(moban.getIsUse())){
            mobanService.updateIsUseTo0ByType(moban.getType());
        }

    }

    @GetMapping("/mobanUrl")
    public R mobanUrl(String type)
    {
        if(StringUtils.isNotEmpty(type)){
            List<CmMoban> list=mobanService.getmobanUrl(type);
            if(list!=null){
                return R.ok(list);
            }
            return R.fail("数据为空");
        }

        return R.fail("附件类型为空");
    }


    @PostMapping("/allList")
    public R replayList(@RequestBody CmMoban t, Page<CmMoban> page) throws ParseException {
        QueryWrapper<CmMoban> wrapper = new QueryWrapper<>();


        if(StringUtils.isNotEmpty(t.getType())){
            wrapper.eq("type",t.getType());
        }
        if(StringUtils.isNotEmpty(t.getName())){
            wrapper.like("type",t.getName());
        }
        if(StringUtils.isNotEmpty(t.getIsUse())){
            wrapper.like("is_use",t.getIsUse());
        }
        wrapper.eq("del_flag",0);

        wrapper.orderByDesc("create_time");

        PageDomain pageDomain = TableSupport.buildPageRequest();
        page.setSize(pageDomain.getPageSize());
        page.setCurrent(pageDomain.getPageNum());

        page = getService().page(page, wrapper);
        return R.ok(page);
    }






    @Override
    public void setQueryWrapperForList(QueryWrapper<CmMoban> wrapper, CmMoban cmAbility) {

    }
}
