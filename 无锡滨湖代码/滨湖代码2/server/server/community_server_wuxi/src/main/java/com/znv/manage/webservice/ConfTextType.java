
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfTextType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfTextType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="caption"/>
 *     &lt;enumeration value="banner"/>
 *     &lt;enumeration value="shortmsg"/>
 *     &lt;enumeration value="bottomCap"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfTextType")
@XmlEnum
public enum ConfTextType {

    @XmlEnumValue("caption")
    CAPTION("caption"),
    @XmlEnumValue("banner")
    BANNER("banner"),
    @XmlEnumValue("shortmsg")
    SHORTMSG("shortmsg"),
    @XmlEnumValue("bottomCap")
    BOTTOM_CAP("bottomCap");
    private final String value;

    ConfTextType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfTextType fromValue(String v) {
        for (ConfTextType c: ConfTextType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
