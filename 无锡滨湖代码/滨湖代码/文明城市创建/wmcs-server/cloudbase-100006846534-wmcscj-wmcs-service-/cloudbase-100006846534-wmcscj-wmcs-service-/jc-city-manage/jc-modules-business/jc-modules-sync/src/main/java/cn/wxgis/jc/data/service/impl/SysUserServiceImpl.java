package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.SysUserMapper;
import cn.wxgis.jc.data.service.SysUserService;
import cn.wxgis.jc.system.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    public SysUserServiceImpl(SysUserMapper baseDao) {
        super(baseDao);
    }
}
