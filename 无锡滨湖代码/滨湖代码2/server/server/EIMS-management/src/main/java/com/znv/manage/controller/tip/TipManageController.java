package com.znv.manage.controller.tip;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.ToDoTipBean;
import com.znv.manage.service.tip.TipManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 待办提示管理
 *
 * @author chd
 * @date 2022/6/9
 */
@RestController
@Api(tags = "待办提示管理")
@Slf4j
public class TipManageController {

    @Resource
    TipManageService tipManageService;

    /**
     * 查询待办提示
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询待办提示")
    @GetMapping(value = "/get-todo-tip")
    public List<ToDoTipBean> getTodoTip(@RequestParam(value = "userId") String userId) {
        return tipManageService.getTodoTip(userId);
    }

    /**
     * 查询APP待办提示
     *
     * @param userId
     * @param text
     * @param pageSize
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询APP待办提示")
    @GetMapping(value = "/get-app-tip")
    public Result getAppTip(@RequestParam(value = "userId") String userId,
                            @RequestParam(value = "text", required = false) String text,
                            @RequestParam(value = "pageNum", required = false) Integer pageNum,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return tipManageService.getAppTip(userId, text, pageNum, pageSize);
    }

    /**
     * 处理APP待办提示
     *
     * @param id
     */
    @ApiOperation(value = "处理APP待办提示")
    @GetMapping(value = "/deal-app-tip")
    public void delAppTip(@RequestParam(value = "id") String id) {
        tipManageService.delAppTip(id);
    }
}
