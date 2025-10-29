package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncSSBJ;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: xx描述xx
 * @date
 */
@Repository
public interface FireAwarenessSSBJDao {

    int insertSSBJData(@Param("list") List<FireAwarenessSyncSSBJ> resultList);

    int queryExist(FireAwarenessSyncSSBJ fireAwarenessSyncSSBJ);

}
