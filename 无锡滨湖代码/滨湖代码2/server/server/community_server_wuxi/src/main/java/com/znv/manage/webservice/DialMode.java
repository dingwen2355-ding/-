
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DialMode�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="DialMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DialIn"/>
 *     &lt;enumeration value="DialOut"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DialMode")
@XmlEnum
public enum DialMode {

    @XmlEnumValue("DialIn")
    DIAL_IN("DialIn"),
    @XmlEnumValue("DialOut")
    DIAL_OUT("DialOut");
    private final String value;

    DialMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DialMode fromValue(String v) {
        for (DialMode c: DialMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
