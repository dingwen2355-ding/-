package com.ruoyi.system.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.customer.domain.CmAppType;
import com.ruoyi.system.customer.mapper.CmAppTypeMapper;
import com.ruoyi.system.customer.service.ICmAppTypeService;
import org.springframework.stereotype.Service;

@Service
public class CmAppTypeServiceImpl extends ServiceImpl<CmAppTypeMapper, CmAppType> implements ICmAppTypeService {
    @Override
    public int updateStatus(String id, Long status) {
        return baseMapper.updateStatus(id,status);
    }
}
