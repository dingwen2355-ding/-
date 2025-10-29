package com.ruoyi.common.vo;

import lombok.Data;

@Data
public class AddApplicationRes {

    private String token;
    private String name;
    private Integer categoryId;
    private String logoId;
    private String logo;
    private String description;
    private String deptId;
    private String contacts;
    private String telephone;
    private String url;
    private Integer isGround;
    private Integer isRecommend;
    private Integer isSendSms;
    private String iconId;
    private String icon;
}
