package com.znv.manage.service;

import com.znv.manage.bean.user.Department;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * @author ：lilingling
 * @date ： 2020/03/04
 */
public interface DepartmentService {
    void insertDepartment(Department department);
    void deleteDepartment(String id,String department);
    void updateDepartment(Department department);
    List<Map<String, String>> queryDepartment(String id, String department, String type, String address, String precinct_id);

    List<Map<String, String>> queryDepartmentByName(String department,String precinctId);
}
