
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfStreamType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfStreamType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VIP"/>
 *     &lt;enumeration value="NonVIP"/>
 *     &lt;enumeration value="Voice"/>
 *     &lt;enumeration value="NoAllAdapt"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfStreamType")
@XmlEnum
public enum ConfStreamType {

    VIP("VIP"),
    @XmlEnumValue("NonVIP")
    NON_VIP("NonVIP"),
    @XmlEnumValue("Voice")
    VOICE("Voice"),
    @XmlEnumValue("NoAllAdapt")
    NO_ALL_ADAPT("NoAllAdapt");
    private final String value;

    ConfStreamType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfStreamType fromValue(String v) {
        for (ConfStreamType c: ConfStreamType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
