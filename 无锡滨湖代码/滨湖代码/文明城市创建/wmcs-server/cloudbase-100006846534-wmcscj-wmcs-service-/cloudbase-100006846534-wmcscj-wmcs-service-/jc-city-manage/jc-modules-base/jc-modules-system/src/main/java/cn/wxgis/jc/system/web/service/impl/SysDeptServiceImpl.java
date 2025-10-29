package cn.wxgis.jc.system.web.service.impl;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.enums.system.DeptTypeEnum;
import cn.wxgis.jc.common.result.TreeNode;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.security.annotation.DataScope;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysDictData;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysDeptSaveRequest;
import cn.wxgis.jc.system.resp.SysDeptResponse;
import cn.wxgis.jc.system.select.SysDeptSelect;
import cn.wxgis.jc.system.web.mapper.SysDeptMapper;
import cn.wxgis.jc.system.web.mapper.SysUserMapper;
import cn.wxgis.jc.system.web.service.SysDeptService;
import cn.wxgis.jc.system.web.service.entity.SysDeptEntity;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysUserMapper userMapper;
    public SysDeptServiceImpl(SysDeptMapper baseDao) {
        super(baseDao);
    }

    @Override
    @Transactional
    public int save(SysDeptSaveRequest request) {
        SysDept dept = SysDeptEntity.getSaveDept(request);
        /*** 设置父级部门、祖级信息 ***/
        this.setParentDept(dept, request.getParentId());
        return validResult(baseDao.insert(dept));
    }

    /**
     * 设置父级部门信息
     * @param dept
     * @param parentId
     */
    private void setParentDept(SysDept dept, String parentId) {
        if(StringUtils.isNotEmpty(parentId) && !"0".equals(parentId)) {
            SysDept parentDept = baseDao.selectById(parentId);
            dept.setParentId(parentId);
            dept.setAncestors(parentDept.getAncestors() + "," + dept.getId());
            dept.setType(parentDept.getType());
        } else {
            dept.setParentId("0");
            dept.setAncestors("0," + dept.getId());
        }
    }

    @Override
    @Transactional
    public int edit(SysDeptSaveRequest request) {
        SysDept bean = validDataNull(baseDao.selectById(request.getId()));
        SysDept data = SysDeptEntity.getUpdateDept(request, bean);
        /*** 设置父级部门、祖级信息 ***/
        this.setParentDept(data, request.getParentId());
        return validResult(baseDao.updateById(data));
    }

    @Override
    @Transactional
    public int delete(String id) {
        return baseDao.deleteById(id);
    }

    @Override
    public ExistsReturn exists(Map<String, String> existsParam) {
        ExistsReturn existsReturn = new ExistsReturn();
        boolean exists = false;
        //根据条件：名称、父级数据、id查询是否存在相同数据
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getName, existsParam.get(ExistsParam.name))
                .eq(StringUtils.isNotBlank(existsParam.get(ExistsParam.parentId)), SysDept::getParentId, existsParam.get(ExistsParam.parentId))
                .ne(StringUtils.isNotBlank(existsParam.get(ExistsParam.id)), SysDept::getId, existsParam.get(ExistsParam.id));
        List<SysDept> list = baseDao.selectList(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            exists = true;
            existsReturn.setMessage(String.format("部门名称:%s", existsParam.get(ExistsParam.name)) + GlobalStatusEnum.DATA_EXISTE.getMsg());
        }
        return existsReturn.setExists(exists);
    }


    @DataScope(deptAlias = "t")
    @Override
    public List<SysDeptResponse> listDeptTree(SysDeptSelect select) {
        log.info("===============" );
        log.info(JSON.toJSONString(select.getParams()));
        List<SysDeptResponse> volist = baseDao.voList(select);
        if (StringUtils.isEmpty(volist)) return volist;
        Map<String, SysDeptResponse> map = volist.stream().collect(Collectors.toMap(SysDept::getId, dept -> dept));
        volist.forEach(dept -> {
            getDeptByUsername(dept, map);
        });
        if (StringUtils.isNotBlank(select.getName())) {
            return volist;
        } else {
            TreeNode<SysDeptResponse> tree = new TreeNode<>(volist);
            List<SysDeptResponse> resultList = tree.buildThree();
            return resultList;
        }
    }

    public void getDeptByUsername(SysDeptResponse dept, Map<String, SysDeptResponse> map) {
        List<SysDeptResponse> userList = new ArrayList<>();
        List<SysDeptResponse> deptList = getParentDeptObject(dept, userList, map);
        Collections.reverse(deptList);
        String name = deptList.stream().map(SysDept::getName).collect(Collectors.joining("/"));
        dept.setAllname(name);
    }
    private List<SysDeptResponse> getParentDeptObject(SysDeptResponse sysDept, List<SysDeptResponse> userDeptList, Map<String, SysDeptResponse> map) {
        List<SysDeptResponse> deptList = new ArrayList<>();
        if (sysDept == null) {
            deptList.add(sysDept);
            return deptList;
        }
        //父节点为0代表是一级部门，直接返回
        if (sysDept.getParentId().equals(0)) {
            userDeptList.add(sysDept);
            return userDeptList;
        }
        SysDeptResponse deptByDeptId = map.get(sysDept.getParentId());
        userDeptList.add(sysDept);
        getParentDeptObject(deptByDeptId, userDeptList, map);
        return userDeptList;
    }

    @Override
    public SysDeptResponse findResponseById(String id) {
        SysDept dept = baseDao.selectById(id);
        SysDeptResponse deptResponse = new SysDeptResponse();
        BeanUtils.copyProperties(dept, deptResponse);
        if (StringUtils.isNotBlank(dept.getParentId()) && !Constant.DEFAULT_PID.equals(dept.getParentId())) {
            SysDept parentDept = baseDao.selectById(dept.getParentId());
            deptResponse.setParentName(StringUtils.isNotNull(parentDept) ? parentDept.getName() : null);
        }
        return deptResponse;
    }

    @Override
    public SysDeptResponse getAllNameByDept(String id) {
        SysDeptResponse dept = baseDao.findResponseById(id);
        if (StringUtils.isNull(dept)) return dept;
        List<SysDeptResponse> userList = new ArrayList<>();
        List<SysDeptResponse> deptList = getParentDeptObject(dept, userList);
        Collections.reverse(deptList);
        if (StringUtils.isEmpty(deptList)) return dept;
        String name = deptList.stream().map(SysDept::getName).collect(Collectors.joining("/"));
        dept.setAllname(name);
        return dept;
    }
    private List<SysDeptResponse> getParentDeptObject(SysDeptResponse sysDept, List<SysDeptResponse> userDeptList) {
        List<SysDeptResponse> deptList = new ArrayList<>();
        if (StringUtils.isNull(sysDept)) {
            return deptList;
        }
        //父节点为0代表是一级部门，直接返回
        if (sysDept.getParentId().equals(0)) {
            userDeptList.add(sysDept);
            return userDeptList;
        }
        SysDeptResponse deptByDeptId = baseDao.findResponseById(sysDept.getParentId());
        userDeptList.add(sysDept);
        getParentDeptObject(deptByDeptId, userDeptList);
        return userDeptList;
    }

    @Override
    public boolean hasChildById(String id) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getParentId, id).eq(SysDept::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        return baseDao.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean checkDeptExistUser(String id) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId, id).eq(SysUser::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        return userMapper.selectCount(queryWrapper) > 0;
    }

}
