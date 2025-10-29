package com.znv.manage.service.information;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.*;

import java.util.List;

/**
 * 信息管理
 *
 * @author chd
 * @date 2022/6/1
 */
public interface MesManageService {

    /**
     * 新增信息发布方式
     *
     * @param mesRelBean
     * @return
     */
    Result addMesRel(MesRelBean mesRelBean);

    /**
     * 编辑信息发布方式
     *
     * @param mesRelBean
     * @return
     */
    Result editMesRel(MesRelBean mesRelBean);

    /**
     * 删除信息发布方式
     *
     * @param id
     * @return
     */
    Result delMesRel(String id);

    /**
     * 删除我的信息
     *
     * @param id
     * @return
     */
    Result delMesInfo(String id);

    /**
     * 查询信息发布方式
     *
     * @param text
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result getMesRel(String text, Integer pageNum, Integer pageSize);

    /**
     * 查询信息发布方式
     *
     * @param getMesReviewBean
     * @return
     */
    Result getMesReview(GetMesReviewBean getMesReviewBean);

    /**
     * 获取类型树结构
     *
     * @return
     */
    Result getMsgTypeTree();

    /**
     * 新增我的信息通讯录组
     *
     * @param mesGroupBean
     * @return
     */
    Result addMesGroup(MesGroupBean mesGroupBean);

    /**
     * 修改我的信息通讯录组
     *
     * @param mesGroupBean
     * @return
     */
    Result updateMesGroup(MesGroupBean mesGroupBean);


    /**
     * 获取我的信息通讯录组组员
     *
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result getMesGroupPerson(String id, Integer pageNum, Integer pageSize);

    /**
     * 删除我的信息通讯录组
     *
     * @param id
     * @return
     */
    Result delMesGroup(String id);

    /**
     * 新增我的信息通讯录组组员
     *
     * @param mesGroupBeanPerson
     * @return
     */
    Result addMesGroupPerson(MesGroupBeanPerson mesGroupBeanPerson);

    /**
     * 删除我的信息通讯录组组员
     *
     * @param ids
     * @return
     */
    Result delMesGroupPerson(List<String> ids);

    /**
     * 查询所属科室通讯录组
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result getMesGroup(String userId, Integer pageNum, Integer pageSize);

    /**
     * 新增我的信息
     *
     * @param mesInfoBean
     * @return
     */
    Result addMesInfo(MesInfoBean mesInfoBean);

    /**
     * 新增我的信息
     *
     * @param mesInfoBean
     * @return
     */
    Result editMesInfo(MesInfoBean mesInfoBean);

    /**
     * 新增我的信息
     *
     * @param getMesInfoBean
     * @return
     */
    Result getMesInfo(GetMesInfoBean getMesInfoBean);

    /**
     * 根据id查询我的信息
     *
     * @param id
     * @return
     */
    Result getMesById(String id);

    /**
     * 查询我的信息流程
     *
     * @param getMesInfoBean
     * @return
     */
    Result getMesInfoProcess(GetMesInfoBean getMesInfoBean);

    /**
     * 审核我的信息
     *
     * @param auditMesBean
     * @return
     */
    Result auditMesInfo(AuditMesBean auditMesBean);

    /**
     * 查询发布日志
     *
     * @param getMesRelLogBean
     * @return
     */
    PageInfo<List<MesRelLogBean>> getMesRelLog(GetMesRelLogBean getMesRelLogBean);

    /**
     * 查询发布状态
     *
     * @param id
     * @return
     */
    PageInfo<List<MesRelPersonBean>> getMesRelStatus(String id, Integer pageNum, Integer pageSize);
}
