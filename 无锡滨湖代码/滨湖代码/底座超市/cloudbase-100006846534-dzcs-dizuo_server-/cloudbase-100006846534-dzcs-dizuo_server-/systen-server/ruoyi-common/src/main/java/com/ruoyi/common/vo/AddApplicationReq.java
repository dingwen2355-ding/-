package com.ruoyi.common.vo;

import lombok.Data;

@Data
public class AddApplicationReq {

    private String token;


    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用类别，对应15接口中的id字段
     */
    private Long categoryId;
    /**
     * 应用截图的id，对应17接口中的id字段，并用逗号隔开
     */
    private String logoId;

    /**
     * 应用截图的地址，对应17接口中的fileUrl字段，并用逗号隔开
     */
    private String logo;

    /**
     * 应用介绍
     */
    private String description;

    /**
     * 所属部门，对应16接口中的id字段
     */
    private Long deptId;

    /**
     * 联系人
     */
    private String contacts;
    /**
     * 电话
     */
    private String telephone;

    /**
     * 应用URL
     */
    private String url;
    /**
     * 是否上架   1-是，0-否
     */
    private Integer isGround;
    /**
     * 是否推荐   1-是，0-否
     */
    private Integer isRecommend;

    /**
     * 是否短信推送   1-是，0-否
     */
    private Integer isSendSms;

    /**
     * 应用图标的id，对应17接口中的id字段
     */
    private String iconId;

    /**
     * 应用图标的地址，对应17接口中的fileUrl字段
     */
    private String icon;
    /**
     * 申请人账号
     */
    private String applicant;
    /**
     * 申请理由
     */
    private String reason;


}
