package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncSBXX;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author znv
 * @ClassName:
 * @Description: xx描述xx
 * @date
 */
@Repository
public interface FireAwarenessSBXXDao {

    int insertSBXXData(@Param("list") List<FireAwarenessSyncSBXX> resultList);

    int updateSBXXData(@Param("list") List<FireAwarenessSyncSBXX> resultList);

    int queryExist(FireAwarenessSyncSBXX fireAwarenessSyncSBXX);

    int truncateSBXXData();

}
