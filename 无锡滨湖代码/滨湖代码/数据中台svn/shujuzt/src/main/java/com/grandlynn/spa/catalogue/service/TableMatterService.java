package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.entity.TableMatterDO;

public interface TableMatterService extends IService<TableMatterDO> {
   Boolean syncMatterByDate(String date);
}
