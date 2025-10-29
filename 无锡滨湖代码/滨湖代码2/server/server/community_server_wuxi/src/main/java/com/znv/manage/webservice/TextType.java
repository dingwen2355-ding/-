
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TextType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="TextType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Caption"/>
 *     &lt;enumeration value="Banner"/>
 *     &lt;enumeration value="Shortmsg"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TextType")
@XmlEnum
public enum TextType {

    @XmlEnumValue("Caption")
    CAPTION("Caption"),
    @XmlEnumValue("Banner")
    BANNER("Banner"),
    @XmlEnumValue("Shortmsg")
    SHORTMSG("Shortmsg");
    private final String value;

    TextType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextType fromValue(String v) {
        for (TextType c: TextType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
