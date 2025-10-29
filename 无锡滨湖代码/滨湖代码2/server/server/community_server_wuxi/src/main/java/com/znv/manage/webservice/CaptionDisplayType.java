
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CaptionDisplayType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CaptionDisplayType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="left"/>
 *     &lt;enumeration value="right"/>
 *     &lt;enumeration value="middle"/>
 *     &lt;enumeration value="right2Left"/>
 *     &lt;enumeration value="bottom2Top"/>
 *     &lt;enumeration value="customType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CaptionDisplayType")
@XmlEnum
public enum CaptionDisplayType {

    @XmlEnumValue("left")
    LEFT("left"),
    @XmlEnumValue("right")
    RIGHT("right"),
    @XmlEnumValue("middle")
    MIDDLE("middle"),
    @XmlEnumValue("right2Left")
    RIGHT_2_LEFT("right2Left"),
    @XmlEnumValue("bottom2Top")
    BOTTOM_2_TOP("bottom2Top"),
    @XmlEnumValue("customType")
    CUSTOM_TYPE("customType");
    private final String value;

    CaptionDisplayType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CaptionDisplayType fromValue(String v) {
        for (CaptionDisplayType c: CaptionDisplayType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
