package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.entity.ForeignOaOrg;

public interface ForeignOaOrgService extends IService<ForeignOaOrg> {
   ForeignOaOrg recursionTop(Long orgid);
}
