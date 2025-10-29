
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PushMode�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="PushMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Pull"/>
 *     &lt;enumeration value="Callback"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PushMode")
@XmlEnum
public enum PushMode {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Pull")
    PULL("Pull"),
    @XmlEnumValue("Callback")
    CALLBACK("Callback");
    private final String value;

    PushMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PushMode fromValue(String v) {
        for (PushMode c: PushMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
