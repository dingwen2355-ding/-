package com.znv.manage.service.impl.tip;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.tip.MesInfoTipBean;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.ToDoTipBean;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.dao.tip.TipManageDao;
import com.znv.manage.service.tip.TipManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 代办提示管理
 *
 * @author chd
 * @date 2022/6/9
 */
@Service
@Slf4j
public class TipManageServiceImpl implements TipManageService {

    @Resource
    TipManageDao tipManageDao;

    public static final String PLAN_ROLE = "应急预案";

    public static final String PLAN_ROUTER = "emergencyPlan";

    public static final String MES_ROLE = "信息审核";

    public static final String MES_ROUTER = "AuditInformation";

    @Override
    public List<ToDoTipBean> getTodoTip(String userId) {
        List<ToDoTipBean> toDoTipBeans = new ArrayList();
        if (StringUtils.isEmpty(userId)) {
            log.error("userId is null");
            throw new BusinessException("userId is null");
        }
        List<String> modules = tipManageDao.getRoleModule(userId);
        if (CollectionUtils.isEmpty(modules)) {
            return toDoTipBeans;
        }

        if (modules.contains(PLAN_ROLE)) {
            toDoTipBeans.addAll(tipManageDao.getAuditPlan());
        }
        if (modules.contains(MES_ROLE)) {
            toDoTipBeans.addAll(tipManageDao.getAuditMes());
        }
        toDoTipBeans.sort(new Comparator<ToDoTipBean>() {
            @Override
            public int compare(ToDoTipBean o1, ToDoTipBean o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return toDoTipBeans;
    }

    @Override
    public Result getAppTip(String userId, String text, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        if (StringUtils.isEmpty(userId)) {
            log.error("userId is null");
            throw new BusinessException("userId is null");
        }

        List<MesInfoTipBean> mesInfoTipBeans = tipManageDao.getMesInfoByPersonId(userId, text);
        Integer tipTotal = tipManageDao.getStatus(userId);
        if(tipTotal == null) {
            tipTotal = 0;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mesInfoTipBeans", new PageInfo(mesInfoTipBeans));
        jsonObject.put("tipTotal", tipTotal);
        return new Result(jsonObject);
    }

    @Override
    public void delAppTip(String id) {
        tipManageDao.updateMesStatus(id);
    }
}
