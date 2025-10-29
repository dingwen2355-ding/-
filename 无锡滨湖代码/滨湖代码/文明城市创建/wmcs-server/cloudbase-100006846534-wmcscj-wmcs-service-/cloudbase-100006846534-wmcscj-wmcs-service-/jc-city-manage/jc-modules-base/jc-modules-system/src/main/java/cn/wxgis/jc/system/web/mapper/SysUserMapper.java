package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.synch.unify.po.UnifyUser;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.resp.SysUserResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface SysUserMapper extends BaseDao<SysUser> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param queryWrapper 搜索条件
     * @return 用户列表
     */
    IPage<SysUserResponse> selectPageCustom(IPage<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUserResponse> queryWrapper, @Param("params")Map<String, Object> params);


    /**
     * 根据用户id查询政务微信用户
     */
    UnifyUser findUnifyUserByUsername(@Param("username") String username);
}
