package com.ruoyi.system.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.customer.domain.CmAbilityExamine;
import com.ruoyi.system.customer.domain.CmMoban;
import com.ruoyi.system.customer.mapper.CmAbilityExamineMapper;
import com.ruoyi.system.customer.mapper.CmMobanMapper;
import com.ruoyi.system.customer.service.ICmAbilityExamine2Service;
import com.ruoyi.system.customer.service.ICmMobanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmMobanServiceImpl extends ServiceImpl<CmMobanMapper, CmMoban> implements ICmMobanService {


    @Override
    public void updateIsUseTo0ByType(String type) {
        baseMapper.updateIsUseTo0ByType( type);
    }

    @Override
    public List<CmMoban> getmobanUrl(String type) {
        return baseMapper.getmobanUrl( type);
    }
}
