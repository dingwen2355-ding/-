package com.ruoyi.common.vo;

import lombok.Data;

import java.util.Map;

@Data
public class AddUserAuthorityReq {

    private String token;
    /**
     * 需要授权的用户账号
     */
    private String account;
    /**
     * 需要授权的用户账号的部门，可不填，默认用户主部门
     */
    private String deptId;
    /**
     * 应用id，对应12接口中的id字段
     */
    private String applicationManagementId;
    /**
     * 申请人账号
     */
    private String applicant;
    /**
     * 申请理由
     */
    private String reason;

    private Map<String,String> param;
}
