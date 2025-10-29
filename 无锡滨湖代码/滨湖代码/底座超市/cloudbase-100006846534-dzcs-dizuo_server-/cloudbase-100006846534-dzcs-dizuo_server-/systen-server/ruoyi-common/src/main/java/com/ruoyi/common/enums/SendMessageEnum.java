package com.ruoyi.common.enums;


public enum SendMessageEnum {

    cmAbilityCreate("用户%s申请%s的应用能力", "请尽快审批!"),
    cmAbilityWithdraw("用户%s对%s应用能力的申请已撤回", "请知悉!"),
    cmAbilitySuccess("滨湖区大数据管理局门户平台已通过%s对%s应用能力申请", "请知悉!"),
    cmAbilityFail("滨湖区大数据管理局门户平台已驳回%s对%s应用能力申请", "请知悉!"),


    cmAbilityCreate_new("%s申请%s应用能力", "请等待管理员审批!"),
    cmAbilityWithdraw_new("%s的%s应用能力申请已被撤回", ""),
    cmAbilitySuccess_new("%s申请%s应用能力", "已审批通过!"),


    cmAppCreate("用户%s申请%s入驻滨湖区大数据管理局门户平台", "请尽快审批!"),
    cmAppWithdraw("用户%s对%s入驻滨湖区大数据管理局门户平台的申请已撤回", "请知悉!"),
    cmAppSuccess("滨湖区大数据管理局门户平台已通过%s的%s入驻申请", "请知悉!"),
    cmAppFail("滨湖区大数据管理局门户平台已驳回%s的%s入驻申请", "请知悉!"),

    cmAppCreate_new("%s申请上架%s应用", "请等待管理员审批!"),
    cmAppWithdraw_new("%s申请上架%s应用已被撤回", ""),
    cmAppSuccess_new("%s申请上架%s应用", "已审批通过!"),


    /**
     * 申请试用
     */
    authorityCreate("%s申请%s应用权限","请等待管理员审批！"),
    authorityWithdraw("%s的%s应用权限申请已被撤回",""),
    authoritySuccess("%s申请%s应用权限","已审批通过！"),
    authorityFail("%s申请%s应用权限", "已被驳回！");


    private String header;

    private String user;

    private String end;


    SendMessageEnum(String header, String end) {
        this.header = header;
        this.end = end;
    }

    @Override
    public String toString() {
        return this.header + ',' + this.end;
    }
}