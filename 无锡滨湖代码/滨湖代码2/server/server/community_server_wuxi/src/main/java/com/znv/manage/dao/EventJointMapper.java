package com.znv.manage.dao;

import com.znv.manage.common.bean.EventBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EventJointMapper {
    List<Map<String, Object>> queryDepartment(@Param("id") Integer id,
                                              @Param("name") String name,
                                              @Param("secondaryName") String secondaryName,
                                              @Param("level") String level);

    /**
     * 查询区市县下的三级部门
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> queryDepartmentLow();

    List<Map<String, Object>> queryAllSecondary();

    List<Map<String, Object>> queryAllCounty();

    List<Map<String, Object>> queryTitle(@Param("eventId") String eventId,
                                         @Param("startDate") String startDate,
                                         @Param("endDate") String endDate);

    int saveBill(EventBill eventBill);
}
