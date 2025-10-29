
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HwPCTermType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="HwPCTermType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WebClient"/>
 *     &lt;enumeration value="CloudLink"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HwPCTermType")
@XmlEnum
public enum HwPCTermType {

    @XmlEnumValue("WebClient")
    WEB_CLIENT("WebClient"),
    @XmlEnumValue("CloudLink")
    CLOUD_LINK("CloudLink");
    private final String value;

    HwPCTermType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HwPCTermType fromValue(String v) {
        for (HwPCTermType c: HwPCTermType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
