package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.synch.unify.po.UnifyUser;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import cn.wxgis.jc.system.resp.SysUserResponse;
import cn.wxgis.jc.system.select.SysUserSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

public interface SysUserService extends BaseService<SysUser> {

    /**
     * 新增
     * @param request 用户信息
     * @return 新增条数
     */
    int save(SysUserSaveRequest request);

    /**
     * 修改
     * @param request 用户信息
     * @return 修改条数
     */
    int edit(SysUserSaveRequest request);

    /**
     * 删除
     * @param id 用户id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 修改用户密码
     * @param user 用户
     * @param password 新密码
     * @return 修改条数
     */
    int updateMyPassword(SysUser user, String password);

    /**
     * 查询分页列表
     * @param select 查询参数
     * @return 用户分页列表
     */
    IPage<SysUserResponse> page(SysUserSelect select);

    /**
     * 查询详情
     * @param id 用户id
     * @return 用户详情
     */
    SysUserResponse findResponseById(String id);

    /**
     * 验证唯一性
     * @param existsParam 验证参数
     * @return 结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 根据用户名查询数据
     * @param userName 用户名
     * @return 用户信息
     */
    SysUser findByUserName(String userName);

    /**
     * 获取登陆用户
     * @param userName 用户名
     * @return 登陆用户信息
     */
    LoginUser getLoginUser(String userName);


    /**
     * 根据用户id查询政务微信用户
     */
    UnifyUser findUnifyUserByUsername(String username);
}
