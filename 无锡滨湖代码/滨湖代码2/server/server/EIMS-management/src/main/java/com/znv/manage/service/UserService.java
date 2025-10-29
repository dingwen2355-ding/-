package com.znv.manage.service;


import com.znv.manage.bean.user.*;
import com.znv.manage.common.bean.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    public void insertUser(User user);

    public void updateUser(
            String userId,
            String userName,
            String password,
            String departmentId,
            String roleId,
            String precinctTreeId,
            String userType,
            String employeeId,
            String trueName,
            String mobilePhone,
            String email,
            String phone,
            String address,
            String userState,
            Integer errLoginTimes,
            Date updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            String userUrl,
            String systemFlag,
            String streetId
    );

    public void deleteUsers(
            String userIds,
            String userName,
            String password,
            String usergroupId,
            String userType,
            String employeeId,
            String trueName,
            String mobilePhone,
            String email,
            String phone,
            String address,
            String userState,
            Integer errLoginTimes,
            Date updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            String userUrl,
            String systemFlag,
            Date createTime
    );

    public List<User> queryUsers(
            String userIds,
            String userName,
            String password,
            String precinctId,
            String usergroupId,
            String precinctName,
            String userType,
            String employeeId,
            String trueName,
            String mobilePhone,
            String email,
            String phone,
            String address,
            String userState,
            Integer errLoginTimes,
            Date updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            String userUrl,
            String systemFlag,
            Date createTime,
            String roleId
    );

    public List<User> queryUserByName(
            String userName
    );

    public List<User> queryUserById(
            String userId
    );

    public List<Module> queryRelatedModules(
            String userId
    );

    public void addUserRoles(
            String userId,
            String roleIds
    );

    public void removeUserRoles(
            String userId,
            String roleIds
    );

    public List<Role> queryUserRoles(
            String userId
    );

    public void changePassword(String userName, String newPassword);

    List<Map<String, String>> queryDepartment(String id, String department, String type, String address, String precinct_id);

    PowerVO getToken(String tockenheader);

    //List<Map<String, Object>>  getUserInfoByUserId(String userId,String operationUserid);

    //String judgeRoleLevelLegal(String userId,String roleId);

    //boolean isHavePrivilege(String creatUserTree,String newUserTree);
    //String judgePrivilege (String userId,String operationUserId,String precinctTreeId);
    String getUserIdsByPrivilege(String operationUserid,String userIdsOld);

    List<String> querySameStreetUser(String userId);

    List<String> queryBelongToAreaUser(String userId);
    String controlPermissionCreatUser(String userId,String operationUserid,String roleId,String precinctTreeId,String streetId);
    String controlPermissionUpdateUser(String userId,String operationUserid,String roleId,String precinctTreeId);
    List<AuthToken> queryAuthToken(String token);

    void updateUserState(String userId, String userState, String errLoginTimes);

    Result userWithPath(String trueName, Integer page, Integer pageSize);
}
