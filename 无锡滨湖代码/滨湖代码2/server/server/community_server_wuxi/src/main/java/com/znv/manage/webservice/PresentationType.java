
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PresentationType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="PresentationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Presentation"/>
 *     &lt;enumeration value="Live"/>
 *     &lt;enumeration value="NotSupport"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PresentationType")
@XmlEnum
public enum PresentationType {

    @XmlEnumValue("Presentation")
    PRESENTATION("Presentation"),
    @XmlEnumValue("Live")
    LIVE("Live"),
    @XmlEnumValue("NotSupport")
    NOT_SUPPORT("NotSupport");
    private final String value;

    PresentationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PresentationType fromValue(String v) {
        for (PresentationType c: PresentationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
