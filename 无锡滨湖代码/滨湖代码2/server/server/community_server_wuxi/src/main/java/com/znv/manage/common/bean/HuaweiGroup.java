package com.znv.manage.common.bean;


import lombok.Data;

import java.util.List;

@Data
public class HuaweiGroup {

    private String id;
    private String name;
    private String type;
    private String parentId;
    private String uris;
    private List<Object> uriList;
    private List<Object> children;
    private String kind = "分组";
    private String precinctId;
}
