package com.znv.manage.service.tip;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.ToDoTipBean;

import java.util.List;

/**
 * 代办提示管理
 *
 * @author chd
 * @date 2022/6/9
 */
public interface TipManageService {

    /**
     * 查询代办提示
     *
     * @param userId
     * @return
     */
    List<ToDoTipBean> getTodoTip(String userId);

    /**
     * 查询APP待办提示
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @param text
     * @return
     */
    Result getAppTip(String userId, String text, Integer pageNum, Integer pageSize);

    /**
     * 处理APP待办提示
     *
     * @param id
     */
    void delAppTip(String id);
}
