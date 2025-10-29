package com.znv.manage.common.bean;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Region {

    private String indexCode;
    private String name;
    private String parentIndexCode;
    private List<Region> children;
}
