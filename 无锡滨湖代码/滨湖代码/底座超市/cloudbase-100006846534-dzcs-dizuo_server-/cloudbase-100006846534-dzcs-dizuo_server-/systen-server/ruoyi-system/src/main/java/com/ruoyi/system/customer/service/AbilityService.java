package com.ruoyi.system.customer.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.customer.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 房屋业务层
 *
 * @author ruoyi
 */
public interface AbilityService
{


    int insert(CmAbility cmAbility);

    CmAbility getAbilityById(String id);

    int update(CmAbility cmAbility);

    void save(CmAbilityExamine cmAbilityExamine);

    List<CmAbility> centerAbility(Page<CmAbility> page);

    List<CmAbility> getAllAbility(CmAbility cmAbility);

    List<CmAbility> centerAbilitytotal();

    List<CmAbility> getAllAbilityForPage(CmAbility cmAbility, Page<CmAbility> page);

    void updateStatusById(CmAbility cmAbility);

    List<Map<String, Integer>> getShouYeShenpiCount();

    List<Map<String, String>> getAppfenleiCount();

    List<Map<String, String>> getRuZhulaiyuanZhanBi();

    List<Map<String, String>> getRuZhuDeptZhanBi() throws Exception;

    CommonRes getUserTotal();

}
