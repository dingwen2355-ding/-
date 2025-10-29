package cn.wxgis.jc.data.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysUser;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {

    List<SysUser> userListByDeptIds(List<String> deptIds);

    List<SysUser> listByRegionDeptIds(List<String> deptIds);

    List<SysUser> listByFunDeptIds(List<String> deptIds);

    List<SysUser> listByDeptId(String deptId);

    List<SysUser> userListByMyDept(String deptId);

}
