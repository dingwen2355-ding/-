
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConferenceMode�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConferenceMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Auto"/>
 *     &lt;enumeration value="Convergent"/>
 *     &lt;enumeration value="VMR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConferenceMode")
@XmlEnum
public enum ConferenceMode {

    @XmlEnumValue("Auto")
    AUTO("Auto"),
    @XmlEnumValue("Convergent")
    CONVERGENT("Convergent"),
    VMR("VMR");
    private final String value;

    ConferenceMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConferenceMode fromValue(String v) {
        for (ConferenceMode c: ConferenceMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
