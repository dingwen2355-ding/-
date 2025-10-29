package com.znv.manage.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.bean.user.*;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.PrivilegeDao;
import com.znv.manage.dao.UserServiceMapper;
import com.znv.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    UserServiceMapper userServiceMapper;
    @Resource
    PrivilegeDao privilegeDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insertUser(User user) {
        userServiceMapper.insertUser(user);
    }

    @Override
    public void updateUser(String userId, String userName, String password, String departmentId, String roleId,
                           String precinctTreeId,
                           String userType, String employeeId, String trueName,
                           String mobilePhone, String email, String phone,
                           String address, String userState, Integer errLoginTimes, Date updateTime,
                           String description, String adminUser, String fax,
                           String gender, String userLevel, String loginClientType,
                           String userUrl, String systemFlag, String streetId) {
        userServiceMapper.updateUser(userId, userName, password, departmentId, roleId, precinctTreeId, userType, employeeId,
                trueName, mobilePhone, email, phone, address, userState, errLoginTimes, updateTime,
                description, adminUser, fax, gender, userLevel, loginClientType, userUrl, systemFlag, streetId);
    }

    @Override
    public void deleteUsers(String userIds, String userName, String password, String departmentId, String userType, String employeeId, String trueName, String mobilePhone, String email, String phone, String address, String userState, Integer errLoginTimes, Date updateTime, String description, String adminUser, String fax, String gender, String userLevel, String loginClientType, String userUrl, String systemFlag, Date createTime) {
        userServiceMapper.deleteUsers(userIds, userName, password, departmentId, userType,
                employeeId, trueName, mobilePhone, email, phone, address, userState, errLoginTimes, updateTime,
                description, adminUser, fax, gender, userLevel, loginClientType, userUrl, systemFlag,
                createTime);
    }

    @Override
    public List<User> queryUsers(String userIds, String userName, String password, String precinctId, String departmentId, String precinctName, String userType, String employeeId, String trueName, String mobilePhone, String email, String phone, String address, String userState, Integer errLoginTimes, Date updateTime, String description, String adminUser, String fax, String gender, String userLevel, String loginClientType, String userUrl, String systemFlag, Date createTime, String roleId) {
        return userServiceMapper.queryUsers(userIds, userName, password, precinctId, departmentId, precinctName,
                userType, employeeId, trueName, mobilePhone, email, phone, address, userState, errLoginTimes,
                updateTime, description, adminUser, fax, gender, userLevel, loginClientType,
                userUrl, systemFlag, createTime, roleId);
    }

    @Override
    public List<User> queryUserByName(
            String userName
    ) {
        return userServiceMapper.queryUsers(null, userName, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null);
    }

    @Override
    public List<User> queryUserById(
            String userId
    ) {
        return userServiceMapper.queryUsers(userId, null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null);
    }

    @Override
    public List<Module> queryRelatedModules(String userId) {
        return userServiceMapper.queryRelatedModules(Integer.parseInt(userId));
    }

    @Override
    public void addUserRoles(String userId, String roleIds) {
        if (roleIds != null && !"".equals(roleIds)) {
            List<String> roleIdList = Arrays.asList(roleIds.split(","));
            userServiceMapper.addUserRoles(userId, roleIdList);
        }
    }

    @Override
    public void removeUserRoles(String userId, String roleIds) {
        userServiceMapper.removeUserRoles(userId, roleIds);
    }

    @Override
    public List<Role> queryUserRoles(String userId) {
        return userServiceMapper.queryUserRoles(userId);
    }

    @Override
    public void changePassword(String userId, String newPassword) {
        userServiceMapper.changePassword(userId, newPassword);
    }

    @Override
    public List<Map<String, String>> queryDepartment(
            String id, String department,
            String type, String address, String precinct_id) {
        return userServiceMapper.queryDepartment(id, department, type, address, precinct_id);
    }

    @Override
    public PowerVO getToken(String tockenheader) {
        Integer userID = (Integer) redisTemplate.opsForValue().get("tockenheader:" + tockenheader);
        if (userID == null) {
            return null;
        }
        List<Map<String, String>> token = userServiceMapper.getToken(userID);
        PowerVO powerVO = new PowerVO();
        Map<String, String> map = new HashMap<>();
        Map<String, String> m = token.get(0);
        map.put("roleId", m.get("roleId"));
        map.put(("roleName"), m.get("roleName"));
        map.put("upRoleId", m.get("upRoleId"));
        map.put("description", m.get("description"));
        map.put("dataPermission", m.get("dataPermission"));
        List<Map<String, String>> mapList = new ArrayList<>();
        for (Map<String, String> stringMap : token) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id", stringMap.get("id"));
            map1.put("moduleName", stringMap.get("moduleName"));
            map1.put("moduleUrl", stringMap.get("moduleUrl"));
            map1.put("description", stringMap.get("description"));
            map1.put("upModuleId", stringMap.get("upModuleId"));
            mapList.add(map1);
        }
        System.out.println(m);
        powerVO.setUserName(m.get("userName"));
        String departmentId = m.get("departmentId");
        System.out.println(departmentId);
        powerVO.setDepartmentId(departmentId);
        powerVO.setDepartmentName(m.get("departmentName"));
        powerVO.setRoleName(m.get("roleName"));
        powerVO.setRoles(map);
        powerVO.setModules(mapList);
        return powerVO;
    }

    //@Override
    public List<Map<String, Object>> getUserInfoByUserId(String userId, String operationUserId) {
        try {

            return userServiceMapper.getUserInfoByUserId(userId, operationUserId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ResultCodeEnum.SYSTEMERROR.getCode(), "getUserInfoByUserId wrong");
        }
    }

    //@Override
    public String judgeRoleLevelLegalCreatUser(String userid, String operationUserid, String roleId) {
        if (null == roleId) {
            return null;//没有编辑角色，无需角色鉴权
        }

        //根据用户userId查询角色id
        List<Map<String, Object>> listRoleId = userServiceMapper.getUserInfoByUserId(operationUserid, null);
        if (listRoleId.size() != 0) {
            String roleIdUser = listRoleId.get(0).get("roleId").toString();
            //编辑时,如果是用户自己编辑自己且角色id不改动时--角色无需鉴权
            if (operationUserid.equals(userid) && roleIdUser.equals(roleId)) {
                return null;
            }
            //比较两角色id等级关系
            String levelResult = userServiceMapper.compareRoleLevelByRoleId(roleIdUser, roleId);
            if ("3".equals(levelResult)) {
                return null;//有权限
            }
            return "无权限";//无权限
        } else {
            return "查询不到该用户[" + operationUserid + "]的角色信息";//查询不到角色，默认无权限
        }
    }

    public String judgeRoleLevelLegalCreatUserUpdateUser(String userid, String operationUserid, String roleId) {
        if (null == roleId) {
            return null;//没有编辑角色，无需角色鉴权
        }

        //根据用户userId查询角色id
        List<Map<String, Object>> listRoleId = userServiceMapper.getUserInfoByUserId(operationUserid, null);
        if (listRoleId.size() != 0) {
            String roleIdUser = listRoleId.get(0).get("roleId").toString();
            //编辑时,如果是用户自己编辑自己且角色id不改动时--角色无需鉴权
            if (operationUserid.equals(userid) && roleIdUser.equals(roleId)) {
                return null;
            }
            //比较两角色id等级关系
            String levelResult = userServiceMapper.compareRoleLevelByRoleId(roleIdUser, roleId);
            if ("3".equals(levelResult)) {
                return null;//有权限
            }
            return "无权限";//无权限
        } else {
            return "查询不到该用户[" + operationUserid + "]的角色信息";//查询不到角色，默认无权限
        }
    }

    //@Override
    public boolean isHavePrivilege(String creatUserTree, String newUserTree, String streetId) {
        if (null == newUserTree || newUserTree.isEmpty()) {
            return true;
        }
        if (null == creatUserTree || creatUserTree.isEmpty()) {
            return false;//权限超限
        }
        String[] strArrCreatUser = creatUserTree.split(",");
        String[] strArrNewUser = newUserTree.split(",");
        for (int i = 0; i < strArrNewUser.length; i++) {
            if (streetId.equals(strArrNewUser[i])) {
                continue;
            }
            boolean flag = false;
            for (int j = 0; j < strArrCreatUser.length; j++) {
                if (strArrNewUser[i].equals(strArrCreatUser[j])) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //log.info("strArrNewUser tree id["+strArrNewUser[i]+"] not in");
                return false;//权限超限
            }
        }
        return true;
    }

    //@Override
    public String judgePrivilege(String userId, String operationUserId, String precinctTreeId) {
        String result = null;
        //String token = request.getHeader("token");
        String streetId = null;

        if (userId != null) {//编辑用户时进来
            List<Map<String, Object>> listTmp = userServiceMapper.getUserInfoByUserId(userId, null);
            String roleId = listTmp.get(0).get("roleId").toString();
            streetId = listTmp.get(0).get("streetId").toString();

            if (RoleEnum.SUPERADMIN.getId().equals(roleId)
                    || RoleEnum.PRECINCTADMIN.getId().equals(roleId)) {
                return null; //超级管理员和街镇管理员进行资源树权限编辑--不受限
            }
        }

        //超级管理员资源管理编辑用户资源不受限
        if (operationUserId != null) {//编辑用户时进来
            List<Map<String, Object>> listTmp = userServiceMapper.getUserInfoByUserId(operationUserId, null);
            String roleId = listTmp.get(0).get("roleId").toString();
            if (RoleEnum.SUPERADMIN.getId().equals(roleId)) {
                return null; //超级管理员进行资源树权限编辑--不受限
            }
        }

        if (true/*token != null && !token.isEmpty() */) {
            //String userId = redisService.getUserId(token);
            List<Map<String, Object>> list = privilegeDao.getUserPricinctTree(operationUserId);
            String creatUserTree = (list.size() == 0 ? "" : list.get(0).get("precinctTreeId").toString());
            String newUserTree = precinctTreeId;
            if (!isHavePrivilege(creatUserTree, newUserTree, streetId)) {
                result = "权限超限，创建者拥有的资源[" + creatUserTree + "]要包含当前用户拥有的资源[" + newUserTree + "]！";
            }
        } else {
            result = "用户操作需要携带token!";
        }
        return result;
    }

    @Override
    public String getUserIdsByPrivilege(String operationUserid, String userIdsOld) {

        String userIds = null;

        //超级管理员啥也不过滤
        String roleId = getRoleId(operationUserid);
        if (RoleEnum.SUPERADMIN.getId().equals(roleId)) {
            return userIdsOld;
        }
        //街镇管理员查看该街镇所有用户
        else if (RoleEnum.PRECINCTADMIN.getId().equals(roleId)) {
            userIds = userServiceMapper.getUserIdsByStreetId(operationUserid);
        }
        //网格管理员查看自己所属区域所有用户
        else if (RoleEnum.GRIDADMIN.getId().equals(roleId)) {
            List<String> strList = new ArrayList<>();
            List<Map<String, Object>> listTree = privilegeDao.getUserPricinctTree(operationUserid);
            String operateUserTree = (listTree.size() == 0 ? "" : listTree.get(0).get("precinctTreeId").toString());
            if ("".equals(operateUserTree)) {
                userIds = getUserIdsByOperateId(operationUserid);//看见自己以及自己创建的用户
            } else {
                List<Map<String, Object>> list = userServiceMapper.getUserInfoByUserIdSameStreet(operationUserid);
                for (int i = 0; i < list.size(); i++) {
                    String userIdTmp = list.get(i).get("userId").toString();
                    String precinctTreeIdTmp = list.get(i).get("precinctTreeId").toString();
                    String streetId = userServiceMapper.getStreetIdByUserId(operationUserid);
                    if (!"".equals(precinctTreeIdTmp) && isHavePrivilege(operateUserTree, precinctTreeIdTmp, streetId)) {
                        strList.add(userIdTmp);
                    }
                }
                userIds = String.join(",", strList);
                userIds = userIds + "," + getUserIdsByOperateId(operationUserid);//再加上自己以及自己创建的用户
            }
        }
        //其他人看见自己以及自己新建用户
        else {
            userIds = getUserIdsByOperateId(operationUserid);
        }

        return userIds;
    }


    private String getUserIdsByOperateId(String operationUserid) {
        String userIds = null;
        List<Map<String, Object>> list = userServiceMapper.getUserInfoByUserId(null, operationUserid);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                userIds = list.get(i).get("userId").toString();
            } else {
                userIds = userIds + "," + list.get(i).get("userId").toString();
            }
        }
        userIds = (userIds == null ? operationUserid : (operationUserid + "," + userIds));

        return userIds;
    }

    private String getRoleId(String userId) {
        List<Map<String, Object>> listRoleId = userServiceMapper.getUserInfoByUserId(userId, null);
        if (listRoleId.size() == 0) {
            return "";
        }
        return listRoleId.get(0).get("roleId").toString();
    }

    @Override
    public List<String> querySameStreetUser(String userId) {
        return userServiceMapper.querySameStreetUser(userId);
    }

    @Override
    public List<String> queryBelongToAreaUser(String userId) {
        return userServiceMapper.queryBelongToAreaUser(userId);
    }

    @Override
    public String controlPermissionCreatUser(String userId, String operationUserid, String roleId, String precinctTreeId, String streetId) {

        String result = null;

        //创建街镇管理员用户时，判断该街镇管理员是否已存在
        // 青岛系统允许存在多个街镇管理员
//        if(null==userId && RoleEnum.PRECINCTADMIN.getId().equals(roleId) && !"".equals(streetId)){
//            int num = userServiceMapper.getPrecinctAdminNum(streetId,RoleEnum.PRECINCTADMIN.getId());
//            if(num>=1){
//                result="该街镇已经存在街镇管理员角色";
//            }
//        }

        //判断角色等级是否合法
        if (null == result) {
            result = judgeRoleLevelLegalCreatUser(userId, operationUserid, roleId);
        }

        //判断操作者的资源要包含被操作用户的资源，否则报错
        if (null == result) {
            result = judgePrivilege(userId, operationUserid, precinctTreeId);
        }

        return result;
    }

    @Override
    public String controlPermissionUpdateUser(String userId, String operationUserid, String roleId, String precinctTreeId) {

        //判断角色等级是否合法
        String result = judgeRoleLevelLegalCreatUserUpdateUser(userId, operationUserid, roleId);

        if (null == result) {

            //不可编辑相同角色用户资源树
            if (!operationUserid.equals(userId)
                    && getRoleId(operationUserid).equals(getRoleId(userId))) {
                result = "不可编辑相同角色用户信息";
            } else {
                //判断操作者的资源要包含被操作用户的资源，否则报错
                result = judgePrivilege(userId, operationUserid, precinctTreeId);
            }
        }

        return result;
    }

    @Override
    public List<AuthToken> queryAuthToken(String token) {
        return userServiceMapper.queryAuthToken(token);
    }

    @Override
    public void updateUserState(String userId, String userState, String errLoginTimes) {
        userServiceMapper.updateUserState(userId, userState, errLoginTimes);
    }

    @Override
    public Result userWithPath(String trueName, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(userServiceMapper.selectUserWithPrecinctPath(trueName)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
