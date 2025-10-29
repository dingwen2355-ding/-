package com.znv.manage.common.bean;


import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class People {

    private String id;
    private String name;
    private String unitId;
    private String unitName;
    private String phone;
    private String landLine;
    private String duty;

    public String checkInfo(){
        String result = "";
        if(StringUtils.isEmpty(name)){
            return "用户名不存在!";
        }
        if(StringUtils.isEmpty(unitName)){
            return "所属部门不存在!";
        }
        if(StringUtils.isEmpty(unitId)){
            return "所属部门不存在!";
        }
        if(StringUtils.isEmpty(phone)){
            return "电话不存在!";
        }
        return result;
    }
}
