package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 导入的新预案
 *
 * @author ：0049003788
 * @date ：2022/10/26 9:36
 */
@Repository
public interface ImportPlanDao {
    List<Map<String, Object>> queryPlan(@Param("planId") Integer planId);

    List<Map<String, Object>> queryRequirement(@Param("planId") Integer planId);
}
