package com.znv.manage.service.impl;

import com.znv.manage.dao.ModuleDao;
import com.znv.manage.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public List<Map<String, Object>> queryModules(String id, String moduleName, String moduleUrl, String description, String upModuleId) {
        return moduleDao.queryModules(id,moduleName,moduleUrl,description,upModuleId);
    }

    @Override
    public void insertModule(String moduleName, String moduleUrl, String description, String upModuleId) {
        moduleDao.insertModule(moduleName,moduleUrl,description,upModuleId);
    }

    @Override
    public void updateModule(String id, String moduleName, String moduleUrl, String description, String upModuleId) {
        moduleDao.updateModule(id,moduleName,moduleUrl,description,upModuleId);
    }

    @Override
    public void deleteModule(String id, String moduleName, String moduleUrl, String description, String upModuleId) {
        moduleDao.deleteModule(id,moduleName,moduleUrl,description,upModuleId);
    }
}
