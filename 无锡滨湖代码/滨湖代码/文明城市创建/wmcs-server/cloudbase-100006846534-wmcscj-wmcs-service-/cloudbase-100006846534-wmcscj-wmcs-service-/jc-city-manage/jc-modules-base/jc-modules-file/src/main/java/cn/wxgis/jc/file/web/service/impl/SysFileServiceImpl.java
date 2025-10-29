package cn.wxgis.jc.file.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.file.web.mapper.SysFileMapper;
import cn.wxgis.jc.file.web.service.SysFileService;
import cn.wxgis.jc.system.po.SysFile;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    public SysFileServiceImpl(SysFileMapper baseDao) {
        super(baseDao);
    }

    @Override
    public List<SysFile> listByIds(List<String> ids) {
        if (CollectionUtil.isEmpty(ids) || ids.size() <= 0) return null;
        LambdaQueryWrapper<SysFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysFile::getId, ids);
        return baseDao.selectList(queryWrapper);
    }
}
