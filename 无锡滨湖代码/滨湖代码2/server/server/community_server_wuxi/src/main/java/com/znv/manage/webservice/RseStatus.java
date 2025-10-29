
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RseStatus�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="RseStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Online"/>
 *     &lt;enumeration value="Offline"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RseStatus")
@XmlEnum
public enum RseStatus {

    @XmlEnumValue("Online")
    ONLINE("Online"),
    @XmlEnumValue("Offline")
    OFFLINE("Offline");
    private final String value;

    RseStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RseStatus fromValue(String v) {
        for (RseStatus c: RseStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
