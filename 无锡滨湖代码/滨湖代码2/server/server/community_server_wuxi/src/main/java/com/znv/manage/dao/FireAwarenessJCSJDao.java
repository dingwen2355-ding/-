package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncJCSJ;
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
public interface FireAwarenessJCSJDao {

    int insertJCSJData(@Param("list") List<FireAwarenessSyncJCSJ> resultList);

    int queryExist(FireAwarenessSyncJCSJ fireAwarenessSyncJCSJ);

    int deleteJCSJData();

}
