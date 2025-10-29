
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfSiteStatus�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfSiteStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Unknow"/>
 *     &lt;enumeration value="Noexist"/>
 *     &lt;enumeration value="Connected"/>
 *     &lt;enumeration value="Disconnected"/>
 *     &lt;enumeration value="Connecting"/>
 *     &lt;enumeration value="Ringing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfSiteStatus")
@XmlEnum
public enum ConfSiteStatus {

    @XmlEnumValue("Unknow")
    UNKNOW("Unknow"),
    @XmlEnumValue("Noexist")
    NOEXIST("Noexist"),
    @XmlEnumValue("Connected")
    CONNECTED("Connected"),
    @XmlEnumValue("Disconnected")
    DISCONNECTED("Disconnected"),
    @XmlEnumValue("Connecting")
    CONNECTING("Connecting"),
    @XmlEnumValue("Ringing")
    RINGING("Ringing");
    private final String value;

    ConfSiteStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfSiteStatus fromValue(String v) {
        for (ConfSiteStatus c: ConfSiteStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
