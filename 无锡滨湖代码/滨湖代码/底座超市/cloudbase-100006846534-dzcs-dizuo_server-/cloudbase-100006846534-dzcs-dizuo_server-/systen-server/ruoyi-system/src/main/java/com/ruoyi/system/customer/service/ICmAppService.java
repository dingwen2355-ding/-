package com.ruoyi.system.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.customer.domain.CmApp;
import com.ruoyi.system.customer.domain.CmAppParam;

import java.util.List;
import java.util.Map;

public interface ICmAppService extends IService<CmApp> {
    void updateCasAppidById(String id, Object data);



}
