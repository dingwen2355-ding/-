package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncBJHCL;
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
public interface FireAwarenessBJHCLDao {

    int insertBJHCLData(@Param("list") List<FireAwarenessSyncBJHCL> resultList);

    int queryExist(FireAwarenessSyncBJHCL fireAwarenessSyncBJHCL);

    int deleteBJHCLData();

}
