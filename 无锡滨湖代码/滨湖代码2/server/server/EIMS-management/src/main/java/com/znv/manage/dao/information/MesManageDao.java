package com.znv.manage.dao.information;

import com.znv.manage.common.bean.information.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 信息管理
 *
 * @author chd
 * @date 2022/6/1
 */
@Repository
public interface MesManageDao {
    /**
     * 新增信息发布方式
     *
     * @param mesRelBean
     */
    void addMesRel(@Param("mesRelBean") MesRelBean mesRelBean);

    /**
     * 新增信息状态巡检
     *
     * @param mesReviewBean
     */
    void addMesReview(@Param("mesReviewBean") MesReviewBean mesReviewBean);

    /**
     * 编辑信息发布方式
     *
     * @param mesRelBean
     */
    void editMesRel(@Param("mesRelBean") MesRelBean mesRelBean);

    /**
     * 删除信息发布方式
     *
     * @param id
     */
    void delMesRel(@Param("id") String id);

    /**
     * 删除我的信息
     *
     * @param id
     */
    void delMesInfo(@Param("id") String id);

    /**
     * 查询信息发布方式
     *
     * @param text
     * @return
     */
    List<MesRelBean> getMesRel(@Param("text") String text);

    /**
     * 查询信息状态巡检
     *
     * @param getMesReviewBean
     * @return
     */
    List<MesReviewBean> getMesReview(@Param("getMesReviewBean") GetMesReviewBean getMesReviewBean);

    /**
     * 获取用户科室id
     *
     * @param userId
     * @return
     */
    String getPrecinctId(@Param("userId") String userId);

    /**
     * 根据可是名称获取用户科室id
     *
     * @param name
     * @return
     */
    List<String> getPrecinctIdByName(@Param("name") String name);

    /**
     * 根据科室id获取用户科室名称
     *
     * @param precinctId
     * @return
     */
    String getPrecinctNameById(@Param("precinctId") String precinctId);

    /**
     * 新增我的信息通讯录组
     *
     * @param mesGroupBean
     */
    void addMesGroup(@Param("mesGroupBean") MesGroupBean mesGroupBean);

    /**
     * 根据组id获取组成员id
     *
     * @param groupId
     * @return
     */
    List<String> getPersonIdById(@Param("groupId") String groupId);

    /**
     * 更新我的信息通讯录组
     *
     * @param mesGroupBean
     */
    void updateMesGroup(@Param("mesGroupBean") MesGroupBean mesGroupBean);

    /**
     * 删除我的信息通讯录组
     *
     * @param id
     */
    void delMesGroup(@Param("id") String id);

    /**
     * 删除我的信息通讯录组
     *
     * @param id
     */
    void delMesGroupPerson(@Param("id") String id);

    /**
     * 删除已删除的人的信息状态发送记录
     *
     * @param personIds
     */
    void delMesRelPerson(@Param("personIds") List<String> personIds);

    /**
     * 删除我的信息通讯录组
     *
     * @param mesGroupBeanPerson
     */
    void addMesGroupPerson(@Param("mesGroupBeanPerson") MesGroupBeanPerson mesGroupBeanPerson);

    /**
     * 根据id删除组员信息
     *
     * @param ids
     * @return
     */
    int delMesGroupPersonById(@Param("ids") List<String> ids);

    /**
     * 获取组员
     *
     * @param groupId
     * @return
     */
    List<MesGroupBeanPerson> getMesGroupPerson(@Param("groupId") String groupId);

    /**
     * 获取组
     *
     * @param userId
     * @param precinctId
     * @return
     */
    List<MesGroupBean> getMesGroup(@Param("userId") String userId,
                                   @Param("precinctId") String precinctId);

    /**
     * 新增我的信息
     *
     * @param mesInfoBean
     */
    void addMesInfo(@Param("mesInfoBean") MesInfoBean mesInfoBean);

    /**
     * 新增我的信息发送状态
     *
     * @param mesInfoBean
     */
    void addMesRelStatus(@Param("mesInfoBean") MesInfoBean mesInfoBean);

    /**
     * 根据id获取发布方式名称
     *
     * @param id
     * @return
     */
    String getNameById(@Param("id") String id);

    /**
     * 新增我的信息发送状态
     *
     * @param mesInfoBean
     */
    void editMesInfo(@Param("mesInfoBean") MesInfoBean mesInfoBean);

    /**
     * 根据id删除信息发送状态表
     *
     * @param id
     * @return
     */
    int delMesRelStatus(@Param("id") String id);

    /**
     * 根据id获取发布对象id
     *
     * @param id
     * @return
     */
    String getRelObjectIdById(@Param("id") String id);

    /**
     * 查询我的信息
     *
     * @param getMesInfoBean
     * @return
     */
    List<MesInfoBean> getMesInfo(@Param("getMesInfoBean") GetMesInfoBean getMesInfoBean);

    /**
     * 根据id查询我的信息
     *
     * @param id
     * @return
     */
    MesInfoBean getMesById(@Param("id") String id);

    /**
     * 查询我的信息发送状态
     *
     * @param getMesRelLogBean
     * @return
     */
    List<MesRelLogBean> getMesRelLog(@Param("getMesRelLogBean") GetMesRelLogBean getMesRelLogBean);

    /**
     * 查询我的信息流程
     *
     * @param getMesInfoBean
     * @return
     */
    List<MesInfoBean> getMesInfoProcess(@Param("getMesInfoBean") GetMesInfoBean getMesInfoBean);

    /**
     * 审核我的信息
     *
     * @param auditMesBean
     */
    void auditMesInfo(@Param("auditMesBean") AuditMesBean auditMesBean);

    /**
     * 修改信息发送状态
     *
     * @param id
     * @param status
     * @param personId
     */
    void updateMesRelStatus(@Param("id") String id, @Param("status") String status,
                            @Param("personId") String personId);

    /**
     * 查询我的信息发送状态表
     *
     * @param id
     * @return
     */
    List<MesRelStatusBean> getMesRelStatusBean(@Param("id") String id);

    /**
     * 查询我的信息发送状态
     *
     * @param id
     * @return
     */
    List<MesRelPersonBean> getMesRelStatusById(@Param("id") String id);
}
