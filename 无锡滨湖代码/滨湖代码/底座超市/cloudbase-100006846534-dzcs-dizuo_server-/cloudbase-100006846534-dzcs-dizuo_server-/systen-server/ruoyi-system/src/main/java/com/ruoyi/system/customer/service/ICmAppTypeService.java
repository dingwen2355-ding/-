package com.ruoyi.system.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.customer.domain.CmAppType;

public interface ICmAppTypeService  extends IService<CmAppType> {
    int updateStatus(String id, Long status);
}
