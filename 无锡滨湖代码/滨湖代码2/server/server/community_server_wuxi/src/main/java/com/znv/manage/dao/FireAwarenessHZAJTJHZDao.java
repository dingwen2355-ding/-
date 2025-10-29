package com.znv.manage.dao;

import com.znv.manage.common.bean.FireAwarenessSyncHZAJTJHZ;
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
public interface FireAwarenessHZAJTJHZDao {

    int insertHZAJTJHZData(@Param("list") List<FireAwarenessSyncHZAJTJHZ> resultList);

    int queryExist(FireAwarenessSyncHZAJTJHZ fireAwarenessSyncHZAJTJHZ);

    int deleteHZAJTJHZData();

}
