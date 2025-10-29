
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SortType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="SortType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="asc"/>
 *     &lt;enumeration value="desc"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SortType")
@XmlEnum
public enum SortType {

    @XmlEnumValue("asc")
    ASC("asc"),
    @XmlEnumValue("desc")
    DESC("desc");
    private final String value;

    SortType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SortType fromValue(String v) {
        for (SortType c: SortType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
