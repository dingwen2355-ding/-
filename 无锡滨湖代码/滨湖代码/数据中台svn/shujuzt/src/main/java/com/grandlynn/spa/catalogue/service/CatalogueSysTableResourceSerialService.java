package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableResourceSerialDO;

public interface CatalogueSysTableResourceSerialService extends IService<CatalogueSysTableResourceSerialDO> {
   String getResourceSerialByOrg(String resourceCode, String nodeNo);
}
