package com.znv.manage.controller.information;


import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.*;
import com.znv.manage.service.information.MesManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 信息管理
 *
 * @author chd
 * @date 2022/6/1
 */
@RestController
@Api(tags = "信息管理")
@Slf4j
public class MesManageController {


    @Resource
    MesManageService mesManageService;

    /**
     * 新增信息发布方式
     *
     * @param mesRelBean
     * @return
     */
    @ApiOperation(value = "新增信息发布方式")
    @PostMapping(value = "/add-mes-rel")
    public Result addMesRel(@RequestBody MesRelBean mesRelBean) {
        return mesManageService.addMesRel(mesRelBean);
    }

    /**
     * 编辑信息发布方式
     *
     * @param mesRelBean
     * @return
     */
    @ApiOperation(value = "编辑信息发布方式")
    @PostMapping(value = "/edit-mes-rel")
    public Result editMesRel(@RequestBody MesRelBean mesRelBean) {
        return mesManageService.editMesRel(mesRelBean);
    }

    /**
     * 删除信息发布方式
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除信息发布方式")
    @GetMapping(value = "/del-mes-rel")
    public Result delMesRel(@RequestParam(value = "id") String id) {
        return mesManageService.delMesRel(id);
    }

    /**
     * 删除我的信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除我的信息")
    @GetMapping(value = "/del-mes-info")
    public Result delMesInfo(@RequestParam(value = "id") String id) {
        return mesManageService.delMesInfo(id);
    }

    /**
     * 查询信息发布方式
     *
     * @param text
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询信息发布方式")
    @GetMapping(value = "/get-mes-rel")
    public Result getMesRel(@RequestParam(value = "text", required = false) String text,
                            @RequestParam(value = "pageNum", required = false) Integer pageNum,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return mesManageService.getMesRel(text, pageNum, pageSize);
    }

    /**
     * 查询状态巡检
     *
     * @param getMesReviewBean
     * @return
     */
    @ApiOperation(value = "查询状态巡检")
    @PostMapping(value = "/get-mes-review")
    public Result getMesReview(@RequestBody GetMesReviewBean getMesReviewBean) {
        return mesManageService.getMesReview(getMesReviewBean);
    }

    /**
     * 获取类型树结构
     *
     * @return
     */
    @ApiOperation(value = "获取类型树结构")
    @GetMapping(value = "/get-mes-ype-tree")
    public Result getMsgTypeTree() {
        return mesManageService.getMsgTypeTree();
    }

    /**
     * 新增我的信息通讯录组
     *
     * @param mesGroupBean
     * @return
     */
    @ApiOperation(value = "新增我的信息通讯录组")
    @PostMapping(value = "/add-mes-group")
    public Result addMesGroup(@RequestBody MesGroupBean mesGroupBean) {
        return mesManageService.addMesGroup(mesGroupBean);
    }

    /**
     * 修改我的信息通讯录组
     *
     * @param mesGroupBean
     * @return
     */
    @ApiOperation(value = "修改我的信息通讯录组")
    @PostMapping(value = "/update-mes-group")
    public Result updateMesGroup(@RequestBody MesGroupBean mesGroupBean) {
        return mesManageService.updateMesGroup(mesGroupBean);
    }

    /**
     * 获取我的信息通讯录组组员
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取我的信息通讯录组组员")
    @GetMapping(value = "/get-mes-group-person")
    public Result getMesGroupPerson(@RequestParam(value = "id") String id,
                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return mesManageService.getMesGroupPerson(id, pageNum, pageSize);
    }

    /**
     * 删除我的信息通讯录组
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除我的信息通讯录组")
    @GetMapping(value = "/del-mes-group")
    public Result delMesGroup(@RequestParam(value = "id") String id) {
        return mesManageService.delMesGroup(id);
    }

    /**
     * 新增我的信息通讯录组组员
     *
     * @param mesGroupBeanPerson
     * @return
     */
    @ApiOperation(value = "新增我的信息通讯录组组员")
    @PostMapping(value = "/add-mes-group-person")
    public Result addMesGroupPerson(@RequestBody MesGroupBeanPerson mesGroupBeanPerson) {
        return mesManageService.addMesGroupPerson(mesGroupBeanPerson);
    }

    /**
     * 删除我的信息通讯录组组员
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除我的信息通讯录组组员")
    @PostMapping(value = "/del-mes-group-person")
    public Result delMesGroupPerson(@RequestBody List<String> ids) {
        return mesManageService.delMesGroupPerson(ids);
    }

    /**
     * 查询所属科室通讯录组
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所属科室通讯录组")
    @GetMapping(value = "/get-mes-group")
    public Result getMesGroup(@RequestParam(value = "userId") String userId,
                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return mesManageService.getMesGroup(userId, pageNum, pageSize);
    }

    /**
     * 新增我的信息
     *
     * @param mesInfoBean
     * @return
     */
    @ApiOperation(value = "新增我的信息")
    @PostMapping(value = "/add-mes-info")
    public Result addMesInfo(@RequestBody MesInfoBean mesInfoBean) {
        return mesManageService.addMesInfo(mesInfoBean);
    }

    /**
     * 更新我的信息
     *
     * @param mesInfoBean
     * @return
     */
    @ApiOperation(value = "更新我的信息")
    @PostMapping(value = "/edit-mes-info")
    public Result editMesInfo(@RequestBody MesInfoBean mesInfoBean) {
        return mesManageService.editMesInfo(mesInfoBean);
    }

    /**
     * 查询我的信息
     *
     * @param getMesInfoBean
     * @return
     */
    @ApiOperation(value = "查询我的信息")
    @PostMapping(value = "/get-mes-info")
    public Result getMesInfo(@RequestBody GetMesInfoBean getMesInfoBean) {
        return mesManageService.getMesInfo(getMesInfoBean);
    }

    /**
     * 根据id查询我的信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询我的信息")
    @GetMapping(value = "/getMesById")
    public Result getMesById(@RequestParam(value = "id") String id) {
        return mesManageService.getMesById(id);
    }

    /**
     * 查询我的信息流程
     *
     * @param getMesInfoBean
     * @return
     */
    @ApiOperation(value = "查询我的信息流程")
    @PostMapping(value = "/get-mes-info-process")
    public Result getMesInfoProcess(@RequestBody GetMesInfoBean getMesInfoBean) {
        return mesManageService.getMesInfoProcess(getMesInfoBean);
    }

    /**
     * 审核我的信息
     *
     * @param auditMesBean
     * @return
     */
    @ApiOperation(value = "审核我的信息")
    @PostMapping(value = "/audit-mes-info")
    public Result auditMesInfo(@RequestBody AuditMesBean auditMesBean) {
        return mesManageService.auditMesInfo(auditMesBean);
    }

    /**
     * 查询发布日志
     *
     * @param getMesRelLogBean
     * @return
     */
    @ApiOperation(value = "查询发布日志")
    @PostMapping(value = "/get-mes-rel-log")
    public PageInfo<List<MesRelLogBean>> getMesRelLog(@RequestBody GetMesRelLogBean getMesRelLogBean) {
        return mesManageService.getMesRelLog(getMesRelLogBean);
    }

    /**
     * 查询人员发布状态
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询人员发布状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "我的信息id", dataType = "String", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页数", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "Integer", required = false)
    })
    @GetMapping(value = "/get-mes-rel-status")
    public PageInfo<List<MesRelPersonBean>> getMesRelStatus(@RequestParam(value = "id") String id,
                                                            @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return mesManageService.getMesRelStatus(id, pageNum, pageSize);
    }
}
