package com.znv.manage.dao;

import com.znv.manage.bean.user.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DepartmentDao {
    List<Map<String, String>> queryDepartment(
            @Param(value = "id") String id,
            @Param(value = "department") String department,
            @Param(value = "type") String type,
            @Param(value = "address") String address,
            @Param(value = "precinct_id") String precinct_id);

    void insertDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(@Param(value = "id")String id,
                      @Param(value = "department")String department);

    List<Map<String, String>> queryDepartmentByName(@Param(value = "department")String department,
                                                    @Param(value = "precinctId")String precinctId);
}
