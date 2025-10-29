package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableResourceSerialDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueSysTableResourceSerialMapper;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableResourceSerialService;
import java.lang.invoke.SerializedLambda;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("catalogueSysTableResourceSerialService")
public class CatalogueSysTableResourceSerialServiceImpl extends ServiceImpl<CatalogueSysTableResourceSerialMapper, CatalogueSysTableResourceSerialDO> implements CatalogueSysTableResourceSerialService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueSysTableResourceSerialServiceImpl.class);

   public String getResourceSerialByOrg(String resourceCode, String nodeNo) {
      log.info("根据nodeNo[{}]获取部门资源序列", nodeNo);
      LambdaQueryWrapper<CatalogueSysTableResourceSerialDO> wp = new LambdaQueryWrapper();
      wp.eq(CatalogueSysTableResourceSerialDO::getOrgName, nodeNo);
      List<CatalogueSysTableResourceSerialDO> list = this.list(wp);
      log.debug("根据nodeNo[{}]获取部门资源序列结果{}", nodeNo, list);
      if (CollUtil.isEmpty(list)) {
         Integer serial = 0;
         this.save(new CatalogueSysTableResourceSerialDO(nodeNo, serial));
         return String.format("%s/%06d", resourceCode, serial);
      } else {
         CatalogueSysTableResourceSerialDO resourceSerial = (CatalogueSysTableResourceSerialDO)list.get(0);
         Integer serial = resourceSerial.getSerial() + 1;
         resourceSerial.setSerial(serial);
         this.updateById(resourceSerial);
         return String.format("%s/%06d", resourceCode, serial);
      }
   }

}
