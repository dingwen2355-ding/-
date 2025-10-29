package com.znv.manage.common.bean;


import lombok.Data;

@Data
public class OperateLog {

    private String name;
    private String area;
    private String state;
    private String time;
    private String precinctId;
    private String precinctName;
}
