package com.znv.manage.dao.plan;

import com.znv.manage.common.bean.plan.PlanLinkBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgPlanLink表数据库访问层
 *
 * @author 0049003788
 * @since 2023-05-19 13:48:39
 */
public interface PlanLinkDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PlanLinkBean queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param planLinkBean 查询条件
     * @return 总行数
     */
    long count(PlanLinkBean planLinkBean);

    /**
     * 新增数据
     *
     * @param planLinkBean 实例对象
     * @return 影响行数
     */
    int insert(PlanLinkBean planLinkBean);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PlanLink> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PlanLinkBean> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PlanLink> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PlanLinkBean> entities);

    /**
     * 修改数据
     *
     * @param planLinkBean 实例对象
     * @return 影响行数
     */
    int update(PlanLinkBean planLinkBean);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteBatch(@Param("ids") String ids);

    int deleteByLinkId(@Param("id") String id, @Param("type") String type);

    /**
     * 条件查询
     *
     * @param id
     * @param resourceType 资源类型 1:专家;2:队伍;3:物资;4:场所
     * @param linkId       预案(t_cfg_plan_info)id/事件(t_cfg_event)id
     * @param type         关联主表类型:1:预案;2:事件
     * @param resourceId   关联的资源id
     * @return 实例列表
     */
    List<PlanLinkBean> queryByCondition(@Param("id") Integer id,
                                        @Param("resourceType") String resourceType,
                                        @Param("linkId") String linkId,
                                        @Param("type") String type,
                                        @Param("resourceId") String resourceId);

}

