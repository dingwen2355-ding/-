package com.znv.manage.common.bean;


import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
public class Unit {

    private String id;
    private String name;
    private String superId;
    private String superName;
    private String areaId;
    private String type;
    private String precinctName;
    private List<Unit> children;
    private String precinctKind;

}
