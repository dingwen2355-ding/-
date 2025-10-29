package cn.wxgis.jc.common.web.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.util.ConvertUtils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    protected final M baseDao;
    public BaseServiceImpl(M baseDao) {
        this.baseDao = baseDao;
    }

    public T validDataNull(T t){
        if (StringUtils.isNull(t)) throw new ServiceException(ConstantForError.DATA_NOT_FOUND);
        return t;
    }

    public int validResult(Integer result){
        if (result <= 0) throw new ServiceException(ConstantForError.SAVE_DATA_ERROR);
        return result;
    }


    /**
     * 获取分页对象
     *
     * @param pageNum           当前页数
     * @param pageSize          显示条数
     * @param defaultOrderField 默认排序字段
     * @param isAsc             排序方式
     */
    protected IPage<T> getPage(Integer pageNum, Integer pageSize, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = pageNum;
        long limit = pageSize;

        Page<T> page = new Page<>(curPage, limit);

        //没有排序字段，则不排序
        if (StrUtil.isEmpty(defaultOrderField)) {
            return page;
        }
        //驼峰命名转下划线
        defaultOrderField = StringUtils.toUnderScoreCase(defaultOrderField);
        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }

    protected <T> IPage<T> getPageData(IPage page, Class<T> target) {
        List<T> targetList = ConvertUtils.sourceToTarget(page.getRecords(), target);
        page.setRecords(targetList);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(T entity) {
        return baseDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, 100);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(T entity) {
        return baseDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(T entity, Wrapper<T> updateWrapper) {
        return baseDao.update(entity, updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 30);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatchById(Collection<T> entityList, int batchSize) {
        if (StringUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try {
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return i;
    }

    @Override
    @Transactional
    public int deleteById(Serializable id) {
        return baseDao.deleteById(id);
    }

    @Override
    @Transactional
    public int delete(Wrapper<T> queryWrapper) {
        return baseDao.delete(queryWrapper);
    }

    @Override
    @Transactional
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return baseDao.deleteBatchIds(idList);
    }

    @Override
    public T selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public T selectOne(Wrapper<T> queryWrapper) {
        return baseDao.selectOne(queryWrapper);
    }

    @Override
    public List<T> selectList(Wrapper<T> queryWrapper) {
        return baseDao.selectList(queryWrapper);
    }


    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }


    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

}
