package cn.wxgis.jc.sync.web.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.sync.web.mapper.UnifyUserMapper;
import cn.wxgis.jc.sync.web.service.UnifyUserService;
import cn.wxgis.jc.synch.unify.po.UnifyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UnifyUserServiceImpl extends BaseServiceImpl<UnifyUserMapper, UnifyUser> implements UnifyUserService {
    public UnifyUserServiceImpl(UnifyUserMapper baseDao) {
        super(baseDao);
    }
}
