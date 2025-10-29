package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.dto.DataSharingAccountDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountPageDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountQueryDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountStatusDTO;
import com.grandlynn.spa.catalogue.service.DataSharingAccountService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/data-catalogue/v1/dataSharingAccount"})
@Api(
   tags = {"系统设置数据分享认证帐号"}
)
public class DataSharingAccountController {
   @Resource
   private DataSharingAccountService dataSharingAccountService;

   @PostMapping({"/add"})
   @ApiOperation(
      value = "创建账号",
      httpMethod = "POST",
      response = Payload.class,
      notes = "创建账号"
   )
   public Payload<Boolean> add(@RequestBody DataSharingAccountDTO dataSharingAccountDTO) {
      return new Payload(this.dataSharingAccountService.add(dataSharingAccountDTO));
   }

   @PutMapping({"/update"})
   @ApiOperation(
      value = "修改账号信息",
      httpMethod = "PUT",
      response = Payload.class,
      notes = "修改账号信息"
   )
   public Payload<Boolean> update(@RequestBody DataSharingAccountDTO dataSharingAccountDTO) {
      return new Payload(this.dataSharingAccountService.update(dataSharingAccountDTO));
   }

   @PutMapping({"/updateStatus"})
   @ApiOperation(
      value = "更新账号状态",
      httpMethod = "PUT",
      response = Payload.class,
      notes = "更新账号状态"
   )
   public Payload<Boolean> updateStatus(@RequestBody DataSharingAccountStatusDTO dataSharingAccountDTO) {
      return new Payload(this.dataSharingAccountService.updateStatus(dataSharingAccountDTO));
   }

   @PostMapping({"/page"})
   @ApiOperation(
      value = "分页查询",
      httpMethod = "POST",
      response = Payload.class,
      notes = "分页查询"
   )
   public Payload<PageBean<DataSharingAccountPageDTO>> page(@RequestBody DataSharingAccountQueryDTO pageDTO) {
      return new Payload(this.dataSharingAccountService.accountByPage(pageDTO));
   }

   @GetMapping({"/getById/{accountId}"})
   @ApiOperation(
      value = "账号详情",
      httpMethod = "GET",
      response = Payload.class,
      notes = "账号详情"
   )
   public Payload<DataSharingAccountPageDTO> getByAccountId(@PathVariable("accountId") Long accountId) {
      return new Payload(this.dataSharingAccountService.getByAccountId(accountId));
   }

   @PostMapping({"/list"})
   @ApiOperation(
      value = "列表数据",
      httpMethod = "POST",
      response = Payload.class,
      notes = "列表数据"
   )
   public Payload<List<DataSharingAccountDTO>> list(@RequestBody DataSharingAccountPageDTO dataSharingAccountDTO) {
      if (dataSharingAccountDTO == null) {
         throw new ApplicationException("参数不能为空");
      } else if (dataSharingAccountDTO.getOrgId() == null) {
         throw new ApplicationException("使用组织orgId不能为空");
      } else {
         return new Payload(this.dataSharingAccountService.listBy(dataSharingAccountDTO));
      }
   }

   @GetMapping({"/updateUseStatus/{accountId}"})
   @ApiOperation(
      value = "更新账号已使用状态",
      httpMethod = "GET",
      response = Payload.class,
      notes = "更新账号已使用状态"
   )
   public Payload<Boolean> updateUseStatus(@PathVariable("accountId") Long accountId) {
      return new Payload(this.dataSharingAccountService.updateUseStatus(accountId));
   }

   @PutMapping({"/removeAccount/{accountId}"})
   @ApiOperation(
      value = "删除认证账号",
      httpMethod = "GET",
      response = Payload.class,
      notes = "删除认证账号"
   )
   public Payload<Boolean> removeAccount(@PathVariable("accountId") Long accountId) {
      return new Payload(this.dataSharingAccountService.removeAccount(accountId));
   }
}
