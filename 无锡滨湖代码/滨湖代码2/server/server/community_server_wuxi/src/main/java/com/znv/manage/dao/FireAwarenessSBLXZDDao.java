package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncSBLXZD;
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
public interface FireAwarenessSBLXZDDao {

    int insertSBLXZDData(@Param("list") List<FireAwarenessSyncSBLXZD> resultList);

    int updateSBLXZDData(@Param("list") List<FireAwarenessSyncSBLXZD> resultList);

    int queryExist(FireAwarenessSyncSBLXZD fireAwarenessSyncSBLXZD);

    int truncateSBLXZDData();

}
