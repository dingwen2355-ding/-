package com.znv.manage.service.impl;

import com.znv.manage.bean.user.Department;
import com.znv.manage.dao.DepartmentDao;
import com.znv.manage.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentDao departmentDao;

    @Override
    public List<Map<String, String>> queryDepartment(
            String id, String department,
            String type, String address, String precinct_id) {
        return departmentDao.queryDepartment(id,department,type,address,precinct_id);
    }

    @Override
    public List<Map<String, String>> queryDepartmentByName(String department,String precinctId) {
        return departmentDao.queryDepartmentByName(department,precinctId);
    }

    @Override
    public void insertDepartment(Department department) {
         departmentDao.insertDepartment(department);
    }

    @Override
    public void deleteDepartment(String id,String department) {
         departmentDao.deleteDepartment(id,department);
    }

    @Override
    public void updateDepartment(Department department) {
        departmentDao.updateDepartment( department);
    }
}
