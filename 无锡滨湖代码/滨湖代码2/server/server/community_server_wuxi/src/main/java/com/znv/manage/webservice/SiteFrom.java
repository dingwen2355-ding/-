
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteFrom�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="SiteFrom">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Internal"/>
 *     &lt;enumeration value="External"/>
 *     &lt;enumeration value="EConference"/>
 *     &lt;enumeration value="LyncSite"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SiteFrom")
@XmlEnum
public enum SiteFrom {

    @XmlEnumValue("Internal")
    INTERNAL("Internal"),
    @XmlEnumValue("External")
    EXTERNAL("External"),
    @XmlEnumValue("EConference")
    E_CONFERENCE("EConference"),
    @XmlEnumValue("LyncSite")
    LYNC_SITE("LyncSite");
    private final String value;

    SiteFrom(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SiteFrom fromValue(String v) {
        for (SiteFrom c: SiteFrom.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
