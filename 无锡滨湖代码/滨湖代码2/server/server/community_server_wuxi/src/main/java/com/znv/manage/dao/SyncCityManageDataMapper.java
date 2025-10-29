package com.znv.manage.dao;

import com.znv.manage.common.bean.Device;
import com.znv.manage.common.bean.SyncCityManageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyncCityManageDataMapper {

    int syncData(@Param("list") List<SyncCityManageData> resultList);

}
