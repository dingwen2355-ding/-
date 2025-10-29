package com.znv.manage.dao;

import com.znv.manage.common.bean.*;
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
public interface DingDingDao {
    int insertDeptData(DeptInfo deptInfo);

    int updateDeptData(DeptInfo deptInfo);

    int queryDeptExist(@Param("id") String id);

    int queryCommunicationExist(@Param("id") String id);

    int insertCommunicationData(@Param("list") List<CommunicationInfo> resultList);

    int updateCommunicationData(@Param("list") List<CommunicationInfo> resultList);

}
