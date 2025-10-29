package com.ruoyi.system.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.customer.domain.CmApp;
import com.ruoyi.system.customer.domain.CmAppParam;
import com.ruoyi.system.customer.mapper.CmAppMapper;
import com.ruoyi.system.customer.service.ICmAppService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CmAppServiceImpl extends ServiceImpl<CmAppMapper, CmApp> implements ICmAppService {

    @Override
    public void updateCasAppidById(String id, Object data) {
        baseMapper.updateCasAppidById(id,data);
    }
}
