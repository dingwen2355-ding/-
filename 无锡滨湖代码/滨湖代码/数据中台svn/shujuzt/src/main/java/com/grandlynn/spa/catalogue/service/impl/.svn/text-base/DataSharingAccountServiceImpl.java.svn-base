package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.common.enums.DataSharingAccountStatusEnum;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountPageDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountQueryDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountStatusDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.DataSharingAccount;
import com.grandlynn.spa.catalogue.mapper.DataSharingAccountMapper;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.DataSharingAccountService;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class DataSharingAccountServiceImpl extends ServiceImpl<DataSharingAccountMapper, DataSharingAccount> implements DataSharingAccountService {
   @Resource
   private CatalogueOrgService catalogueOrgService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean add(DataSharingAccountDTO dataSharingAccountDTO) {
      this.accountIsBlank(dataSharingAccountDTO);
      this.checkAccount(dataSharingAccountDTO.getAccount(), (Long)null);
      DataSharingAccount dataSharingAccount = new DataSharingAccount();
      BeanUtils.copyProperties(dataSharingAccountDTO, dataSharingAccount);
      DateTime date = DateUtil.date();
      dataSharingAccount.setCreatedTime(date);
      return this.save(dataSharingAccount);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean update(DataSharingAccountDTO dataSharingAccountDTO) {
      this.accountIsBlank(dataSharingAccountDTO);
      if (dataSharingAccountDTO.getAccountId() == null) {
         throw new ApplicationException("更新id不能为空");
      } else {
         DataSharingAccount dataSharingAccountVo = (DataSharingAccount)((DataSharingAccountMapper)this.baseMapper).selectById(dataSharingAccountDTO.getAccountId());
         if (dataSharingAccountVo == null) {
            throw new ApplicationException("更新失败，数据不存在");
         } else {
            this.checkAccount(dataSharingAccountDTO.getAccount(), dataSharingAccountDTO.getAccountId());
            if (!dataSharingAccountVo.getAccount().equals(dataSharingAccountDTO.getAccount())) {
               throw new ApplicationException("认证账号不能修改");
            } else if (!DataSharingAccountStatusEnum.UN_USE.getCode().equals(dataSharingAccountVo.getStatus()) && (!dataSharingAccountVo.getAccount().equals(dataSharingAccountDTO.getAccount()) || !dataSharingAccountVo.getOrgId().equals(dataSharingAccountDTO.getOrgId()))) {
               throw new ApplicationException("此状态下，只能修改密码");
            } else {
               DataSharingAccount dataSharingAccount = new DataSharingAccount();
               BeanUtils.copyProperties(dataSharingAccountDTO, dataSharingAccount);
               dataSharingAccount.setUpdatedTime(DateUtil.date());
               return ((DataSharingAccountMapper)this.baseMapper).updateById(dataSharingAccount) > 0;
            }
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean updateStatus(DataSharingAccountStatusDTO dataSharingAccountDTO) {
      if (dataSharingAccountDTO.getAccountId() == null) {
         throw new ApplicationException("更新id不能为空");
      } else {
         DataSharingAccount dataSharingAccountVo = (DataSharingAccount)((DataSharingAccountMapper)this.baseMapper).selectById(dataSharingAccountDTO.getAccountId());
         if (dataSharingAccountVo == null) {
            throw new ApplicationException("更新失败，数据不存在");
         } else {
            dataSharingAccountVo.setStatus(dataSharingAccountDTO.getStatus());
            dataSharingAccountVo.setUpdatedTime(DateUtil.date());
            return this.updateById(dataSharingAccountVo);
         }
      }
   }

   public PageBean<DataSharingAccountPageDTO> accountByPage(DataSharingAccountQueryDTO pageDTO) {
      PageUtil.page(pageDTO.getPage(), pageDTO.getSize());
      List<DataSharingAccountPageDTO> dataSharingAccountPageDTOS = ((DataSharingAccountMapper)this.baseMapper).selectByPage(pageDTO);
      if (dataSharingAccountPageDTOS.size() == 0) {
         return new PageBean();
      } else {
         dataSharingAccountPageDTOS.forEach((dataSharing) -> {
            dataSharing.setStatusName(DataSharingAccountStatusEnum.getByCode(dataSharing.getStatus()));
         });
         return new PageBean(dataSharingAccountPageDTOS);
      }
   }

   public DataSharingAccountPageDTO getByAccountId(Long accountId) {
      DataSharingAccount dataSharingAccount = (DataSharingAccount)this.getById(accountId);
      if (dataSharingAccount == null) {
         throw new ApplicationException("数据不存在");
      } else {
         DataSharingAccountPageDTO dataSharingAccountDTO = new DataSharingAccountPageDTO();
         BeanUtils.copyProperties(dataSharingAccount, dataSharingAccountDTO);
         Integer orgId = dataSharingAccountDTO.getOrgId();
         CatalogueOrgDO catalogueOrgDO = (CatalogueOrgDO)this.catalogueOrgService.getById(orgId);
         if (catalogueOrgDO != null) {
            dataSharingAccountDTO.setOrgNameSimplify(catalogueOrgDO.getNodeNameSimplify());
         }

         dataSharingAccountDTO.setStatusName(DataSharingAccountStatusEnum.getByCode(dataSharingAccountDTO.getStatus()));
         return dataSharingAccountDTO;
      }
   }

   public List<DataSharingAccountDTO> listBy(DataSharingAccountPageDTO dataSharingAccountDTO) {
      LambdaQueryWrapper<DataSharingAccount> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(dataSharingAccountDTO.getOrgId() != null, DataSharingAccount::getOrgId, dataSharingAccountDTO.getOrgId());
      queryWrapper.eq(dataSharingAccountDTO.getShareMode() != null, DataSharingAccount::getUseType, dataSharingAccountDTO.getShareMode());
      queryWrapper.and(qw -> qw.eq(dataSharingAccountDTO.getStatus() != null && !dataSharingAccountDTO.getStatus().equals(0), DataSharingAccount::getStatus, dataSharingAccountDTO.getStatus())
              .ne(DataSharingAccount::getStatus, 0)
              .or(dataSharingAccountDTO.getAccountId() != null)
              .eq(dataSharingAccountDTO.getAccountId() != null, DataSharingAccount::getAccountId, dataSharingAccountDTO.getAccountId())
      );
      queryWrapper.orderByDesc(DataSharingAccount::getCreatedTime);
      List<DataSharingAccount> list = this.list(queryWrapper);
      List<DataSharingAccountDTO> dataSharingAccountDTOS = new ArrayList();
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         DataSharingAccount dataSharingAccount = (DataSharingAccount)var5.next();
         DataSharingAccountDTO sharingAccountDTO = new DataSharingAccountDTO();
         BeanUtils.copyProperties(dataSharingAccount, sharingAccountDTO);
         dataSharingAccountDTOS.add(sharingAccountDTO);
      }

      return dataSharingAccountDTOS;
   }

   public Boolean updateUseStatus(Long accountId) {
      DataSharingAccount dataSharingAccountVo = (DataSharingAccount)((DataSharingAccountMapper)this.baseMapper).selectById(accountId);
      if (ObjectUtil.isEmpty(dataSharingAccountVo)) {
         throw new ApplicationException("更新失败，数据不存在");
      } else {
         dataSharingAccountVo.setStatus(DataSharingAccountStatusEnum.USE.getCode());
         dataSharingAccountVo.setUpdatedTime(DateUtil.date());
         return this.updateById(dataSharingAccountVo);
      }
   }

   public Boolean removeAccount(Long accountId) {
      if (accountId == null) {
         throw new ApplicationException("id不能为空");
      } else {
         return this.removeById(accountId);
      }
   }

   private void accountIsBlank(DataSharingAccountDTO dataSharingAccountDTO) {
      if (dataSharingAccountDTO == null) {
         throw new ApplicationException("参数不能为空");
      } else if (StringUtils.isEmpty(dataSharingAccountDTO.getAccount())) {
         throw new ApplicationException("认证账号不能为空");
      } else if (StringUtils.isEmpty(dataSharingAccountDTO.getAccountPwd())) {
         throw new ApplicationException("认证密码不能为空");
      } else if (dataSharingAccountDTO.getUseType() == null) {
         throw new ApplicationException("请选择使用方式");
      } else if (dataSharingAccountDTO.getOrgId() == null) {
         throw new ApplicationException("请选择使用组织");
      }
   }

   private void checkAccount(String account, Long accountId) {
      LambdaQueryWrapper<DataSharingAccount> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(account != null, DataSharingAccount::getAccount, account);
      queryWrapper.ne(accountId != null, DataSharingAccount::getAccountId, accountId);
      long count = this.count(queryWrapper);
      if (count > 0) {
         throw new ApplicationException("认证账号已存在");
      }
   }

}
