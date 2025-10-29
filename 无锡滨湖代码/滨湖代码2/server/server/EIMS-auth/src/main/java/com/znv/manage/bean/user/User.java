package com.znv.manage.bean.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String userName;
    private String password;
    private String departmentId;//所属部门
    private String roleId;//用户角色id
    private String precinctTreeId;//资源树，逗号分隔
    private String userType; //0普通用户,1第三方平台，默认0
    private String employeeId;
    private String trueName;
    private String mobilePhone;
    private String email;
    private String phone;
    private String address;
    private String userState;//0可用,1不可用，默认0
    private Integer errLoginTimes;//错误的登录的次数，大于5次锁定
    private String updateTime;
    private String description;
    private String adminUser;
    private String fax;
    private String gender;//0男，1女，2未知，默认2
    private String userLevel;
    private String loginClientType;
    private byte[] userImg;
    private String userUrl;
    private String systemFlag;
    private String createTime;
    private String departmentName;
    private String roleName;
    private String operationUserid;//操作用户id
    private String streetId;//用户所属街镇id
	private String streetName;//所属街道名
    private String regionName;//用户所属区名
    private String precinctKind;

    //用户关联的角色集合（不包括通过用户组关联的角色）
    private List<Role> roles = new ArrayList<>();

    //有权限的模块
    private List<HashMap> modules = new ArrayList<>();

    public User(
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
            String updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            byte[] userImg,
            String userUrl,
            String systemFlag,
            String createTime,
            String departmentName,
            String roleName,
            String operationUserid,
            String streetId
    ) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.precinctTreeId = precinctTreeId;
        this.userType = userType;
        this.employeeId = employeeId;
        this.trueName = trueName;
        this.mobilePhone =  mobilePhone;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userState = userState;
        this.errLoginTimes = errLoginTimes;
        this.updateTime = updateTime;
        this.description = description;
        this.adminUser = adminUser;
        this.fax = fax;
        this.gender = gender;
        this.userLevel = userLevel;
        this.loginClientType = loginClientType;
        this.userImg = userImg;
        this.userUrl = userUrl;
        this.systemFlag = systemFlag;
        this.createTime = createTime;
        this.departmentName = departmentName;
        this.roleName = roleName;
        this.operationUserid = operationUserid;
        this.streetId=streetId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
