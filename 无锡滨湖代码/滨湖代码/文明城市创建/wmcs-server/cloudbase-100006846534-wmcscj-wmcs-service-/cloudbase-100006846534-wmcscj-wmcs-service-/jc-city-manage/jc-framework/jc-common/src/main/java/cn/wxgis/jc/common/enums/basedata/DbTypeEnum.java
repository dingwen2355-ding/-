package cn.wxgis.jc.common.enums.basedata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据类型枚举类
 */
@Getter
@AllArgsConstructor
public enum DbTypeEnum {

    MYSQL(1,"MYSQL"),
    SQLSERVER(2,"MICROSOFT SQL SERVER"),
    ORACLE(3,"ORACLE"),
    POSTGRESQL(4,"POSTGRESQL");

    private Integer code;
    private String value;

}
