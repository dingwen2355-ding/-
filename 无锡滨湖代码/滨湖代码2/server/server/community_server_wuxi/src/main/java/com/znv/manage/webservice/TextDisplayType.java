
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TextDisplayType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="TextDisplayType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Left"/>
 *     &lt;enumeration value="Right"/>
 *     &lt;enumeration value="Middle"/>
 *     &lt;enumeration value="RightToleft"/>
 *     &lt;enumeration value="BottomToTop"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TextDisplayType")
@XmlEnum
public enum TextDisplayType {

    @XmlEnumValue("Left")
    LEFT("Left"),
    @XmlEnumValue("Right")
    RIGHT("Right"),
    @XmlEnumValue("Middle")
    MIDDLE("Middle"),
    @XmlEnumValue("RightToleft")
    RIGHT_TOLEFT("RightToleft"),
    @XmlEnumValue("BottomToTop")
    BOTTOM_TO_TOP("BottomToTop"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    TextDisplayType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextDisplayType fromValue(String v) {
        for (TextDisplayType c: TextDisplayType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
