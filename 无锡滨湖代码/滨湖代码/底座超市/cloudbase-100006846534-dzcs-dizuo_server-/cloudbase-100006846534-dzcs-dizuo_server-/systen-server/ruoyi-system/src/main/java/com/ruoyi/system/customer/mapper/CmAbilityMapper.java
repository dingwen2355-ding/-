package com.ruoyi.system.customer.mapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.customer.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface CmAbilityMapper {


    int insertAbility(CmAbility cmAbility);

    CmAbility getAbilityById(String id);

    int update(CmAbility cmAbility);

    void save(CmAbilityExamine cmAbilityExamine);

    List<CmAbility> centerAbility(@Param("s") String createBy,@Param("currentpage") long currentpage,@Param("size") long size);

    List<CmAbility> getAllAbility(CmAbility cmAbility);

    List<CmAbility> centerAbilitytotal(@Param("s") String createBy);

    List<CmAbility> getAllAbilityForPage(@Param("cmAbility")CmAbility cmAbility, @Param("currentpage") long currentpage,@Param("size") long size);

    void updateStatusById(CmAbility cmAbility);

    List<Map<String, Integer>> getShouYeShenpiCount();

    List<Map<String, String>> getAppfenleiCount();

    List<Map<String, String>> getRuZhulaiyuanZhanBi();

    List<Map<String, String>> getRuZhuDeptZhanBi();

}
