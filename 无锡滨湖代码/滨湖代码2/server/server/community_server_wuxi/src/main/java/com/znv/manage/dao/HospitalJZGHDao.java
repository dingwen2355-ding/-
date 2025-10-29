package com.znv.manage.dao;

import com.znv.manage.common.bean.HospitalSyncJZGH;
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
public interface HospitalJZGHDao {

    int insertJZGHData(@Param("list") List<HospitalSyncJZGH> resultList);

    int queryExist(HospitalSyncJZGH fireAwarenessSyncJZGH);

}
