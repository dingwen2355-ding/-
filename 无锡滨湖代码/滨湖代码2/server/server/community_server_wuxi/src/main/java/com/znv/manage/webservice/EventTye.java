
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>EventTye�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="EventTye">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Start"/>
 *     &lt;enumeration value="End"/>
 *     &lt;enumeration value="Change"/>
 *     &lt;enumeration value="SiteAdd"/>
 *     &lt;enumeration value="SiteDelete"/>
 *     &lt;enumeration value="SiteStatusChange"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EventTye")
@XmlEnum
public enum EventTye {

    @XmlEnumValue("Start")
    START("Start"),
    @XmlEnumValue("End")
    END("End"),
    @XmlEnumValue("Change")
    CHANGE("Change"),
    @XmlEnumValue("SiteAdd")
    SITE_ADD("SiteAdd"),
    @XmlEnumValue("SiteDelete")
    SITE_DELETE("SiteDelete"),
    @XmlEnumValue("SiteStatusChange")
    SITE_STATUS_CHANGE("SiteStatusChange");
    private final String value;

    EventTye(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventTye fromValue(String v) {
        for (EventTye c: EventTye.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
