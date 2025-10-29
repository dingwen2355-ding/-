package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.PageRequest;
import com.grandlynn.spa.catalogue.common.constant.TodoBusinessConstant;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.SysRoleEnum;
import com.grandlynn.spa.catalogue.common.enums.TodoBusinessEnum;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.GetTablesByStatesRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.TodoBusinessDo;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;
import com.grandlynn.spa.catalogue.service.TodoBusinessService;
import com.grandlynn.util.pageHelper.PageBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TodoBusinessServiceImpl implements TodoBusinessService {
   private static final Logger log = LoggerFactory.getLogger(TodoBusinessServiceImpl.class);
   @Resource
   private SysAdminUserService userService;
   @Resource
   private CatalogueSysTableService catalogueSysTableService;

   public PageBean<TodoBusinessDo> pendingList(PageRequest request) {
      SysAdminUser user = SecurityUtil.currentUser();
      Long[] roleIds = (Long[])user.getRoles().stream().map(BaseEntity::getId).toArray((x$0) -> {
         return new Long[x$0];
      });
      List<Integer> approveState = this.getApproveState(roleIds);
      List<Integer> oaState = this.getOAState(roleIds);
      if (CollUtil.isEmpty(approveState) && CollUtil.isEmpty(oaState)) {
         return new PageBean();
      } else {
         Long[] orgIds = user.getOrgLimit() ? user.getOrgids() : null;
         GetTablesByStatesRequest gtRequest = new GetTablesByStatesRequest(approveState, oaState, user.getId(), orgIds);
         PageUtil.page(request.getPage(), request.getSize());
         List<CatalogueSysTableDO> tables = this.catalogueSysTableService.getTablesByStates(gtRequest);
         if (CollUtil.isEmpty(tables)) {
            return new PageBean();
         } else {
            List<TodoBusinessDo> todoList = (List)tables.stream().map((t) -> {
               return this.buildTodoBusiness(t);
            }).collect(Collectors.toList());
            return new PageBean(todoList);
         }
      }
   }

   private TodoBusinessDo buildTodoBusiness(CatalogueSysTableDO table) {
      TodoBusinessDo todoBusinessDo = new TodoBusinessDo();
      if (ApproveStateEnum.FIRST.getCode().equals(table.getTableState())) {
         todoBusinessDo.setTheme(TodoBusinessEnum.FIRST.getMsg());
         todoBusinessDo.setContent(TodoBusinessConstant.APPROVE);
      } else if (ApproveStateEnum.RECHECK.getCode().equals(table.getTableState())) {
         todoBusinessDo.setTheme(TodoBusinessEnum.SECOND.getMsg());
         todoBusinessDo.setContent(TodoBusinessConstant.APPROVE);
      } else {
         todoBusinessDo.setTheme(TodoBusinessEnum.PUBLISH.getMsg());
         todoBusinessDo.setContent(TodoBusinessConstant.PUBLISH);
      }

      SysAdminUser fuser = (SysAdminUser)this.userService.getById(table.getPublisher());
      if (null != fuser) {
         todoBusinessDo.setForwardUserId(fuser.getId());
         todoBusinessDo.setForwardUserIcon(fuser.getPortrait());
         todoBusinessDo.setForwardUserName(fuser.getName());
      }

      todoBusinessDo.setUpdatedTime(table.getUpdatedTime());
      return todoBusinessDo;
   }

   private List<Integer> getApproveState(Long[] roleIds) {
      List<Integer> states = new ArrayList();
      Long[] var3 = roleIds;
      int var4 = roleIds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Long roleId = var3[var5];
         if (SysRoleEnum.WBJ_SP.getCode().equals(roleId.intValue())) {
            states.add(ApproveStateEnum.FIRST.getCode());
         }

         if (SysRoleEnum.ZSJ_ADMIN.getCode().equals(roleId.intValue())) {
            states.add(ApproveStateEnum.RECHECK.getCode());
         }
      }

      return states;
   }

   private List<Integer> getOAState(Long[] roleIds) {
      List<Integer> states = new ArrayList();
      Long[] var3 = roleIds;
      int var4 = roleIds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Long roleId = var3[var5];
         if (SysRoleEnum.WBJ_BZ.getCode().equals(roleId.intValue())) {
            states.add(ApproveStateEnum.F_REJECT.getCode());
            states.add(ApproveStateEnum.R_REJECT.getCode());
         }
      }

      return states;
   }
}
