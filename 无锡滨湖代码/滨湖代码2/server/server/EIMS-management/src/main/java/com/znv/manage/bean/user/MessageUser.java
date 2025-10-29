package com.znv.manage.bean.user;

import lombok.Data;

@Data
public class MessageUser {
    private String userId;
    private String userName;
    private String departmentId;//所属部门
    private String roleId;//用户角色id
    private String trueName;
    private String phone;
    private MessagePrecinct messagePrecinct;
}
