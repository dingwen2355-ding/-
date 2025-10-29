
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CallProtocol�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CallProtocol">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="H323"/>
 *     &lt;enumeration value="Sip"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CallProtocol")
@XmlEnum
public enum CallProtocol {

    @XmlEnumValue("H323")
    H_323("H323"),
    @XmlEnumValue("Sip")
    SIP("Sip");
    private final String value;

    CallProtocol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CallProtocol fromValue(String v) {
        for (CallProtocol c: CallProtocol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
