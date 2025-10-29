
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CaptionDisPosition�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CaptionDisPosition">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="top"/>
 *     &lt;enumeration value="middle"/>
 *     &lt;enumeration value="bottom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CaptionDisPosition")
@XmlEnum
public enum CaptionDisPosition {

    @XmlEnumValue("top")
    TOP("top"),
    @XmlEnumValue("middle")
    MIDDLE("middle"),
    @XmlEnumValue("bottom")
    BOTTOM("bottom");
    private final String value;

    CaptionDisPosition(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CaptionDisPosition fromValue(String v) {
        for (CaptionDisPosition c: CaptionDisPosition.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
