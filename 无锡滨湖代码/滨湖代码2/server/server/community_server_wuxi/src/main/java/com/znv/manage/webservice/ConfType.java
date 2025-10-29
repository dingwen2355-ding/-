
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CommonConf"/>
 *     &lt;enumeration value="AdhocConf"/>
 *     &lt;enumeration value="MultiConf"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfType")
@XmlEnum
public enum ConfType {

    @XmlEnumValue("CommonConf")
    COMMON_CONF("CommonConf"),
    @XmlEnumValue("AdhocConf")
    ADHOC_CONF("AdhocConf"),
    @XmlEnumValue("MultiConf")
    MULTI_CONF("MultiConf");
    private final String value;

    ConfType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfType fromValue(String v) {
        for (ConfType c: ConfType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
