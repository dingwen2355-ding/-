package com.znv.manage.dao;


import com.znv.manage.bean.user.AuthToken;
import com.znv.manage.bean.user.Module;
import com.znv.manage.bean.user.Role;
import com.znv.manage.bean.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserServiceMapper {

    public void insertUser(User user);

    public void updateUser(
            @Param(value = "userId") String userId,
            @Param(value = "userName") String userName,
            @Param(value = "password") String password,
            @Param(value = "departmentId") String departmentId,
            @Param(value = "roleId") String roleId,
            @Param(value = "precinctTreeId") String precinctTreeId,
            @Param(value = "userType") String userType,
            @Param(value = "employeeId") String employeeId,
            @Param(value = "trueName") String trueName,
            @Param(value = "mobilePhone") String mobilePhone,
            @Param(value = "email") String email,
            @Param(value = "phone") String phone,
            @Param(value = "address") String address,
            @Param(value = "userState") String userState,
            @Param(value = "errLoginTimes") Integer errLoginTimes,
            @Param(value = "updateTime") Date updateTime,
            @Param(value = "description") String description,
            @Param(value = "adminUser") String adminUser,
            @Param(value = "fax") String fax,
            @Param(value = "gender") String gender,
            @Param(value = "userLevel") String userLevel,
            @Param(value = "loginClientType") String loginClientType,
            @Param(value = "userUrl") String userUrl,
            @Param(value = "systemFlag") String systemFlag,
            @Param(value="streetId") String streetId
    );

    public void deleteUsers(
            @Param(value = "userIds") String userIds,
            @Param(value = "userName") String userName,
            @Param(value = "password") String password,
            @Param(value = "departmentId") String departmentId,
            @Param(value = "userType") String userType,
            @Param(value = "employeeId") String employeeId,
            @Param(value = "trueName") String trueName,
            @Param(value = "mobilePhone") String mobilePhone,
            @Param(value = "email") String email,
            @Param(value = "phone") String phone,
            @Param(value = "address") String address,
            @Param(value = "userState") String userState,
            @Param(value = "errLoginTimes") Integer errLoginTimes,
            @Param(value = "updateTime") Date updateTime,
            @Param(value = "description") String description,
            @Param(value = "adminUser") String adminUser,
            @Param(value = "fax") String fax,
            @Param(value = "gender") String gender,
            @Param(value = "userLevel") String userLevel,
            @Param(value = "loginClientType") String loginClientType,
            @Param(value = "userUrl") String userUrl,
            @Param(value = "systemFlag") String systemFlag,
            @Param(value = "createTime") Date createTime
    );

    public List<User> queryUsers(
            @Param(value = "userIds") String userIds,
            @Param(value = "userName") String userName,
            @Param(value = "password") String password,
            @Param(value = "precinctId") String precinctId,
            @Param(value = "departmentId") String departmentId,
            @Param(value = "precinctName") String precinctName,
            @Param(value = "userType") String userType,
            @Param(value = "employeeId") String employeeId,
            @Param(value = "trueName") String trueName,
            @Param(value = "mobilePhone") String mobilePhone,
            @Param(value = "email") String email,
            @Param(value = "phone") String phone,
            @Param(value = "address") String address,
            @Param(value = "userState") String userState,
            @Param(value = "errLoginTimes") Integer errLoginTimes,
            @Param(value = "updateTime") Date updateTime,
            @Param(value = "description") String description,
            @Param(value = "adminUser") String adminUser,
            @Param(value = "fax") String fax,
            @Param(value = "gender") String gender,
            @Param(value = "userLevel") String userLevel,
            @Param(value = "loginClientType") String loginClientType,
            @Param(value = "userUrl") String userUrl,
            @Param(value = "systemFlag") String systemFlag,
            @Param(value = "createTime") Date createTime,
            @Param(value = "roleId") String roleId
    );

    public List<Module> queryRelatedModules(
            @Param(value = "userId") int userId
    );

    public void addUserRoles(
            @Param(value = "userId") String userId,
            @Param(value = "roleIds") List roleIds
    );

    public void removeUserRoles(
            @Param(value = "userId") String userId,
            @Param(value = "roleIds") String roleIds
    );

    public List<Role> queryUserRoles(
            @Param(value = "userId") String userId
    );

    public void  changePassword(
            @Param(value = "userId") String userId,
            @Param(value = "newPassword") String newPassword);

    List<Map<String, String>> queryDepartment(
            @Param(value = "id") String id,
            @Param(value = "department") String department,
            @Param(value = "type") String type,
            @Param(value = "address") String address,
            @Param(value = "precinct_id") String precinct_id);

    List<Map<String, String>> getToken(@Param(value = "userID") Integer userID);

    List<Map<String, Object>>  getUserInfoByUserId(
            @Param(value = "userId") String userId,
            @Param(value = "operationUserid") String operationUserid);

    String  compareRoleLevelByRoleId(
            @Param(value = "roleId1") String roleId1,
            @Param(value = "roleId2") String roleId2);

    String  getUserIdsByStreetId(
            @Param(value = "operationUserid") String operationUserid);

    List<Map<String, Object>>  getUserInfoByUserIdSameStreet(
            @Param(value = "userId") String userId);

    List<String> querySameStreetUser(@Param(value = "userId") String userId);

    List<String> queryBelongToAreaUser(@Param(value = "userId") String userId);

    String getStreetIdByUserId(@Param(value = "userId")String userId);

    int getPrecinctAdminNum(@Param(value = "streetId") String streetId,
                            @Param(value = "roleId") String roleId);
    List<AuthToken> queryAuthToken(@Param(value = "token") String token);

    void updateUserState(@Param(value = "userId") String userId,
                         @Param(value = "userState") String userState,@Param(value = "errLoginTimes") String errLoginTimes);

    int changePasswordByName(@Param("userName")String userName,
                             @Param("newPassword")String newPassword);

    int isExistByUserName(@Param("userName")String userName);

    List<Map<String, Object>> selectUserWithPrecinctPath(@Param("trueName")String trueName);
}
