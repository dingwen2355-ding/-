package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommunicationInfo {

    private String userId;

    private String mobile;

    private String unionId;

    private String name;

    private String deptIdList;

    private String avatar;

    private String title;

}
