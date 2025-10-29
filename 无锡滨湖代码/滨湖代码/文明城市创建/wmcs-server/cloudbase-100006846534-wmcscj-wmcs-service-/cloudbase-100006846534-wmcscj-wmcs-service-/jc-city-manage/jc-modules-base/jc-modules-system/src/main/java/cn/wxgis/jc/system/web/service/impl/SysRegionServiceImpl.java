package cn.wxgis.jc.system.web.service.impl;

import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.TreeNode;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.synch.unify.po.UnifyDept;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysRegion;
import cn.wxgis.jc.system.req.SysRegionSaveRequest;
import cn.wxgis.jc.system.resp.SysRegionResponse;
import cn.wxgis.jc.system.select.SysRegionSelect;
import cn.wxgis.jc.system.web.mapper.SysRegionMapper;
import cn.wxgis.jc.system.web.service.SysDeptService;
import cn.wxgis.jc.system.web.service.SysRegionService;
import cn.wxgis.jc.system.web.service.entity.SysRegionEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysRegionServiceImpl extends BaseServiceImpl<SysRegionMapper, SysRegion> implements SysRegionService{

    @Autowired
    private SysDeptService deptService;
    public SysRegionServiceImpl(SysRegionMapper baseDao) {
        super(baseDao);
    }

    @Override
    @Transactional
    public int save(SysRegionSaveRequest request) {
        SysRegion data = SysRegionEntity.getSaveRegion(request);
        return validResult(baseDao.insert(data));
    }

    @Override
    @Transactional
    public int edit(SysRegionSaveRequest request) {
        SysRegion bean = validDataNull(baseDao.selectById(request.getId()));
        SysRegion data = SysRegionEntity.getUpdateRegion(request, bean);
        return validResult(baseDao.updateById(data));
    }

    @Override
    @Transactional
    public int delete(String id) {
        SysRegion bean = validDataNull(baseDao.selectById(id));
        bean.setDelFlag(DelFlagEnum.DELETE.getCode());
        return validResult(baseDao.updateById(bean));
    }

    @Override
    public int updateByDept() {
        int result = 0;
        List<SysRegion> updateList = new ArrayList<>();
        // 部门列表
        List<SysDept> deptList = deptService.selectList(null);
        if (StringUtils.isEmpty(deptList)) return result;
        // 街道列表
        List<SysRegion> regionList = baseDao.selectList(null);
        if (StringUtils.isEmpty(regionList)) return result;

        // 将部门列表专场以名称-> 对象对应的map对象
        Map<String, SysDept> map = new HashMap<>();
        for (SysDept dept : deptList) {
            map.put(dept.getName(), dept);
        }
        regionList.forEach(r -> {
            String name = r.getRegionName();
            SysDept sysDept = map.get(name);
            if (StringUtils.isNull(sysDept) || !sysDept.getName().contains(r.getRegionName())) return;
            r.setDeptId(sysDept.getId());
            updateList.add(r);
        });
        if (StringUtils.isNotEmpty(updateList)) {
            result = this.updateBatchById(updateList);
        }
        return result;
    }

    @Override
    public ExistsReturn exists(Map<String, String> existsParam) {
        ExistsReturn existsReturn = new ExistsReturn();
        boolean exists = false;
        LambdaQueryWrapper<SysRegion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRegion::getRegionName, existsParam.get(ExistsParam.regionName))
                .eq(SysRegion::getRegionCode, existsParam.get(ExistsParam.regionCode))
                .eq(StringUtils.isNotBlank(existsParam.get(ExistsParam.parentId)), SysRegion::getParentId, existsParam.get(ExistsParam.parentId))
                .ne(StringUtils.isNotBlank(existsParam.get(ExistsParam.id)), SysRegion::getId, existsParam.get(ExistsParam.id));
        List<SysRegion> list = baseDao.selectList(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            exists = true;
            existsReturn.setMessage(String.format("区划名称:%s", existsParam.get(ExistsParam.regionName)) + String.format(",区划编码:%s", existsParam.get(ExistsParam.regionCode)) + GlobalStatusEnum.DATA_EXISTE.getMsg());
        }
        return existsReturn.setExists(exists);
    }

    @Override
    public List<SysRegionResponse> listRegionTree(SysRegionSelect select) {
        List<SysRegionResponse> volist = baseDao.voList(select);
        if (StringUtils.isNotBlank(select.getRegionName())) {
            return volist;
        } else {
            TreeNode<SysRegionResponse> tree = new TreeNode<>(volist);
            List<SysRegionResponse> resultList = tree.buildThree();
            return resultList;
        }
    }

    @Override
    public SysRegion findByRegionCode(String regionCode) {
        LambdaQueryWrapper<SysRegion> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRegion::getRegionCode, regionCode);
        return baseDao.selectOne(queryWrapper);
    }

    @Override
    public SysRegion findByRegionName(String regionName) {
        LambdaQueryWrapper<SysRegion> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRegion::getRegionName, regionName);
        return baseDao.selectOne(queryWrapper);
    }

    @Override
    public SysRegionResponse findResponseById(String id) {
        SysRegionResponse response = new SysRegionResponse();
        SysRegion region = baseDao.selectById(id);
        BeanUtils.copyProperties(region, response);
        if (StringUtils.isNotBlank(region.getParentId()) && !"0".equals(region.getParentId())) {
            SysRegion parentRegion = baseDao.selectById(region.getParentId());
            response.setParentName(parentRegion.getRegionName());
        }
        return response;
    }
}
