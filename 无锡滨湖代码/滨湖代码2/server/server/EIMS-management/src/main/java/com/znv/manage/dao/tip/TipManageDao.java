package com.znv.manage.dao.tip;

import com.znv.manage.common.bean.tip.MesInfoTipBean;
import com.znv.manage.common.bean.tip.ToDoTipBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代办提示管理
 *
 * @author chd
 * @date 2022/6/9
 */
@Repository
public interface TipManageDao {

    /**
     * 获取用户权限模块
     * @param userId
     * @return
     */
    List<String> getRoleModule(@Param("userId") String userId);

    /**
     * 获取待审核预案
     *
     * @return
     */
    List<ToDoTipBean> getAuditPlan();

    /**
     * 获取待审核信息
     *
     * @return
     */
    List<ToDoTipBean> getAuditMes();

    /**
     * 获取APP提示信息
     * @param userId
     * @param text
     * @return
     */
    List<MesInfoTipBean> getMesInfoByPersonId(@Param("userId") String userId, @Param("text")String text);

    /**
     * 处理APP待办提示
     * @param id
     */
    void updateMesStatus(@Param("id") String id);

    /**
     * 获取APP提示信息数量
     * @param userId
     * @return
     */
    Integer getStatus(@Param("userId") String userId);
}
