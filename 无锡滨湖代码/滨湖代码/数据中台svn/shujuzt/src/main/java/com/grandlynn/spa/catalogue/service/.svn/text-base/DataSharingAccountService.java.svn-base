package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountPageDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountQueryDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountStatusDTO;
import com.grandlynn.spa.catalogue.entity.DataSharingAccount;
import com.grandlynn.util.pageHelper.PageBean;

import java.util.List;

public interface DataSharingAccountService extends IService<DataSharingAccount> {
   Boolean add(DataSharingAccountDTO dataSharingAccountDTO);

   Boolean update(DataSharingAccountDTO dataSharingAccountDTO);

   Boolean updateStatus(DataSharingAccountStatusDTO dataSharingAccountDTO);

   PageBean<DataSharingAccountPageDTO> accountByPage(DataSharingAccountQueryDTO pageDTO);

   DataSharingAccountPageDTO getByAccountId(Long accountId);

   List<DataSharingAccountDTO> listBy(DataSharingAccountPageDTO dataSharingAccountDTO);

   Boolean updateUseStatus(Long accountId);

   Boolean removeAccount(Long accountId);
}
