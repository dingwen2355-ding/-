
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TextDisplayPosition�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="TextDisplayPosition">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Top"/>
 *     &lt;enumeration value="Middle"/>
 *     &lt;enumeration value="Bottom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TextDisplayPosition")
@XmlEnum
public enum TextDisplayPosition {

    @XmlEnumValue("Top")
    TOP("Top"),
    @XmlEnumValue("Middle")
    MIDDLE("Middle"),
    @XmlEnumValue("Bottom")
    BOTTOM("Bottom");
    private final String value;

    TextDisplayPosition(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextDisplayPosition fromValue(String v) {
        for (TextDisplayPosition c: TextDisplayPosition.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
