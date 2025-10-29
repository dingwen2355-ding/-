package com.znv.manage.dao;

import com.znv.manage.common.bean.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgDict表数据库访问层
 *
 * @author 0049003788
 * @since 2022-11-11 13:41:48
 */
public interface DictDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dict queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param dict 查询条件
     * @return 总行数
     */
    long count(Dict dict);

    /**
     * 新增数据
     *
     * @param dict 实例对象
     * @return 影响行数
     */
    int insert(Dict dict);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Dict> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Dict> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Dict> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Dict> entities);

    /**
     * 修改数据
     *
     * @param dict 实例对象
     * @return 影响行数
     */
    int update(Dict dict);

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

    List<Dict> queryByCondition(@Param("id") Integer id,
                                @Param("dictName") String dictName,
                                @Param("dictOrder") Integer dictOrder,
                                @Param("dictType") String dictType,
                                @Param("parentId") Integer parentId,
                                @Param("typeId") Integer typeId);

}

