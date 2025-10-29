
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FreeBusyState�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="FreeBusyState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="invaild"/>
 *     &lt;enumeration value="free"/>
 *     &lt;enumeration value="busy"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FreeBusyState")
@XmlEnum
public enum FreeBusyState {

    @XmlEnumValue("invaild")
    INVAILD("invaild"),
    @XmlEnumValue("free")
    FREE("free"),
    @XmlEnumValue("busy")
    BUSY("busy");
    private final String value;

    FreeBusyState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FreeBusyState fromValue(String v) {
        for (FreeBusyState c: FreeBusyState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
