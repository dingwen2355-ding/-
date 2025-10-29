package com.znv.manage.common.bean.resource;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BasicExpertInfo {
    private String id;
    private String name;
    private Integer sex;
    private String birthday;
    private String type;
    private String title;
    private String company;
    private String experience;
    private String experienceType;
    private String phone;
    private String idNumber;
    private String address;
    private String expertCategory;
    private String dictOrder;
}
