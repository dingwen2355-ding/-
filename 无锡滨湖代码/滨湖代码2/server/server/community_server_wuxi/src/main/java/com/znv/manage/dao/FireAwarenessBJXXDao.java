package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncBJXX;
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
public interface FireAwarenessBJXXDao {

    int insertBJXXData(@Param("list") List<FireAwarenessSyncBJXX> resultList);

    int queryExist(FireAwarenessSyncBJXX fireAwarenessSyncBJXX);

    int deleteBJXXData();

}
