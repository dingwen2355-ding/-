package com.znv.manage.common.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Data
public class ImportantPeople {

    private String id;
    private String name;
    private String cardId;
    private String cardType;
    private String sex;
    private String age;
    private String address;
    private String village;
    private String committee;
    private String street;
    private String area;
    private String city;
    private String province;

    private List<Map<String,String>> gpsList;

    public String checkInfo(){
        if(StringUtils.isEmpty(name)){
            return "姓名为空!";
        }
        if(StringUtils.isEmpty(sex)){
            return "性别为空!";
        }
        if(StringUtils.isEmpty(age)){
            return "年龄为空!";
        }
        if(StringUtils.isEmpty(address)){
            return "地址为空";
        }
        if(StringUtils.isEmpty(street)){
            return "所属街道为空";
        }
        return "";
    }
}
