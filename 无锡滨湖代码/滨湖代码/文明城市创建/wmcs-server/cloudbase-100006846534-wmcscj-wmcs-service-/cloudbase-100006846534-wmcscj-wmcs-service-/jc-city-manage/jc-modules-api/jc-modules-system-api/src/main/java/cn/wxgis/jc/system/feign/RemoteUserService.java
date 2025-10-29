package cn.wxgis.jc.system.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.constant.ServiceNameConstants;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.system.feign.factory.RemoteUserFallbackFactory;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户服务
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{

    /**
     * 通过用户名查询用户信息
     *
     * @param userName 用户名
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.USER_FIND_USERNAME)
    JsonResult<LoginUser> findByUserName(@RequestParam("userName") String userName, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 新增用户
     * @param user 用户信息
     * @param inner 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.USER_SAVE)
    JsonResult<Integer> saveUser(@RequestBody SysUserSaveRequest user, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

}
