package com.znv.manage.common.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 专项指挥部门(TCfgCommandDepartment)实体类
 *
 * @author makejava
 * @since 2022-11-28 11:18:21
 */
@Data
public class CommandDepartment implements Serializable {
    private static final long serialVersionUID = -22221794678834389L;

    private Integer id;
    /**
     * 指挥部名称
     */
    private String departmentName;

    private String workTeamName;

    /**
     * 领导单位
     */
    private String leadUnit;

    /**
     * 成员单位
     */
    private String memberUnit;

    /**
     * 职责
     */
    private String responsibility;

    private String dataSource;

    private String sharedDatetime;

}
