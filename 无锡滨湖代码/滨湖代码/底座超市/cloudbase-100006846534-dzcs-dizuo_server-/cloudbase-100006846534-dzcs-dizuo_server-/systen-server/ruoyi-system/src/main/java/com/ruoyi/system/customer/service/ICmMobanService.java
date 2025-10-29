package com.ruoyi.system.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.customer.domain.CmAbilityExamine;
import com.ruoyi.system.customer.domain.CmMoban;

import java.util.List;
import java.util.Map;


public interface ICmMobanService extends IService<CmMoban> {


    void updateIsUseTo0ByType(String type);

    List<CmMoban> getmobanUrl(String type);



}
